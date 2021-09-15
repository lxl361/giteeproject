package com.zd.giteeproject.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class IndexController {
    @GetMapping("/")
    public String index(){
        return "index";
    }

//    @GetMapping("/logout")
//    public String logout(HttpServletRequest request){
//        HttpSession session = request.getSession();
//        session.invalidate();
//        return "index";
//    }
}
