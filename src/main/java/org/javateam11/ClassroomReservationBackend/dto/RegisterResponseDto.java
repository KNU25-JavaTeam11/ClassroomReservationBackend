package org.javateam11.ClassroomReservationBackend.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@Builder
@Schema(description = "회원가입 응답 DTO")
public class RegisterResponseDto {
    @Schema(description = "학번", example = "2024000000")
    private String studentId;
    
    @Schema(description = "이름", example = "홍길동")
    private String name;
    
    @Schema(description = "회원가입 성공 메시지", example = "회원가입이 완료되었습니다.")
    private String message;
} 