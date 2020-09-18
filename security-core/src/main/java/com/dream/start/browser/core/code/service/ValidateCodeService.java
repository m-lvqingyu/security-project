package com.dream.start.browser.core.code.service;

import com.dream.start.browser.core.code.bo.ImageCodeBO;

/**
 * @description: <pre>
 * </pre>
 * @author: Lvqingyu
 * @create: 2020/09/18 16:47
 */
public interface ValidateCodeService {

    /**
     * 获取验证码
     *
     * @return
     */
    ImageCodeBO createImageCode();
}
