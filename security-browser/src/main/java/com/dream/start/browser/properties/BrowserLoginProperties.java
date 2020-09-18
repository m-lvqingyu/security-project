package com.dream.start.browser.properties;

import com.dream.start.browser.core.enums.LoginResponseType;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @description: <pre>
 * </pre>
 * @author: Lvqingyu
 * @create: 2020/09/17 18:08
 */
@ConfigurationProperties(prefix = "custom.security.browser.prop.login")
public class BrowserLoginProperties {

    private static final String DEFAULT_LOGIN_PAGE = "/login/page";

    private static final String DEFAULT_LOGIN_PROCESSING_URL = "/login/form";

    private static final String DEFAULT_LOGIN_USERNAME = "username";

    private static final String DEFAULT_LOGIN_PASSWORD = "password";

    private static final String[] DEFAULT_IGNORING_MATCHERS_PATH = {"/dist/**", "/modules/**", "/plugins/**"};

    private static final String DEFAULT_CODE_IMAGE = "/code/image";

    private String loginPage = DEFAULT_LOGIN_PAGE;

    private String loginProcessingUrl = DEFAULT_LOGIN_PROCESSING_URL;

    private String loginUserName = DEFAULT_LOGIN_USERNAME;

    private String loginPassword = DEFAULT_LOGIN_PASSWORD;

    private String[] ignoringMatchersPath = DEFAULT_IGNORING_MATCHERS_PATH;

    private LoginResponseType loginType = LoginResponseType.REDIRECT;

    private String codeImage = DEFAULT_CODE_IMAGE;

    public String getLoginPage() {
        return loginPage;
    }

    public void setLoginPage(String loginPage) {
        this.loginPage = loginPage;
    }

    public String getLoginProcessingUrl() {
        return loginProcessingUrl;
    }

    public void setLoginProcessingUrl(String loginProcessingUrl) {
        this.loginProcessingUrl = loginProcessingUrl;
    }

    public String getLoginUserName() {
        return loginUserName;
    }

    public void setLoginUserName(String loginUserName) {
        this.loginUserName = loginUserName;
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }

    public String[] getIgnoringMatchersPath() {
        return ignoringMatchersPath;
    }

    public void setIgnoringMatchersPath(String[] ignoringMatchersPath) {
        this.ignoringMatchersPath = ignoringMatchersPath;
    }

    public LoginResponseType getLoginType() {
        return loginType;
    }

    public void setLoginType(LoginResponseType loginType) {
        this.loginType = loginType;
    }

    public String getCodeImage() {
        return codeImage;
    }

    public void setCodeImage(String codeImage) {
        this.codeImage = codeImage;
    }
}
