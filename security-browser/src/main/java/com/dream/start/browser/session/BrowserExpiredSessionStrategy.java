package com.dream.start.browser.session;

import com.dream.start.browser.core.authentication.handler.MyAuthenticationFailureHandler;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * @description: <pre>
 * </pre>
 * @author: Lvqingyu
 * @create: 2020/09/21 17:34
 */
@Slf4j
@Component("browserExpiredSessionStrategy")
public class BrowserExpiredSessionStrategy implements SessionInformationExpiredStrategy {

    private final MyAuthenticationFailureHandler myAuthenticationFailureHandler;

    @Autowired
    public BrowserExpiredSessionStrategy(MyAuthenticationFailureHandler myAuthenticationFailureHandler){
        this.myAuthenticationFailureHandler = myAuthenticationFailureHandler;
    }

    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent sessionInformationExpiredEvent) throws IOException {
        AuthenticationException exception = new AuthenticationServiceException("账户已在其它计算机登录");
        try {
            sessionInformationExpiredEvent.getRequest().setAttribute("toAuthentication", true);
            myAuthenticationFailureHandler.onAuthenticationFailure(
                    sessionInformationExpiredEvent.getRequest(),
                    sessionInformationExpiredEvent.getResponse(),
                    exception);
        } catch (ServletException e) {
            log.error("登录过期-服务处理异常，异常信息：{}", Throwables.getStackTraceAsString(e));
        }
    }
}
