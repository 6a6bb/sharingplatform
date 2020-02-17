package com.littlea.sharingplatform.controller;

import com.littlea.sharingplatform.bo.UserLoginBo;
import com.littlea.sharingplatform.constant.ResultConstant;
import com.littlea.sharingplatform.entity.User;
import com.littlea.sharingplatform.service.UserService;
import com.littlea.sharingplatform.vo.Result;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

/**
 * @author chenqiting
 */
@RestController
@AllArgsConstructor
public class UserController {

    private UserService userService;

    @PostMapping("/api/user/login")
    public Result login(@RequestBody UserLoginBo userLoginBo){
        return userService.login(userLoginBo);
    }

    @PostMapping("/api/user/register")
    public Result register(@RequestBody User user){
        return userService.register(user);
    }

    @GetMapping("/api/user/email/send")
    public Result sendMail(String email){
        int result = (int)((Math.random() * 9 + 1) * 100000);
        Result ans = userService.sendEmailForRegister(email, result);
        if (!ResultConstant.EMAIL_HOST_EXIST.equals(ans)){
            userService.sendEmailSender(result, email);
        }
        return ans;
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/api/userAuthority/logout")
    public Result logout(UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken){
        return userService.logout(Integer.valueOf(usernamePasswordAuthenticationToken.getName()));
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/api/userAuthority/password/update")
    public Result updatePassword(@RequestBody String password, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) {
        return userService.updatePassword(password, Integer.valueOf(usernamePasswordAuthenticationToken.getName()));
    }
}
