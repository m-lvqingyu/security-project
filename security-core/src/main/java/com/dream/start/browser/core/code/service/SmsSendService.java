package com.dream.start.browser.core.code.service;

/**
 * Create By 2020/9/19
 *
 * @author lvqingyu
 */
public interface SmsSendService {

    /**
     * 短信验证码发送接口
     *
     * @param mobile 手机号
     * @param smsCode 验证码
     * @return
     */
    boolean smsSend(String mobile, String smsCode);
}
