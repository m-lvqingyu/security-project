package com.dream.start.browser.main;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Create By 2020/9/13
 *
 * @author lvqingyu
 */
@Controller
public class IndexController {

    @RequestMapping({"/index","/",""})
    public String index(){
        return "index";
    }
}
