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
    @NotNull
    @Size(min = 3, max = 20)
    @Schema(description = "사용자명", example = "testuser", minLength = 3, maxLength = 20)
    private String username;

    @NotNull
    @Size(min = 4, max = 30)
    @Schema(description = "비밀번호", example = "password123", minLength = 4, maxLength = 30)
    private String password;
} 