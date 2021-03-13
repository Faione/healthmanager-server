package stu.swufe.healthmanager.controller.user;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import stu.swufe.healthmanager.pojo.UserPojo;
import stu.swufe.healthmanager.response.ResponseResult;
import stu.swufe.healthmanager.service.IUserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/user")
@Log4j
public class UserAPI {

    @Autowired
    private IUserService userService;

    /**
     * 初始化管理员账户
     * @param userPojo
     * @param request
     * @return
     */
    @PostMapping("/admin_account")
    public ResponseResult initManagerAccount(@RequestBody UserPojo userPojo, HttpServletRequest request){


        return userService.initManagerAccount(userPojo, request);
    }

    /**
     * 注册账户
     * @param userPojo
     * @return
     */
    @PostMapping
    public ResponseResult register(@RequestBody UserPojo userPojo,
                                   @RequestParam("captcha_code") String captchaCode,
                                   @RequestParam("captcha_key") String captchaKey,
                                   HttpServletRequest request){

        return userService.register(userPojo, captchaCode, captchaKey, request);
    }

    /**
     * 用户登录
     * @param captchaCode
     * @param captchaKey
     * @param userPojo
     * @return
     */
    @PostMapping("/{captcha_code}/{captcha_key}")
    public ResponseResult login(@PathVariable("captcha_code") String captchaCode,
                                @PathVariable("captcha_key") String captchaKey ,
                                @RequestBody UserPojo userPojo){
        return userService.dologin(captchaCode, captchaKey, userPojo);
    }

    /**
     * 获得图灵验证码
     * @return
     */
    @GetMapping("/captcha")
    public void getCaptcha(HttpServletResponse response, @RequestParam("captcha_key") String captchaKey){

        try {
            userService.createCaptcha(response, captchaKey);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送邮件
     * @param email
     * @return
     */
    @GetMapping("/verify_code")
    public ResponseResult sendVerifyCode(@RequestParam("email") String email){
        log.info("email == > " + email);
        return null;
    }

    /**
     * 修改密码
     * @param userPojo
     * @return
     */
    @PutMapping("/password/{user_id}/{token_key}")
    public ResponseResult updatePassword(@PathVariable("user_id")String userId,
                                         @PathVariable("token_key") String tokenKey,
                                         @RequestBody UserPojo userPojo){

        StringBuffer tokenKeyBuffer = new StringBuffer(tokenKey);
        return userService.updatePassword(userId, tokenKeyBuffer, userPojo);
    }

    /**
     * 获得用户（管理员：from Mysql）
     * @param userid
     * @return
     */
    @GetMapping("/{user_id}")
    public ResponseResult getUserInfoByID(@PathVariable("user_id") String userid){
        log.info("getUserInfo->" + "Fuck!");

        return userService.getUserInfo(userid);
    }

    /**
     * 获得用户信息（用户：from Redis）
     * @param tokenKey
     * @return
     */
    @GetMapping("/user_info/{token_key}")
    public ResponseResult getUserInfoByToken(@PathVariable("token_key") String tokenKey){
        StringBuffer tokenKeyBuffer = new StringBuffer(tokenKey);
        return userService.getUserInfo(tokenKeyBuffer);
    }

    @GetMapping("/user_name")
    public ResponseResult checkUserName(@RequestParam("user_name") String userName){
        return userService.checkUserName(userName);
    }

    /**
     * 更新用户信息
     * @param userPojo
     * @return
     */
    @PutMapping("/{user_id}")
    public ResponseResult updateUserInfo(@PathVariable("user_id")String userid,
                                         @RequestParam("token_key") String tokenKey,
                                         @RequestBody UserPojo userPojo){
        StringBuffer tokenKeyBuffer = new StringBuffer(tokenKey);
        return userService.updateUserInfo(userid, tokenKeyBuffer, userPojo);
    }

    /**
     * 获得用户列表
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/user_list")
    public ResponseResult listUsers(@RequestParam("page") int page, @RequestParam("size") int size){
        return null;
    }

    /**
     * 删除用户
     * @param userId
     * @return
     */
    @DeleteMapping("/{user_id}")
    public ResponseResult deleteUser(@PathVariable("user_id") String userId){
        return null;
    }

    @DeleteMapping("")
    public ResponseResult logout(@RequestParam("user_id") String userId,
                                 @RequestParam("tokenKey") String tokenKey){

        return userService.logout(userId, tokenKey);

    }
}
