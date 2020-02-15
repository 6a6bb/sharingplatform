package com.littlea.sharingplatform.security;

import com.littlea.sharingplatform.constant.ResultConstant;
import com.littlea.sharingplatform.security.util.ErrorHandlerUtil;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author chenqiting
 */
@Component
public class MyAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        ErrorHandlerUtil.printError(httpServletResponse, ResultConstant.PERMISSION_DENIED, 401);
    }
}
