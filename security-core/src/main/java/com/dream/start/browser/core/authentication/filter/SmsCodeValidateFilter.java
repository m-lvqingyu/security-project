package com.dream.start.browser.core.authentication.filter;

import com.dream.start.browser.core.authentication.handler.MyAuthenticationFailureHandler;
import com.dream.start.browser.core.constant.BrowserLoginConstant;
import com.dream.start.browser.core.exception.ValidateCodeException;
import com.dream.start.browser.core.properties.LoginProperties;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Create By 2020/9/19
 *
 * @author lvqingyu
 */
@Slf4j
@Component("smsCodeValidateFilter")
public class SmsCodeValidateFilter extends OncePerRequestFilter {

    private final LoginProperties loginProperties;
    private final MyAuthenticationFailureHandler myAuthenticationFailureHandler;

    @Autowired
    public SmsCodeValidateFilter(LoginProperties loginProperties, MyAuthenticationFailureHandler myAuthenticationFailureHandler){
        this.loginProperties = loginProperties;
        this.myAuthenticationFailureHandler = myAuthenticationFailureHandler;
    }

    /**
     * 短信验证码过滤器
     *
     * @param httpServletRequest
     * @param httpServletResponse
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String requestURI = httpServletRequest.getRequestURI();
        String method = httpServletRequest.getMethod();
        if(requestURI.equals(loginProperties.getMobileForm()) && method.equalsIgnoreCase(HttpMethod.POST.name())){
            try {
                doValidate(httpServletRequest);
            } catch (AuthenticationException e) {
                myAuthenticationFailureHandler.onAuthenticationFailure(httpServletRequest, httpServletResponse, e);
                log.error("短信验证码校验失败，异常信息:{}", Throwables.getStackTraceAsString(e));
                return;
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private void doValidate(HttpServletRequest request) throws ValidateCodeException{
        String code = request.getParameter("code");
        if(StringUtils.isBlank(code)){
            throw new ValidateCodeException("短信验证码不能为空");
        }
        HttpSession session = request.getSession();
        String sessionCode = (String) session.getAttribute(BrowserLoginConstant.SESSION_SMS_CODE_KEY);
        if(StringUtils.isBlank(sessionCode)){
            throw new ValidateCodeException("短信验证码已过期");
        }
        if(!code.equals(sessionCode)){
            throw new ValidateCodeException("短信验证码不正确");
        }
        session.removeAttribute(BrowserLoginConstant.SESSION_SMS_CODE_KEY);
    }
}
