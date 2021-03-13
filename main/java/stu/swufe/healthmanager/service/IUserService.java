package stu.swufe.healthmanager.service;

import stu.swufe.healthmanager.pojo.UserPojo;
import stu.swufe.healthmanager.response.ResponseResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface IUserService {

    /**
     * 初始化管理员
     * @param userPojo
     * @param request
     * @return
     */
    ResponseResult initManagerAccount(UserPojo userPojo, HttpServletRequest request);


    /**
     * 获得图形验证码
     * @param response
     * @param captchakey
     * @throws IOException
     */
    void createCaptcha(HttpServletResponse response, String captchakey) throws IOException;


    /**
     * 用户注册
     * @param userPojo
     * @param captchacode
     * @param captchakey
     * @param request
     * @return
     */
    ResponseResult register(UserPojo userPojo, String captchacode, String captchakey, HttpServletRequest request);


    /**
     * 用户登录
     * @param captchacode
     * @param captchakey
     * @param userPojo
     * @return
     */
    ResponseResult dologin(String captchacode, String captchakey, UserPojo userPojo);

    /**
     * 检验Token，并返回用户对象
     * @param tokenkey
     * @return
     */
    UserPojo checkUser(StringBuffer tokenkey);

    /**
     * 获得用户信息
     * @param userid
     * @return
     */
    ResponseResult getUserInfo(String userid);


    ResponseResult getUserInfo(StringBuffer tokenKey);



    /**
     * 检测用户名是否被注册过(仅在注册时考虑)
     * @param userName
     * @return
     */
    ResponseResult checkUserName(String userName);

    /**
     * 更新用户信息
     * @param userid
     * @param tokenKey
     * @param userPojo
     * @return
     */
    ResponseResult updateUserInfo(String userid, StringBuffer tokenKey, UserPojo userPojo);


    ResponseResult updatePassword(String userId, StringBuffer tokenKey, UserPojo userPojo);

    /**
     * 退出登录
     * @param userId
     * @param tokenKey
     * @return
     */
    ResponseResult logout(String userId, String tokenKey);
}
