package com.dream.start.browser.config;

import com.dream.start.browser.authentication.BrowserAuthenticationFailureHandler;
import com.dream.start.browser.authentication.BrowserAuthenticationSuccessHandler;
import com.dream.start.browser.filter.ImageCodeValidateFilter;
import com.dream.start.browser.filter.SmsCodeValidateFilter;
import com.dream.start.browser.mobile.MobileAuthenticationConfig;
import com.dream.start.browser.properties.BrowserLoginProperties;
import com.dream.start.browser.service.UserNameUserDetailsService;
import com.dream.start.browser.session.BrowserExpiredSessionStrategy;
import com.dream.start.browser.session.BrowserInvalidSessionStrategy;
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
import org.springframework.security.web.session.InvalidSessionStrategy;

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

    @Autowired
    private BrowserLoginProperties browserProperties;
    @Autowired
    private BrowserAuthenticationFailureHandler browserAuthenticationFailureHandler;
    @Autowired
    private BrowserAuthenticationSuccessHandler browserAuthenticationSuccessHandler;
    @Autowired
    private ImageCodeValidateFilter imageCodeValidateFilter;
    @Autowired
    private UserNameUserDetailsService userNameUserDetailsService;
    @Autowired
    private SmsCodeValidateFilter smsCodeValidateFilter;
    @Autowired
    private MobileAuthenticationConfig mobileAuthenticationConfig;
    @Autowired
    private BrowserInvalidSessionStrategy browserInvalidSessionStrategy;
    @Autowired
    private BrowserExpiredSessionStrategy browserExpiredSessionStrategy;

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
        web.ignoring().antMatchers(browserProperties.getIgnoringMatchersPath());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        String defaultLoginPage = browserProperties.getLoginPage();
        String defaultCodeImage = browserProperties.getCodeImage();
        String defaultMobileLoginUrl = browserProperties.getMobileLoginUrl();
        String defaultCodeSms = browserProperties.getCodeSms();
        String defaultLoginProcessingUrl = browserProperties.getLoginProcessingUrl();
        String defaultLoginUserName = browserProperties.getLoginUserName();
        String defaultLoginPassword = browserProperties.getLoginPassword();
        http.addFilterBefore(smsCodeValidateFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(imageCodeValidateFilter, UsernamePasswordAuthenticationFilter.class)
                .formLogin()
                .loginPage(defaultLoginPage)
                .loginProcessingUrl(defaultLoginProcessingUrl)
                .usernameParameter(defaultLoginUserName)
                .passwordParameter(defaultLoginPassword)
                .successHandler(browserAuthenticationSuccessHandler)
                .failureHandler(browserAuthenticationFailureHandler)
                .and().authorizeRequests().antMatchers(defaultLoginPage, defaultCodeImage, defaultMobileLoginUrl, defaultCodeSms).permitAll()
                .anyRequest().authenticated()
                .and()
                // Session失效后处理
                .sessionManagement().invalidSessionStrategy(browserInvalidSessionStrategy)
                // 同一用户只能在一台计算机上登录
                .maximumSessions(1).expiredSessionStrategy(browserExpiredSessionStrategy)
                // 同一用户只能在一台计算机上登录，未退出时，不允许登录
                .maxSessionsPreventsLogin(true);
        http.apply(mobileAuthenticationConfig);
    }
}
