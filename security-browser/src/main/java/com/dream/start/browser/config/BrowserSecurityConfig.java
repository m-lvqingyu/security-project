package com.dream.start.browser.config;

import com.dream.start.browser.core.authentication.filter.ImageCodeValidateFilter;
import com.dream.start.browser.core.authentication.filter.SmsCodeValidateFilter;
import com.dream.start.browser.core.authentication.handler.MyAuthenticationFailureHandler;
import com.dream.start.browser.core.authentication.handler.MyAuthenticationSuccessHandler;
import com.dream.start.browser.core.properties.LoginProperties;
import com.dream.start.browser.mobile.MobileAuthenticationConfig;
import com.dream.start.browser.session.BrowserExpiredSessionStrategy;
import com.dream.start.browser.session.BrowserInvalidSessionStrategy;
import com.dream.start.browser.users.UserNameUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Create By 2020/9/13
 * <p>
 * 浏览器安全认证中心
 *
 * @author lvqingyu
 */
@Configuration
@EnableWebSecurity
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {

    private final LoginProperties loginProperties;
    private final MyAuthenticationFailureHandler myAuthenticationFailureHandler;
    private final MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;
    private final ImageCodeValidateFilter imageCodeValidateFilter;
    private final UserNameUserDetailsService userNameUserDetailsService;
    private final SmsCodeValidateFilter smsCodeValidateFilter;
    private final MobileAuthenticationConfig mobileAuthenticationConfig;
    private final BrowserInvalidSessionStrategy browserInvalidSessionStrategy;
    private final BrowserExpiredSessionStrategy browserExpiredSessionStrategy;

    @Autowired
    public BrowserSecurityConfig(LoginProperties loginProperties,
                                 MyAuthenticationFailureHandler myAuthenticationFailureHandler,
                                 MyAuthenticationSuccessHandler myAuthenticationSuccessHandler,
                                 ImageCodeValidateFilter imageCodeValidateFilter,
                                 UserNameUserDetailsService userNameUserDetailsService,
                                 SmsCodeValidateFilter smsCodeValidateFilter,
                                 MobileAuthenticationConfig mobileAuthenticationConfig,
                                 BrowserInvalidSessionStrategy browserInvalidSessionStrategy,
                                 BrowserExpiredSessionStrategy browserExpiredSessionStrategy) {
        this.loginProperties = loginProperties;
        this.myAuthenticationFailureHandler = myAuthenticationFailureHandler;
        this.myAuthenticationSuccessHandler = myAuthenticationSuccessHandler;
        this.imageCodeValidateFilter = imageCodeValidateFilter;
        this.userNameUserDetailsService = userNameUserDetailsService;
        this.smsCodeValidateFilter = smsCodeValidateFilter;
        this.mobileAuthenticationConfig = mobileAuthenticationConfig;
        this.browserInvalidSessionStrategy = browserInvalidSessionStrategy;
        this.browserExpiredSessionStrategy = browserExpiredSessionStrategy;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 认证管理器
     * 1.认证信息提供方式（用户名、密码、当前用户的资源权限）
     * 2.可采用内存存储方式，也可采用数据库方式等
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userNameUserDetailsService);
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(loginProperties.getIgnoringMatchersPath());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        String defaultLoginPage = loginProperties.getLoginPage();
        String defaultCodeImage = loginProperties.getCodeImage();
        String defaultMobileLoginUrl = loginProperties.getMobileLoginUrl();
        String defaultCodeSms = loginProperties.getCodeSms();
        String defaultLoginProcessingUrl = loginProperties.getLoginProcessingUrl();
        String defaultLoginUserName = loginProperties.getLoginUserName();
        String defaultLoginPassword = loginProperties.getLoginPassword();
        http.addFilterBefore(smsCodeValidateFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(imageCodeValidateFilter, UsernamePasswordAuthenticationFilter.class)
                .formLogin()
                .loginPage(defaultLoginPage)
                .loginProcessingUrl(defaultLoginProcessingUrl)
                .usernameParameter(defaultLoginUserName)
                .passwordParameter(defaultLoginPassword)
                .successHandler(myAuthenticationSuccessHandler)
                .failureHandler(myAuthenticationFailureHandler)
                .and()
                .authorizeRequests().antMatchers(defaultLoginPage, defaultCodeImage, defaultMobileLoginUrl, defaultCodeSms).permitAll()
                .anyRequest().authenticated()
                .and()
                // Session失效后处理
                .sessionManagement().invalidSessionStrategy(browserInvalidSessionStrategy)
                // 同一用户只能在一台计算机上登录
                .maximumSessions(1).expiredSessionStrategy(browserExpiredSessionStrategy)
                // 同一用户只能在一台计算机上登录，未退出时，不允许登录
                .maxSessionsPreventsLogin(true);
        http.csrf().disable();
        http.apply(mobileAuthenticationConfig);

    }
}
