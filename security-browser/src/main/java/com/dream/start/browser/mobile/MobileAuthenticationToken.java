package com.dream.start.browser.mobile;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @description: <pre>
 *     {@link org.springframework.security.authentication.UsernamePasswordAuthenticationToken}
 * </pre>
 * @author: Lvqingyu
 * @create: 2020/09/20 21:16
 */

public class MobileAuthenticationToken extends AbstractAuthenticationToken {

    /**
     * 认证之前是手机号，认证之后存放用户信息
     */
    private final Object principal;

    public MobileAuthenticationToken(Object principal) {
        super((Collection) null);
        this.principal = principal;
        this.setAuthenticated(false);
    }

    public MobileAuthenticationToken(Object principal, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        super.setAuthenticated(true);
    }

    /**
     * 父类中的抽象方法，需要实现。表示密码，短信验证码不需要，所以直接返回null
     *
     * @return
     */
    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        if (isAuthenticated) {
            throw new IllegalArgumentException("Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        } else {
            super.setAuthenticated(false);
        }
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
    }

}
