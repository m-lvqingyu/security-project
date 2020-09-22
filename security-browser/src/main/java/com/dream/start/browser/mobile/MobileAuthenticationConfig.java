package com.dream.start.browser.mobile;

import com.dream.start.browser.core.authentication.handler.MyAuthenticationFailureHandler;
import com.dream.start.browser.core.authentication.handler.MyAuthenticationSuccessHandler;
import com.dream.start.browser.users.MobileUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.stereotype.Component;

/**
 * @description: <pre>
 * </pre>
 * @author: Lvqingyu
 * @create: 2020/09/20 21:52
 */
@Component
public class MobileAuthenticationConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private final MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;
    private final MyAuthenticationFailureHandler myAuthenticationFailureHandler;
    private final MobileUserDetailsService mobileUserDetailsService;

    @Autowired
    public MobileAuthenticationConfig(MyAuthenticationSuccessHandler myAuthenticationSuccessHandler,
                                      MyAuthenticationFailureHandler myAuthenticationFailureHandler,
                                      MobileUserDetailsService mobileUserDetailsService) {
        this.myAuthenticationFailureHandler = myAuthenticationFailureHandler;
        this.myAuthenticationSuccessHandler = myAuthenticationSuccessHandler;
        this.mobileUserDetailsService = mobileUserDetailsService;
    }


    @Override
    public void configure(HttpSecurity builder) throws Exception {
        MobileAuthenticationFilter mobileAuthenticationFilter = new MobileAuthenticationFilter();
        // 解决正常应该是同一个用户，系统中只能用用户名或手机号登录一次的问题
        // MobileAuthenticationFilter 继承自 AbstractAuthenticationProcessingFilter，SessionAuthenticationStrategy使用的是NullAuthenticatedSessionStrategy
        // 所以与用户名和密码登录，使用CompositeSessionAuthenticationStrategy即可
        mobileAuthenticationFilter.setSessionAuthenticationStrategy(builder.getSharedObject(SessionAuthenticationStrategy.class));
        mobileAuthenticationFilter.setAuthenticationManager(builder.getSharedObject(AuthenticationManager.class));
        mobileAuthenticationFilter.setAuthenticationSuccessHandler(myAuthenticationSuccessHandler);
        mobileAuthenticationFilter.setAuthenticationFailureHandler(myAuthenticationFailureHandler);

        MobileAuthenticationProvider provider = new MobileAuthenticationProvider();
        provider.setUserDetailsService(mobileUserDetailsService);

        builder.authenticationProvider(provider).addFilterAfter(mobileAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
