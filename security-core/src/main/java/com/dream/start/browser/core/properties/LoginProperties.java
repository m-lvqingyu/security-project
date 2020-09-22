package com.dream.start.browser.core.properties;

import com.dream.start.browser.core.constant.BrowserLoginConstant;
import com.dream.start.browser.core.enums.LoginResponseType;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @description: <pre>
 * </pre>
 * @author: Lvqingyu
 * @create: 2020/09/17 18:08
 */
@Data
@ConfigurationProperties(prefix = "my.security.prop.login")
public class LoginProperties {

    private String loginPage = BrowserLoginConstant.DEFAULT_LOGIN_PAGE_PATH;

    private String loginProcessingUrl = BrowserLoginConstant.DEFAULT_LOGIN_PROCESSING_URL;

    private String loginUserName = BrowserLoginConstant.DEFAULT_LOGIN_USERNAME;

    private String loginPassword = BrowserLoginConstant.DEFAULT_LOGIN_PASSWORD;

    private String[] ignoringMatchersPath = BrowserLoginConstant.DEFAULT_IGNORING_MATCHERS_PATH;

    private LoginResponseType loginType = LoginResponseType.REDIRECT;

    private String codeImage = BrowserLoginConstant.DEFAULT_CODE_IMAGE;

    private String mobileLoginUrl = BrowserLoginConstant.DEFAULT_MOBILE_LOGIN_URL;

    private String codeSms = BrowserLoginConstant.DEFAULT_CODE_SMS;

    private String mobileForm = BrowserLoginConstant.DEFAULT_MOBILE_FORM;

}
