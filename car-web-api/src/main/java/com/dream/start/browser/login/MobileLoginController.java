package com.dream.start.browser.login;

import com.dream.start.browser.core.code.service.SmsSendService;
import com.dream.start.browser.core.constant.BrowserLoginConstant;
import com.dream.start.browser.core.utils.ResultUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Create By 2020/9/19
 *
 * @author lvqingyu
 */
@Controller
public class MobileLoginController {

    private final SmsSendService aliSmsSendService;

    @Autowired
    public MobileLoginController(SmsSendService aliSmsSendService){
        this.aliSmsSendService = aliSmsSendService;
    }

    @RequestMapping(value = "/mobile/page", method = RequestMethod.GET)
    public String mobileLogin(HttpServletRequest request, HttpServletResponse response){
        return "login-mobile";
    }

    @ResponseBody
    @RequestMapping(value = "/code/mobile", method = RequestMethod.GET)
    public ResultUtil codeMobile(HttpServletRequest request, HttpServletResponse response) throws JsonProcessingException {
        String mobile = request.getParameter("mobile");
        if(StringUtils.isBlank(mobile)){
            ResultUtil<Object> failure = ResultUtil.failure("手机号不能为空");
            return failure;
        }
        String code = RandomStringUtils.randomNumeric(4);
        request.getSession().setAttribute(BrowserLoginConstant.SESSION_SMS_CODE_KEY, code);
        aliSmsSendService.smsSend(mobile, code);
        ResultUtil<Object> success = ResultUtil.success(null);
        return success;
    }

}
