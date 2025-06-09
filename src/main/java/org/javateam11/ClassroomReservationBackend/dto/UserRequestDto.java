package org.javateam11.ClassroomReservationBackend.dto;

import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@Schema(description = "사용자 요청 DTO")
public class UserRequestDto {
    @NotNull(message = "학번은 필수입니다")
    @Size(min = 10, max = 10, message = "학번은 10자리여야 합니다")
    @Schema(description = "학번", example = "2024000000", minLength = 10, maxLength = 10)
    private String studentId;

    @NotNull(message = "이름은 필수입니다")
    @Size(min = 2, max = 30, message = "이름은 2~30자 사이여야 합니다")
    @Schema(description = "이름", example = "홍길동", minLength = 2, maxLength = 30)
    private String name;

    @NotNull(message = "비밀번호는 필수입니다")
    @Size(min = 4, max = 30, message = "비밀번호는 4~30자 사이여야 합니다")
    @Schema(description = "비밀번호", example = "password123", minLength = 4, maxLength = 30)
    private String password;
} 