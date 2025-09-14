package com.app.showboxd.auth.controller;

import com.app.showboxd.user.dto.UserDto;
import com.app.showboxd.user.entity.User;
import com.app.showboxd.auth.service.LoginService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/showboxd/v1/auth")
public class AuthenticationController {


    private final LoginService loginService;

    public AuthenticationController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody UserDto userDto, HttpServletResponse response) {
        return loginService.login(userDto, response);
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody UserDto userDto) {
        return loginService.register(userDto);
    }

    @GetMapping("/testJwt")
    public String test() {
        return "Successfull";
    }
}
