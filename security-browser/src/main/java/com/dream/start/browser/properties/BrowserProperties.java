package com.dream.start.browser.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @description: <pre>
 * </pre>
 * @author: Lvqingyu
 * @create: 2020/09/17 18:08
 */
@Data
@Configuration
@EnableConfigurationProperties(BrowserProperties.class)
@ConfigurationProperties(prefix = "custom.security.browser.prop")
public class BrowserProperties {

    private String defaultLoginPage = "/login/page";

    private String defaultLoginProcessingUrl = "/login/form";

    private String defaultLoginUserName = "name";

    private String defaultLoginPassword = "pwd";

    private String[] ignoringMatchersPath = {"/dist/**", "/modules/**", "/plugins/**"};
}
