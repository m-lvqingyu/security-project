package com.dream.start.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

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
        // 用户信息存储在内存中 admin - 1234
        String password = passwordEncoder().encode("1234");
        auth.inMemoryAuthentication().withUser("admin").password(password).authorities("ADMIN");
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/dist/**", "/modules/**", "/plugins/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .loginPage("/login/page")
                .loginProcessingUrl("/login/form")
                .usernameParameter("name")
                .passwordParameter("pwd")
                .and()
                .authorizeRequests().antMatchers("/login/page").permitAll()
                .anyRequest().authenticated().and().csrf().disable();
    }
}
