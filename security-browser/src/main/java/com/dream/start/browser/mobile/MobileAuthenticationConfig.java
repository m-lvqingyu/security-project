package com.dream.start.browser.mobile;

import com.dream.start.browser.authentication.BrowserAuthenticationFailureHandler;
import com.dream.start.browser.authentication.BrowserAuthenticationSuccessHandler;
import com.dream.start.browser.service.MobileUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

/**
 * @description: <pre>
 * </pre>
 * @author: Lvqingyu
 * @create: 2020/09/20 21:52
 */
@Component
public class MobileAuthenticationConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private final BrowserAuthenticationSuccessHandler browserAuthenticationSuccessHandler;
    private final BrowserAuthenticationFailureHandler browserAuthenticationFailureHandler;
    private final MobileUserDetailsService mobileUserDetailsService;

    @Autowired
    public MobileAuthenticationConfig(BrowserAuthenticationSuccessHandler browserAuthenticationSuccessHandler,
                                      BrowserAuthenticationFailureHandler browserAuthenticationFailureHandler,
                                      MobileUserDetailsService mobileUserDetailsService) {
        this.browserAuthenticationFailureHandler = browserAuthenticationFailureHandler;
        this.browserAuthenticationSuccessHandler = browserAuthenticationSuccessHandler;
        this.mobileUserDetailsService = mobileUserDetailsService;
    }


    @Override
    public void configure(HttpSecurity builder) throws Exception {
        MobileAuthenticationFilter mobileAuthenticationFilter = new MobileAuthenticationFilter();
        mobileAuthenticationFilter.setAuthenticationManager(builder.getSharedObject(AuthenticationManager.class));
        mobileAuthenticationFilter.setAuthenticationSuccessHandler(browserAuthenticationSuccessHandler);
        mobileAuthenticationFilter.setAuthenticationFailureHandler(browserAuthenticationFailureHandler);

        MobileAuthenticationProvider provider = new MobileAuthenticationProvider();
        provider.setUserDetailsService(mobileUserDetailsService);

        builder.authenticationProvider(provider).addFilterAfter(mobileAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
