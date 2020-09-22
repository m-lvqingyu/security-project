package com.dream.start.browser.core.authentication.handler;

import com.dream.start.browser.core.enums.LoginResponseType;
import com.dream.start.browser.core.properties.LoginProperties;
import com.dream.start.browser.core.utils.ResultUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @description: <pre>
 * </pre>
 * @author: Lvqingyu
 * @create: 2020/09/18 11:38
 */
@Component("myAuthenticationSuccessHandler")
public class MyAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final ObjectMapper objectMapper;
    private final LoginProperties loginProperties;

    @Autowired
    public MyAuthenticationSuccessHandler(ObjectMapper objectMapper, LoginProperties loginProperties){
        this.objectMapper = objectMapper;
        this.loginProperties = loginProperties;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        LoginResponseType loginType = loginProperties.getLoginType();
        if (LoginResponseType.JSON.equals(loginType)) {
            ResultUtil<Authentication> resultUtil = ResultUtil.success(authentication);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(resultUtil));
        } else {
            super.onAuthenticationSuccess(request, response, authentication);
        }
    }

}
