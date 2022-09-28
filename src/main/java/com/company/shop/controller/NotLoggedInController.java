package com.company.shop.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@RestController
public class NotLoggedInController implements ErrorController {

    @RequestMapping(value = "/error")
    public ModelAndView error(HttpServletRequest request) {
        Integer statusCode = (Integer)request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == 404) {
            return new ModelAndView("err404.html");
        }
        return new ModelAndView("err.html");
    }
}
