package com.littlea.sharingplatform.service;

import com.littlea.sharingplatform.bo.UserLoginBo;
import com.littlea.sharingplatform.entity.User;
import com.littlea.sharingplatform.vo.Result;

/**
 * @author chenqiting
 */
public interface UserService {
    /**
     * 功能描述
     * @param userLoginBo
     * @return 登录成功的结果
     * @author chenqiting
     * @date 2020/2/10 15:57
     */
     Result login(UserLoginBo userLoginBo);
     /**
      * 功能描述
      * @param user user的个人注册信息
      * @return  注册成功返回的结果
      * @author chenqiting
      * @date 2020/2/11 12:39
      */
     Result register(User user);

    /***
     * 发送email
     * @param email 参数
     * @param result 验证码
     * @return 返回email的验证码
     */
     Result sendEmailForRegister(String email, int result);

    /***
     * adasd
     * @param validationCode
     * @param email
     */
    void sendEmailSender(int validationCode, String email);

    /***
     * 注销
     * @param userId 参数
     * @return 返回成功与否
     */
     Result logout(Integer userId);

    /***
     * 修改密码
     * @param password
     * @param userId
     * @return 返回成功与否
     */
    Result updatePassword(String password, Integer userId);

}
