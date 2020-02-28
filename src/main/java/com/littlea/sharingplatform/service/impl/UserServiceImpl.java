package com.littlea.sharingplatform.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.littlea.sharingplatform.bo.UserLoginBo;
import com.littlea.sharingplatform.bo.UserLoginResultBo;
import com.littlea.sharingplatform.constant.ResultConstant;
import com.littlea.sharingplatform.constant.RoleConstant;
import com.littlea.sharingplatform.constant.SystemConstant;
import com.littlea.sharingplatform.entity.User;
import com.littlea.sharingplatform.entity.UserInformation;
import com.littlea.sharingplatform.entity.UserRole;
import com.littlea.sharingplatform.mapper.PermissionMapper;
import com.littlea.sharingplatform.mapper.UserMapper;
import com.littlea.sharingplatform.mapper.UserRoleMapper;
import com.littlea.sharingplatform.security.util.JwtUtil;
import com.littlea.sharingplatform.security.util.RedisUtil;
import com.littlea.sharingplatform.service.UserService;
import com.littlea.sharingplatform.vo.Result;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.Objects;
import java.util.Set;

/**
 * @author chenqiting
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    @NonNull
    private PasswordEncoder passwordEncoder;
    @NonNull
    private UserMapper userMapper;
    @NonNull
    private JwtUtil jwtUtil;
    @NonNull
    private RedisUtil redisUtil;
    @NonNull
    private PermissionMapper permissionMapper;
    @NonNull
    private UserRoleMapper userRoleMapper;
    @NonNull
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String emailSender;

    @Override
    public Result login(UserLoginBo userLoginBo) {
        //检查参数是否错误
        if (Objects.isNull(userLoginBo.getPassword()) || Objects.isNull(userLoginBo.getUsername())){
            return ResultConstant.ARGS_ERROR;
        }
        //从数据库获取该用户的数据
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("user_name", userLoginBo.getUsername()));
        //账号不存在或者密码错误，都直接返回
        if (Objects.isNull(user) || !passwordEncoder.matches(userLoginBo.getPassword(), user.getUserPassword())){
            return ResultConstant.PASSWORD_ERROR;
        }
        Integer roleId = userRoleMapper.selectByUserId(user.getId());
        //查询该用户的权限
        Set<String> authorities = permissionMapper.selectByRoleId(roleId);
        //此时账号信息都对，开始生成token
        String token = SystemConstant.BEARER + jwtUtil.createToken(user.getUserName(), user.getId());
        //包装信息存储
        UserInformation userInformation = new UserInformation(user.getId(), roleId,  authorities);
        //将userInformation存储进redis
        redisUtil.set(SystemConstant.AUTHORIZATION_PREFIX + user.getId(), JSONObject.toJSONString(userInformation));

        return new Result<>(new UserLoginResultBo(roleId, token));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result register(User user) {
        if (Objects.isNull(user.getEmail()) || Objects.isNull(user.getTeamGroup())
                || Objects.isNull(user.getUserPassword()) || Objects.isNull(user.getUserName())){
                    return ResultConstant.ARGS_ERROR;
        }
        //先去user里查看是否存在username相同的情况
        User oldUser = userMapper.selectOne(new QueryWrapper<User>().eq("user_name", user.getUserName()));
        if (Objects.nonNull(oldUser)) {
            return ResultConstant.USERNAME_EXIST;
        }
        String password = passwordEncoder.encode(user.getUserPassword());
        //设置加密后的密码
        try {
            user.setUserPassword(password);
            userMapper.insert(user);
            //分配角色权限
            UserRole userRole = new UserRole(null, user.getId(), RoleConstant.RAND_MEMBER);
            userRoleMapper.insert(userRole);
        }catch (Exception e){
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResultConstant.SYSTEM_ERROR;
        }
            //返回成功
        return ResultConstant.SUCCESS;
    }

    @Override
    public Result sendEmailForRegister(String email, int result) {
        //查询一下邮箱是否已经被注册了
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("email", email));
        if (Objects.nonNull(user)) {
            return ResultConstant.EMAIL_HOST_EXIST;
        }
        return new Result<>(result);
    }
    @Async("emailExecutor")
    @Override
    public void sendEmailSender(int validationCode, String email) {
        SimpleMailMessage message = new SimpleMailMessage();
        //生成验证码
        message.setFrom(this.emailSender);
        message.setText("您的验证码是：" + validationCode);
        message.setSubject("小A共享平台");
        message.setTo(email);
        //进行邮件发送
        javaMailSender.send(message);
    }

    @Override
    public Result logout(Integer userId) {
        redisUtil.delete(SystemConstant.AUTHORIZATION_PREFIX + userId);
        return ResultConstant.SUCCESS;
    }

    @Override
    public Result updatePassword(String password, Integer userId) {
        //加密处理
        password = passwordEncoder.encode(password);
        User user = new User();
        user.setId(userId);
        user.setUserPassword(password);

        int success = userMapper.updateById(user);
        if (success == 1){
            redisUtil.delete(SystemConstant.AUTHORIZATION_PREFIX + userId);
            return ResultConstant.SUCCESS;
        }
        return ResultConstant.SYSTEM_ERROR;
    }

}
