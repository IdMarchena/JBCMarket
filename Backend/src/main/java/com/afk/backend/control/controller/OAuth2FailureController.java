package com.afk.backend.control.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OAuth2FailureController {

    @GetMapping("/loginFailure")
    public String loginFailure(HttpServletRequest request) {
        System.out.println("Login con Google fallido");
        return "redirect:/error"; // o cualquier página de error
    }
}
