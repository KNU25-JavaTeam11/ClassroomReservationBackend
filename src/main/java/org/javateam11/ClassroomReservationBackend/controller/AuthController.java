package org.javateam11.ClassroomReservationBackend.controller;

import lombok.RequiredArgsConstructor;
import org.javateam11.ClassroomReservationBackend.model.User;
import org.javateam11.ClassroomReservationBackend.service.UserService;
import org.javateam11.ClassroomReservationBackend.dto.RegisterRequestDto;
import org.javateam11.ClassroomReservationBackend.dto.RegisterResponseDto;
import org.javateam11.ClassroomReservationBackend.dto.LoginRequestDto;
import org.javateam11.ClassroomReservationBackend.dto.LoginResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.javateam11.ClassroomReservationBackend.security.JwtUtil;
import java.util.Map;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "인증", description = "사용자 인증 관련 API")
public class AuthController {
    private final UserService userService;
    private final JwtUtil jwtUtil;

    @PostMapping("/register")
    @Operation(summary = "회원 가입", description = "새로운 사용자를 등록합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "회원 가입 성공", 
                    content = @Content(schema = @Schema(implementation = RegisterResponseDto.class))),
        @ApiResponse(responseCode = "400", description = "잘못된 요청 (중복된 사용자명 등)")
    })
    public ResponseEntity<?> register(@RequestBody @Valid RegisterRequestDto req) {
        try {
            User user = userService.register(req.getStudentId(), req.getName(), req.getPassword());
            return ResponseEntity.ok(RegisterResponseDto.builder()
                .studentId(user.getStudentId())
                .name(user.getName())
                .message("회원가입이 완료되었습니다.")
                .build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/login")
    @Operation(summary = "로그인", description = "사용자 인증을 수행하고 JWT 토큰을 발급합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "로그인 성공", 
                    content = @Content(schema = @Schema(implementation = LoginResponseDto.class))),
        @ApiResponse(responseCode = "400", description = "인증 실패 (잘못된 사용자명 또는 비밀번호)")
    })
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequestDto req) {
        try {
            User user = userService.authenticate(req.getStudentId(), req.getPassword());
            String token = jwtUtil.generateToken(user.getStudentId());
            return ResponseEntity.ok(LoginResponseDto.builder()
                .studentId(user.getStudentId())
                .name(user.getName())
                .token(token)
                .build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
} 