package com.javamentors.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.javamentors.service.UserService;

import java.security.Principal;

@Controller
public class HelloController {

    @Autowired
    private UserService userService;

    @GetMapping("/hello")
    public String hello(Principal principal, Model model, Authentication authentication){
        model.addAttribute("user", principal);
        return "hello";
    }
}
