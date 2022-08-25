package com.mysite.askAnything;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {
    @RequestMapping("/HOME")
    @ResponseBody
    public String index(){
        return "Welcome!";
    }

    @RequestMapping("/")
    public String root(){
        return "redirect:/post/list";
    }

}
