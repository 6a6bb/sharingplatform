package com.littlea.sharingplatform.security;

import com.alibaba.fastjson.JSONObject;
import com.littlea.sharingplatform.constant.ResultConstant;
import com.littlea.sharingplatform.constant.SystemConstant;
import com.littlea.sharingplatform.entity.UserInformation;
import com.littlea.sharingplatform.security.util.ErrorHandlerUtil;
import com.littlea.sharingplatform.security.util.JwtUtil;
import com.littlea.sharingplatform.security.util.RedisUtil;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author chenqiting
 */
public class CustomerFilter extends BasicAuthenticationFilter {

    public CustomerFilter(AuthenticationManager authenticationManager, RedisUtil redisUtil, JwtUtil jwtUtil) {
        super(authenticationManager);
        this.jwtUtil = jwtUtil;
        this.redisUtil = redisUtil;
    }

    private JwtUtil jwtUtil;

    private RedisUtil redisUtil;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
            String token = request.getHeader(SystemConstant.HEAD);
            //判断header是否合法
            if (!StringUtils.isEmpty(token) && token.startsWith(SystemConstant.BEARER)){
                //获取jwt
                String jwt = token.substring(SystemConstant.BEARER.length());
                //获取userName
                String uuid = jwtUtil.getUuid(jwt);
                //获取userId
                Integer userId = jwtUtil.getUserId(jwt);

                if (Objects.nonNull(uuid)){
                    String userInformationJsonString = redisUtil.get(SystemConstant.AUTHORIZATION_PREFIX + userId);
                    //判断是否从数据库里获取成功
                    if (!StringUtils.isEmpty(userInformationJsonString)){
                        UserInformation userInformation = JSONObject
                                        .parseObject(userInformationJsonString, UserInformation.class);
                        //在redis得到用户的细粒化控制权限转换为集合
                        Set<GrantedAuthority> authorityHashSet = null;
                        //对全部字符串要加上前缀
                        if (Objects.nonNull(userInformation.getPermission())){
                            authorityHashSet = new HashSet<>(userInformation.getPermission().size());
                            //加上ROLE_前缀
                            for (String authorityString : userInformation.getPermission()){
                                GrantedAuthority authority = new MyGrantedAuthority("ROLE_" + authorityString);
                                authorityHashSet.add(authority);
                            }
                        }
                        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                                new UsernamePasswordAuthenticationToken(userId, null, authorityHashSet);
                        // 设置具体的detail与否根据功能需求可以自定义设置details,即后台自定义传导数据
                        usernamePasswordAuthenticationToken.setDetails(userInformation);
                        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                        chain.doFilter(request, response);
                        return;
                    }
                }
            }
            logger.info("未授权");
            ErrorHandlerUtil.printError(response, ResultConstant.LOGIN_ERROR, 401);
    }

}
