package com.dream.start.browser.core.code.bo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

/**
 * @description: <pre>
 *     图形验证码
 * </pre>
 * @author: Lvqingyu
 * @create: 2020/09/18 16:48
 */

@Data
@NoArgsConstructor
public class ImageCodeBO {

    private String code;

    private BufferedImage image;

    private LocalDateTime expiredTime;

    public ImageCodeBO(String code, BufferedImage image, int expiredIn){
        this.code = code;
        this.image = image;
        this.expiredTime = LocalDateTime.now().plusSeconds(expiredIn);
    }

    /**
     * 判断验证码是否过期
     *
     * @return true：已过期  false：未过期
     */
    public boolean isExpired(){
        return expiredTime.isBefore(LocalDateTime.now());
    }
}
