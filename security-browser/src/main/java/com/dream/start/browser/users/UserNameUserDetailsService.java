package com.dream.start.browser.users;

import com.dream.start.browser.system.pojo.TSysUserInfo;
import com.dream.start.browser.system.service.TSysUserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * Create By 2020/9/19
 *
 * @author lvqingyu
 */
@Slf4j
@Component("userNameUserDetailsService")
public class UserNameUserDetailsService implements UserDetailsService {

    @Autowired
    private TSysUserInfoService tSysUserInfoService;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        log.info("请求系统认证的用户名:{}", userName);
        TSysUserInfo userInfo = tSysUserInfoService.findUserInfoByName(userName);
        if (userInfo == null) {
            throw new UsernameNotFoundException("用户信息不存在");
        }
        String password = userInfo.getPassword();
        return new User(
                userName,
                password,
                AuthorityUtils.commaSeparatedStringToAuthorityList("ADMIN"));
    }
}
