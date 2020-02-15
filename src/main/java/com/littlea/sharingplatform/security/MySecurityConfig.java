package com.littlea.sharingplatform.security;

import com.littlea.sharingplatform.security.util.JwtUtil;
import com.littlea.sharingplatform.security.util.RedisUtil;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author chenqiting
 */
@AllArgsConstructor
@Configuration
//启动安全类
@EnableWebSecurity
//可以在控制层上放上注解确定是否具有权限
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MySecurityConfig extends WebSecurityConfigurerAdapter {

    private MyAccessDeniedHandler myAccessDeniedHandler;
    private MyAuthenticationEntryPoint myAuthenticationEntryPoint;
    private RedisUtil redisUtil;
    private JwtUtil jwtUtil;

    /**
     * 重写PasswordEncoder  接口中的方法，实例化加密策略
     * @return 返回 BCrypt 加密策略
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/api/user/**");
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //表单登录 方式
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER)//服务端不创建session
                .and()
                .authorizeRequests()
                    .antMatchers("/api/user/**").permitAll()
                    .antMatchers("/api/**").authenticated()
                .and()
                .addFilter(new CustomerFilter(authenticationManager(), redisUtil, jwtUtil))
                .exceptionHandling()
                .accessDeniedHandler(myAccessDeniedHandler)
                .authenticationEntryPoint(myAuthenticationEntryPoint);

    }





}
