package com.dream.start.browser.core.code.service.impl;

import com.dream.start.browser.core.code.bo.ImageCodeBO;
import com.dream.start.browser.core.code.service.ValidateCodeService;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;

/**
 * @description: <pre>
 * </pre>
 * @author: Lvqingyu
 * @create: 2020/09/18 17:01
 */
@Service
public class ValidateCodeServiceImpl implements ValidateCodeService {

    @Autowired
    private DefaultKaptcha defaultKaptcha;

    @Override
    public ImageCodeBO createImageCode() {
        String code = defaultKaptcha.createText();
        BufferedImage image = defaultKaptcha.createImage(code);
        ImageCodeBO imageCodeBO = new ImageCodeBO(code, image, 60);
        return imageCodeBO;
    }

}
