package com.dream.start.browser.core.authentication.handler;

import com.dream.start.browser.core.constant.ResultUtilsConstant;
import com.dream.start.browser.core.enums.LoginResponseType;
import com.dream.start.browser.core.properties.LoginProperties;
import com.dream.start.browser.core.utils.ResultUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
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
@Component("myAuthenticationFailureHandler")
public class MyAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private final ObjectMapper objectMapper;
    private final LoginProperties loginProperties;

    @Autowired
    public MyAuthenticationFailureHandler(LoginProperties loginProperties, ObjectMapper objectMapper){
        this.objectMapper = objectMapper;
        this.loginProperties = loginProperties;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        LoginResponseType loginType = loginProperties.getLoginType();
        if(LoginResponseType.JSON.equals(loginType)){
            ResultUtil<String> failure = ResultUtil.failure(ResultUtilsConstant.AUTHENTICATION_FAILURE);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(failure));
        } else {
            Object toAuthentication = request.getAttribute("toAuthentication");
            String loginPage = loginProperties.getLoginPage();
            String referer = request.getHeader("Referer");
            if(toAuthentication != null){
                super.setDefaultFailureUrl(loginPage + "?error");
            } else if(StringUtils.isBlank(referer)) {
                super.setDefaultFailureUrl(loginPage);
            } else {
                referer = StringUtils.substringBefore(referer, "?");
                super.setDefaultFailureUrl(referer + "?error");
            }
            super.onAuthenticationFailure(request, response, exception);
        }
    }

}
