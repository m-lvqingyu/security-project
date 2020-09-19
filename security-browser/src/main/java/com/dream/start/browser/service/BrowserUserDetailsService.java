package com.dream.start.browser.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Create By 2020/9/19
 *
 * @author lvqingyu
 */
@Slf4j
@Component("browserUserDetailsService")
public class BrowserUserDetailsService implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        log.info("请求系统认证的用户名:{}", s);
        if (!s.equalsIgnoreCase("admin")) {
            throw new UsernameNotFoundException("用户信息不存在");
        }
        String password = passwordEncoder.encode("1234");
        return new User(
                s,
                password,
                AuthorityUtils.commaSeparatedStringToAuthorityList("ADMIN"));
    }
}
