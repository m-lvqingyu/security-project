package com.dream.start.browser.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * @description: <pre>
 * </pre>
 * @author: Lvqingyu
 * @create: 2020/09/20 21:46
 */
@Slf4j
@Component("mobileUserDetailsService")
public class MobileUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String mobile) throws UsernameNotFoundException {
        log.info("请求认证的用户手机号是：{}", mobile);
        return new User(
                mobile,
                "",
                true,
                true,
                true,
                true,
                AuthorityUtils.commaSeparatedStringToAuthorityList("ADMIN"));
    }
}
