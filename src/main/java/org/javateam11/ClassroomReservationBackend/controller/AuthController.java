package org.javateam11.ClassroomReservationBackend.controller;

import lombok.RequiredArgsConstructor;
import org.javateam11.ClassroomReservationBackend.model.User;
import org.javateam11.ClassroomReservationBackend.service.UserService;
import org.javateam11.ClassroomReservationBackend.dto.UserRequestDto;
import org.javateam11.ClassroomReservationBackend.dto.UserResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.javateam11.ClassroomReservationBackend.security.JwtUtil;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid UserRequestDto req) {
        try {
            User user = userService.register(req.getUsername(), req.getPassword());
            String token = jwtUtil.generateToken(user.getUsername());
            return ResponseEntity.ok(UserResponseDto.builder()
                .username(user.getUsername())
                .token(token)
                .build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid UserRequestDto req) {
        try {
            User user = userService.authenticate(req.getUsername(), req.getPassword());
            String token = jwtUtil.generateToken(user.getUsername());
            return ResponseEntity.ok(UserResponseDto.builder()
                .username(user.getUsername())
                .token(token)
                .build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
} 