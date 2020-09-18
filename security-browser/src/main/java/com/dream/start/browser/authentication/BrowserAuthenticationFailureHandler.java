package com.dream.start.browser.authentication;

import com.dream.start.browser.core.constant.ResultUtilsConstant;
import com.dream.start.browser.core.enums.LoginResponseType;
import com.dream.start.browser.core.utils.ResultUtil;
import com.dream.start.browser.properties.BrowserLoginProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @description: <pre>
 * </pre>
 * @author: Lvqingyu
 * @create: 2020/09/18 15:17
 */
@Component("browserAuthenticationFailureHandler")
public class BrowserAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Autowired
    private BrowserLoginProperties browserLoginProperties;

    @Autowired
    private ObjectMapper objectMapper;


    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        LoginResponseType loginType = browserLoginProperties.getLoginType();
        if(LoginResponseType.JSON.equals(loginType)){
            ResultUtil<String> failure = ResultUtil.failure(ResultUtilsConstant.AUTHENTICATION_FAILURE);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(failure));
        } else {
            super.onAuthenticationFailure(request, response, exception);
        }
    }

}
