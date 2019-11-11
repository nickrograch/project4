package com.javamentors.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AccesDeniedController {
    @GetMapping("/access_denied")
    public String accessDenied(){
        return "accessDenied";
    }
}
