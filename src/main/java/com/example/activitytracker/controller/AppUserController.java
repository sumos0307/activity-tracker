package com.example.activitytracker.controller;

import com.example.activitytracker.dto.LoginRequest;
import com.example.activitytracker.dto.LoginResponse;
import com.example.activitytracker.dto.RegisterRequest;
import com.example.activitytracker.entity.AppUser;
import com.example.activitytracker.service.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class AppUserController {
    private final AppUserService appUserService;
    @PostMapping("/register")
    public AppUser register(@Valid @RequestBody RegisterRequest request){
        System.out.println("REGISTER ENDPOINT ÇALIŞTI");
        return appUserService.register(request);
    }
    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request){

        return appUserService.login(request);
    }
}
