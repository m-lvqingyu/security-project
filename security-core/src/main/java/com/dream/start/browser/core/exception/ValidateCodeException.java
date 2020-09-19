package com.dream.start.browser.core.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * Create By 2020/9/19
 *
 * @author lvqingyu
 */
public class ValidateCodeException extends AuthenticationException {

    public ValidateCodeException(String message, Throwable throwable){
        super(message, throwable);
    }

    public ValidateCodeException(String message){
        super(message);
    }
}
