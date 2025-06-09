package org.javateam11.ClassroomReservationBackend.dto;

import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@Schema(description = "로그인 요청 DTO")
public class LoginRequestDto {
    @NotNull(message = "학번은 필수입니다")
    @Schema(description = "학번", example = "2024000000")
    private String studentId;

    @NotNull(message = "비밀번호는 필수입니다")
    @Schema(description = "비밀번호", example = "password123")
    private String password;
} 