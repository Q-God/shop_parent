package com.gmall.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @version v1.0
 * @ClassName WebLoginController
 * @Description TODO
 * @Author Q
 */
@Controller
public class WebLoginController {
    @GetMapping("/login.html")
    public String login(HttpServletRequest request) {
        String originalUrl = request.getParameter("originalUrl");
        request.setAttribute("originalUrl", originalUrl);
        return "login";
    }
}
