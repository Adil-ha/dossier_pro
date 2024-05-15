package com.adil.blog.controller;

import com.adil.blog.dto.AuthenticationDTO;
import com.adil.blog.dto.UserDTO;
import com.adil.blog.entity.User;
import com.adil.blog.security.JwtService;
import com.adil.blog.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@AllArgsConstructor
@RestController
public class UserController {

    private AuthenticationManager authenticationManager;
    private UserService userService;
    private JwtService jwtService;
    @PostMapping(path= "/register")
    public void register(@RequestBody User user){
        log.info("Inscription");
        userService.register(user);
    }

    @PostMapping(path = "/login")
    public Map<String, String> login(@RequestBody AuthenticationDTO authenticationDTO){
        final Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationDTO.username(), authenticationDTO.password())
        );
        if(authenticate.isAuthenticated()){
            return this.jwtService.generate(authenticationDTO.username());
        }

        return null;
    }


    @PostMapping(path = "/logoutt")
    public void logout(){
        log.info("deconnexion");
        this.jwtService.logout();

    }
}
