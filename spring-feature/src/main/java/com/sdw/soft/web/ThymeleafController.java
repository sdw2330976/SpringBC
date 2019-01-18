package com.sdw.soft.web;

import com.sdw.soft.config.UserProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping
public class ThymeleafController {

    @Autowired
    private UserProperties userProperties;

    @GetMapping("/index")
    public String index(HttpServletRequest request) {

        request.setAttribute("user", userProperties);
        return "user";
    }
}
