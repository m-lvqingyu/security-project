package com.dream.start.browser.filter;

import com.dream.start.browser.authentication.BrowserAuthenticationFailureHandler;
import com.dream.start.browser.core.code.bo.ImageCodeBO;
import com.dream.start.browser.core.constant.BrowserSomeConstant;
import com.dream.start.browser.core.exception.ValidateCodeException;
import com.dream.start.browser.properties.BrowserLoginProperties;
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
@Component("imageCodeValidateFilter")
public class ImageCodeValidateFilter extends OncePerRequestFilter {

    @Autowired
    private BrowserLoginProperties browserLoginProperties;
    @Autowired
    private BrowserAuthenticationFailureHandler browserAuthenticationFailureHandler;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String requestURI = httpServletRequest.getRequestURI();
        String method = httpServletRequest.getMethod();
        String loginProcessingUrl = browserLoginProperties.getLoginProcessingUrl();
        if(requestURI.equals(loginProcessingUrl) &&
                method.equalsIgnoreCase(HttpMethod.POST.name())){
            try {
                doValidate(httpServletRequest);
            } catch (AuthenticationException e) {
                browserAuthenticationFailureHandler.onAuthenticationFailure(httpServletRequest, httpServletResponse, e);
                log.error("验证码校验失败，异常信息:{}", Throwables.getStackTraceAsString(e));
                return;
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private void doValidate(HttpServletRequest request) throws ValidateCodeException {
        String code = request.getParameter("code");
        if(StringUtils.isBlank(code)){
            throw new ValidateCodeException("验证码不能为空!");
        }
        HttpSession session = request.getSession();
        ImageCodeBO imageCodeBO = (ImageCodeBO) session.getAttribute(BrowserSomeConstant.SESSION_IMAGE_CODE_KEY);
        if(imageCodeBO == null){
            throw new ValidateCodeException("验证码已失效，请重新输入!");
        }
        String imageCode = imageCodeBO.getCode();
        if(StringUtils.isBlank(imageCode)){
            throw new ValidateCodeException("验证码已失效，请重新输入!");
        }
        boolean expired = imageCodeBO.isExpired();
        if(expired){
            throw new ValidateCodeException("验证码已失效，请重新输入!");
        }
        if(!code.equalsIgnoreCase(imageCode)){
            throw new ValidateCodeException("验证码不正确，请重新输入!");
        }
    }

}
