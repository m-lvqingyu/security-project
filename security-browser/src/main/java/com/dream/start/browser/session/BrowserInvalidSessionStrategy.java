package com.dream.start.browser.session;

import com.dream.start.browser.core.authentication.handler.MyAuthenticationFailureHandler;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @description: <pre>
 * {@link }
 * </pre>
 * @author: Lvqingyu
 * @create: 2020/09/21 16:19
 */
@Slf4j
@Component("browserInvalidSessionStrategy")
public class BrowserInvalidSessionStrategy implements InvalidSessionStrategy {

    private final MyAuthenticationFailureHandler myAuthenticationFailureHandler;

    @Autowired
    public BrowserInvalidSessionStrategy(MyAuthenticationFailureHandler myAuthenticationFailureHandler){
        this.myAuthenticationFailureHandler = myAuthenticationFailureHandler;
    }

    @Override
    public void onInvalidSessionDetected(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
        cancelCookie(httpServletRequest, httpServletResponse);
        AuthenticationException exception = new AuthenticationServiceException("登录已过期");
        try {
            myAuthenticationFailureHandler.onAuthenticationFailure(httpServletRequest, httpServletResponse, exception);
        } catch (ServletException e) {
            log.error("登录过期-服务处理异常，异常信息：{}", Throwables.getStackTraceAsString(e));
        }
    }

    protected void cancelCookie(HttpServletRequest request, HttpServletResponse response) {
        Cookie cookie = new Cookie("JSESSIONID", null);
        cookie.setMaxAge(0);
        cookie.setPath(getCookiePath(request));
        response.addCookie(cookie);
    }

    private String getCookiePath(HttpServletRequest request) {
        String contextPath = request.getContextPath();
        return contextPath.length() > 0 ? contextPath : "/";
    }


}
