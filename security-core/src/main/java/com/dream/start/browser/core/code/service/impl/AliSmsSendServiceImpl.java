package com.dream.start.browser.core.code.service.impl;

import com.dream.start.browser.core.code.service.SmsSendService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Create By 2020/9/19
 *
 * @author lvqingyu
 */
@Slf4j
@Component("aliSmsSendService")
public class AliSmsSendServiceImpl implements SmsSendService {

    @Override
    public boolean smsSend(String mobile, String smsCode) {
        String smsContent = String.format("SSO短信验证码%s，请勿泄露给其他人", smsCode);
        log.info("SSO短信发送内容:{}", smsContent);
        return true;
    }

}
