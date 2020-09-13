package com.dream.start.login;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Create By 2020/9/13
 *
 * @author lvqingyu
 */
@Controller
public class CustomLoginController {

    @RequestMapping("/login/page")
    public String login(){
        return "login";
    }
}
