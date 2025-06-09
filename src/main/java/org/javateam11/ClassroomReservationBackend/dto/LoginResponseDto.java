package org.javateam11.ClassroomReservationBackend.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@Builder
@Schema(description = "로그인 응답 DTO")
public class LoginResponseDto {
    @Schema(description = "학번", example = "2024000000")
    private String studentId;
    
    @Schema(description = "이름", example = "홍길동")
    private String name;
    
    @Schema(description = "JWT 토큰", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String token;
} 