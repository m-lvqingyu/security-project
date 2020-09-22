package com.dream.start.browser.login;

import com.dream.start.browser.core.code.bo.ImageCodeBO;
import com.dream.start.browser.core.code.service.ValidateCodeService;
import com.dream.start.browser.core.constant.BrowserLoginConstant;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Create By 2020/9/13
 *
 * @author lvqingyu
 */
@Slf4j
@Controller
public class CustomLoginController {

    @Autowired
    private ValidateCodeService validateCodeService;
    @Autowired
    private ObjectMapper objectMapper;


    @RequestMapping(value = "/login/page", method = RequestMethod.GET)
    public String login(HttpServletRequest request, HttpServletResponse response) {
        return "login";
    }

    @RequestMapping(value = "/code/image", method = RequestMethod.GET)
    public void codeImage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ImageCodeBO imageCode = validateCodeService.createImageCode();
        BufferedImage image = imageCode.getImage();
        request.getSession().setAttribute(BrowserLoginConstant.SESSION_IMAGE_CODE_KEY, imageCode);
        ServletOutputStream outputStream = response.getOutputStream();
        ImageIO.write(image, BrowserLoginConstant.IMAGE_JPG_FORMAT_NAME, outputStream);
    }

}
