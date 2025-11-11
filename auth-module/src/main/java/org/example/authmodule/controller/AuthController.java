package org.example.authmodule.controller;

import lombok.RequiredArgsConstructor;
import org.example.authmodule.dto.UserRequestDto;
import org.example.authmodule.dto.UserVerificationDto;
import org.example.authmodule.service.AuthService;
import org.example.authmodule.service.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserRequestDto requestDto) {


        authService.generateAndSendCode(requestDto);
        return ResponseEntity.ok("message " + "Verification code sent");
    }

    @PostMapping("/confirm")
    public ResponseEntity<?> confirm(@RequestBody UserVerificationDto userVerificationDto) {

        String email = userVerificationDto.getEmail();


        if (authService.verifyCode(userVerificationDto)) {
            return ResponseEntity.ok(Map.of("token", jwtService.generateToken(email)));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid or expired code");
    }
}
