package com.dream.start.browser.mobile;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @description: <pre>
 * </pre>
 * @author: Lvqingyu
 * @create: 2020/09/20 21:29
 */

public class MobileAuthenticationProvider implements AuthenticationProvider {

    private UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        MobileAuthenticationToken mobileAuthenticationToken = (MobileAuthenticationToken)authentication;
        String mobile = (String) mobileAuthenticationToken.getPrincipal();
        UserDetails userDetails = userDetailsService.loadUserByUsername(mobile);
        if(userDetails == null){
            throw new AuthenticationServiceException("用户还未注册");
        }
        MobileAuthenticationToken token = new MobileAuthenticationToken(userDetails, userDetails.getAuthorities());
        token.setDetails(mobileAuthenticationToken.getDetails());
        return token;
    }

    /**
     * 通过此方法来判断采用哪一个AuthenticationProvider
     * @param aClass
     * @return
     */
    @Override
    public boolean supports(Class<?> aClass) {
        return MobileAuthenticationToken.class.isAssignableFrom(aClass);
    }

    public void setUserDetailsService(UserDetailsService userDetailsService){
        this.userDetailsService = userDetailsService;
    }

}
