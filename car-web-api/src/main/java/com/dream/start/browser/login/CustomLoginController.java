package com.dream.start.browser.login;

import cn.hutool.core.util.RandomUtil;
import com.dream.start.browser.core.code.bo.ImageCodeBO;
import com.dream.start.browser.core.code.service.ValidateCodeService;
import com.dream.start.browser.core.utils.MD5Util;
import com.dream.start.browser.core.utils.ResultUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.UUID;

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

    private static final String BASE_CHECK_CODES = "qwertyuiplkjhgfdsazxcvbnmQWERTYUPLKJHGFDSAZXCVBNM1234567890";

    @RequestMapping(value = "/login/page", method = RequestMethod.GET)
    public String login(){
        return "login";
    }

    @ResponseBody
    @RequestMapping(value = "/code/image", method = RequestMethod.GET)
    public String codeImage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ImageCodeBO imageCode = validateCodeService.createImageCode();

        String code = imageCode.getCode();
        BufferedImage image = imageCode.getImage();
        HttpSession session = request.getSession();

        ServletOutputStream outputStream = response.getOutputStream();
        ImageIO.write(image, "jpg", outputStream);

        String sessionKeyPrefix = RandomUtil.randomString(BASE_CHECK_CODES,4);
        String lowerCaseCode = sessionKeyPrefix.toLowerCase() + UUID.randomUUID();
        String realKey = MD5Util.MD5Encode(lowerCaseCode , "UTF-8");
        log.info("[获取验证码]-realkey:{}", realKey);
        session.setAttribute(realKey, code);
        ResultUtil<String> success = ResultUtil.success(realKey);
        return objectMapper.writeValueAsString(success);
    }

}
