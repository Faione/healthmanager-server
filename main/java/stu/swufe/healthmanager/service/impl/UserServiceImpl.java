package stu.swufe.healthmanager.service.impl;


import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import stu.swufe.healthmanager.common.Constants;
import stu.swufe.healthmanager.common.ResponseMsg;
import stu.swufe.healthmanager.dao.RefreshTokenPojoMapper;
import stu.swufe.healthmanager.dao.UserPojoMapper;
import stu.swufe.healthmanager.pojo.RefreshTokenPojo;
import stu.swufe.healthmanager.pojo.UserPojo;
import stu.swufe.healthmanager.response.ResponseResult;
import stu.swufe.healthmanager.response.ResponseState;
import stu.swufe.healthmanager.service.IUserService;
import stu.swufe.healthmanager.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

@Service
@Log4j
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserPojoMapper userPojoMapper;

    @Autowired
    private RefreshTokenPojoMapper refreshTokenPojoMapper;

    @Autowired
    private IDWorker idWorker;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private TokenStorage tokenStorage;





    @Override
    public ResponseResult initManagerAccount(UserPojo userPojo, HttpServletRequest request) {

        // 1. 检查是否初始化
        if(TextUtils.isEmpty(userPojo.getUserName())){
            return ResponseResult.creatFailed(ResponseMsg.USERNAME_EMPTY);
        }

        if(userPojoMapper.selectByName(userPojo.getUserName())>0){
            return ResponseResult.creatFailed(ResponseMsg.USER_EXIST);
        }


        // 2. 空值检测
        if(TextUtils.isEmpty(userPojo.getPassword())){
            return ResponseResult.creatFailed(ResponseMsg.PASSWORD_EMPTY);
        }
//        if(TextUtils.isEmpty(userPojo.getEmail())){
//            return ResponseResult.creatFailed();
//        }


        // 3. 初始化默认参数
        userPojo.setId(String.valueOf(idWorker.nextId()));
        userPojo.setPassword(bCryptPasswordEncoder.encode(userPojo.getPassword()));
        userPojo.setRoles(Constants.User.ROlA_ADMIN);
        userPojo.setAvatar(Constants.User.DEFAULT_AVATAR);
        userPojo.setState(Constants.User.DEFAULT_STATE);

        setDefaultInfo(userPojo, request);

        // 4. 保存入数据库
        logUserPojo(userPojo);

        userPojoMapper.insert(userPojo);


        return ResponseResult.createSuccess();
    }


    @Override
    public void createCaptcha(HttpServletResponse response, String captchaKey) throws IOException {
        if(TextUtils.isEmpty(captchaKey) || captchaKey.length() < 13){
            return;
        }

        // 设置请求头为输出图片类型
        response.setContentType("image/gif");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        // 三个参数分别为宽、高、位数
        SpecCaptcha specCaptcha = new SpecCaptcha(130, 48, 5);
        // 设置字体
        specCaptcha.setFont(new Font("Verdana", Captcha.FONT_1, 32));  // 有默认字体，可以不用设置
        // 设置类型，纯数字、纯字母、字母数字混合
        specCaptcha.setCharType(Captcha.TYPE_DEFAULT);


        // 验证码存入Redis
        redisUtil.set(Constants.StaticValue.SALT_CAPTCHA + captchaKey, specCaptcha.text(), 5*60);

        // 输出图片流
        specCaptcha.out(response.getOutputStream());
    }

    @Override
    public ResponseResult register(UserPojo userPojo, String captchaCode, String captchaKey, HttpServletRequest request) {
        // 1. 检查图灵验证码是否正确
        if(cheakCaptcha(captchaCode, captchaKey)!=null)  return cheakCaptcha(captchaCode, captchaKey);

        // 2. 检查用户是否注册
        ResponseResult responseResult = checkUserName(userPojo.getUserName());
        if(!responseResult.isSuccess()){
            return responseResult;
        }

        // 3. 密码加密

        userPojo.setPassword(bCryptPasswordEncoder.encode(userPojo.getPassword()));

        // 4. 补全数据

        userPojo.setId(String.valueOf(idWorker.nextId()));
        userPojo.setRoles(Constants.User.NORMAL_USER);
        userPojo.setAvatar(Constants.User.DEFAULT_AVATAR);
        userPojo.setState(Constants.User.NORMAL_STATE);

        setDefaultInfo(userPojo, request);

        // 5. 存入数据库
        userPojoMapper.insert(userPojo);

        // 6. 生成Token并返回
        Map<String, Object> claims = ClaimsUtil.toClaims(userPojo);
        tokenStorage.setTools(userPojoMapper, refreshTokenPojoMapper, idWorker, redisUtil);

        tokenStorage.writeToken(claims);

        // 携带用户信息并返回

        return ResponseResult.createSuccess(ResponseState.REGISTER_SUCCESS, claims).setToken(tokenStorage.getNewTokenkey());
    }

    @Override
    public ResponseResult dologin(String captchaCode, String captchaKey, UserPojo userPojo) {
        // 1. 检查图灵验证码是否正确
        if(cheakCaptcha(captchaCode, captchaKey)!=null)  return cheakCaptcha(captchaCode, captchaKey);

        // 2. 空值检测
        String username = userPojo.getUserName();
        String password = userPojo.getPassword();

        if(TextUtils.isEmpty(username)){
            return ResponseResult.creatFailed(ResponseMsg.USERNAME_EMPTY);
        }
        if(TextUtils.isEmpty(password)){
            return ResponseResult.creatFailed(ResponseMsg.PASSWORD_EMPTY);
        }


        // 2. 检查用户是否注册
        UserPojo userFromDb = userPojoMapper.selectAllByName(username);
        // 3. 验证密码是否正确
        if(userFromDb==null || !bCryptPasswordEncoder.matches(password, userFromDb.getPassword())){
            return ResponseResult.creatFailed("用户名或密码错误");
        }

        // 4. 密码正确，生成Token
        Map<String, Object> claims = ClaimsUtil.toClaims(userFromDb);
        tokenStorage.setTools(userPojoMapper, refreshTokenPojoMapper, idWorker, redisUtil);

        tokenStorage.writeToken(claims);

        // 携带用户信息并返回

        return ResponseResult.createSuccess(ResponseState.LOGIN_SUCCESS, claims).setToken(tokenStorage.getNewTokenkey());
    }

    @Override
    public UserPojo checkUser(StringBuffer tokenKeyBuffer) {
        tokenStorage.setTools(userPojoMapper, refreshTokenPojoMapper, idWorker, redisUtil);
        Map<String, Object> claims = tokenStorage.readRedis(String.valueOf(tokenKeyBuffer));


        if(claims!=null){
            if(tokenStorage.getNewTokenkey()!=null){
                tokenKeyBuffer.delete(0, tokenKeyBuffer.length());
                tokenKeyBuffer.append(tokenStorage.getNewTokenkey());
            }
            return ClaimsUtil.toPojo(claims, UserPojo.class);
        }
        return null;

    }

    @Override
    public ResponseResult getUserInfo(String userid) {
        UserPojo userPojo = userPojoMapper.selectUserInfoByPrimaryKey(userid);
        if(userPojo!=null){
            return ResponseResult.createSuccess(ResponseState.SUCCESS, userPojo);
        }else{
            return ResponseResult.creatFailed("用户不存在");
        }


    }

    @Override
    public ResponseResult getUserInfo(StringBuffer tokenKey) {
        UserPojo userPojoFromToken = checkUser(tokenKey);
        if(userPojoFromToken == null) {
            return ResponseResult.creatFailed(ResponseState.NOT_LOGIN_IN);
        }

        return ResponseResult.createSuccess(ResponseState.SUCCESS, userPojoFromToken)
                .setToken(String.valueOf(tokenKey));
    }

    @Override
    public ResponseResult checkUserName(String userName) {
        if(TextUtils.isEmpty(userName)){
            return ResponseResult.creatFailed(ResponseMsg.USERNAME_EMPTY);
        }
        if(userPojoMapper.selectByName(userName)>0){
            return ResponseResult.creatFailed(ResponseMsg.USER_EXIST);
        }

        // 测试后可去除文字说明
        return ResponseResult.createSuccess().setMsg("可以注册");
    }

    // 此处能修改的内容为，用户名（查重）、头像、签名
    // 不必检测空值（除必要），空值默认不参与修改
    @Override
    public ResponseResult updateUserInfo(String userId, StringBuffer tokenKey, UserPojo userPojo) {
        // 1. 检测用户是否登录
        UserPojo userPojoFromToken = checkUser(tokenKey);

        if(userPojoFromToken == null){
            return ResponseResult.creatFailed(ResponseState.NOT_LOGIN_IN);
        }

        // 2. 对比UserId，判断是否为同一用户

        if(!userId.equals(userPojoFromToken.getId())) return ResponseResult.creatFailed("该用户没有权限");

        userPojo.setId(userId);

        // 3. 用户名查重

        // 用户名不为空，不同于当前且数据库中已有则为重复
        if(userPojo.getUserName() != null
                && !userPojo.getUserName().equals(userPojoFromToken.getUserName())
                && userPojoMapper.selectByName(userPojo.getUserName())>0){
            return ResponseResult.creatFailed(ResponseMsg.USER_EXIST);
        }

        // 5. 删去Redis中的Token记录（已经无效）
        redisUtil.del(Constants.StaticValue.SALT_TOKEN + tokenKey);

        // 6.修改数据库
        // 标记Mysql_Token为脏（下次用户请求时，再获得新Token）

        userPojo.setUpdateTime(new Date());  // 记录修改时间
        refreshTokenPojoMapper.updateByTokenKey(Constants.StaticValue.SALT_TOKEN + tokenKey, true);
        userPojoMapper.updateByPrimaryKeySelective(userPojo);

        return ResponseResult.createSuccess("修改成功").setToken(String.valueOf(tokenKey));
    }

    @Override
    public ResponseResult updatePassword(String userId, StringBuffer tokenKey, UserPojo userPojo) {
        // 1. 检测用户是否登录
        UserPojo userPojoFromToken = checkUser(tokenKey);

        if(userPojoFromToken == null){
            return ResponseResult.creatFailed(ResponseState.NOT_LOGIN_IN);
        }

        // 2. 对比UserId，判断是否为同一用户

        if(!userId.equals(userPojoFromToken.getId())) return ResponseResult.creatFailed("该用户没有权限");

        userPojo.setId(userId);

        // 3. 空值检测
        if(userPojo.getPassword()==null){
            return ResponseResult.creatFailed(ResponseMsg.PASSWORD_EMPTY);
        }
        // 4. 修改密码（假定前端完成新旧密码验证）



        return null;
    }

    @Override
    public ResponseResult logout(String userId, String tokenKey) {
        // 此请求必然发生在登录后，不必判断是否登录
        if(TextUtils.isEmpty(tokenKey) || TextUtils.isEmpty(userId)) return ResponseResult.creatFailed();

        // 判断用户权限, redis中可能没有数据，故直接从数据库中寻找
        RefreshTokenPojo refreshTokenPojo = refreshTokenPojoMapper
                .selectByTokenKey(Constants.StaticValue.SALT_TOKEN + tokenKey);

        if(refreshTokenPojo == null) return ResponseResult.creatFailed();

        if(!userId.equals(refreshTokenPojo.getUserId())) return ResponseResult.creatFailed().setMsg("用户无权限");


        // 删除Redis，mysql中的登录token记录
        redisUtil.del(Constants.StaticValue.SALT_TOKEN + tokenKey);
        refreshTokenPojoMapper.deleteByUserId(userId);

        return ResponseResult.createSuccess();
    }


    private ResponseResult cheakCaptcha(String captchacode, String captchakey){
        if(TextUtils.isEmpty(captchacode)){
            return ResponseResult.creatFailed(ResponseMsg.CAPTCHA_EMPTY);
        }

        String captchaverifycode = (String) redisUtil.get(Constants.StaticValue.SALT_CAPTCHA + captchakey);

        if(TextUtils.isEmpty(captchaverifycode)){
            return ResponseResult.creatFailed("验证码过期");
        }

        // 检测验证码是否正确，忽略大小写
//      captchaverifycode.toLowerCase(Locale.ROOT).equals(captchacode.toLowerCase())
        if(!captchaverifycode.equalsIgnoreCase(captchacode)){
            return ResponseResult.creatFailed("验证码错误");
        }else{
            redisUtil.del(Constants.StaticValue.SALT_CAPTCHA + captchakey);
            return null;
        }
    }

    private void setDefaultInfo(UserPojo userPojo, HttpServletRequest request) {
        // 设置 ip
        userPojo.setLoginIp(request.getRemoteAddr());
        userPojo.setRegIp(request.getRemoteAddr());

        // 设置 日期
        userPojo.setCreateTime(new Date());
        userPojo.setUpdateTime(new Date());
    }

    public static void logUserPojo(UserPojo userPojo){
        log.info("UserName == >" + userPojo.getUserName());
        log.info("Password == >" + userPojo.getPassword());
        log.info("Email == >" + userPojo.getEmail());
        log.info("Avatar == >" + userPojo.getAvatar());
        log.info("State == >" + userPojo.getState());
        log.info("Sign == >" + userPojo.getSign());
        log.info("LoginIp == >" + userPojo.getLoginIp());
        log.info("RegIp == >" + userPojo.getRegIp());
        log.info("CreateTime == >" + userPojo.getCreateTime());
        log.info("UpdateTime == >" + userPojo.getUpdateTime());
    }
}
