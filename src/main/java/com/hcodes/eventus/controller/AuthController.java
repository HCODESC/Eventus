/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.hcodes.eventus.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hcodes.eventus.config.JWTGenerator;
import com.hcodes.eventus.dto.AuthResponseDto;
import com.hcodes.eventus.dto.LoginDto;
import com.hcodes.eventus.dto.RegisterDto;
import com.hcodes.eventus.entity.UserEntity;
import com.hcodes.eventus.repository.UserRepository;

/**
 *
 * @author mavis
 */

 @RestController
 @RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository; 
    private final JWTGenerator jwtGenerator; 
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder; 

    public AuthController(UserRepository userRepository, JWTGenerator jwtGenerator, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository; 
        this.jwtGenerator = jwtGenerator;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody LoginDto loginDto) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword())); 
        SecurityContextHolder 
            .getContext()
            .setAuthentication(authentication);

        String token = jwtGenerator.generateToken(authentication);

        return new ResponseEntity<>(new AuthResponseDto(token), HttpStatus.OK); 
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) {

        if (userRepository.existsByUsername(registerDto.getUsername())) {
            return new ResponseEntity<>("Username is already taken", HttpStatus.BAD_REQUEST);
        }

        if (userRepository.existsByEmail(registerDto.getEmail())) {
            return new ResponseEntity<>("Email is already taken", HttpStatus.BAD_REQUEST);
        }

        UserEntity user = new UserEntity(); 
        user.setUsername(registerDto.getUsername());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setEmail(registerDto.getEmail());
        user.setFirstName(registerDto.getFirstName());
        user.setLastName(registerDto.getLastName());

        userRepository.save(user);

        return new ResponseEntity<>("User Successfully registered", HttpStatus.OK); 
    }

}
