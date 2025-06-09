package org.javateam11.ClassroomReservationBackend.dto;

import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@Schema(description = "예약 요청 DTO")
public class ReservationRequestDto {
    @NotNull(message = "강의실 ID는 필수입니다")
    @Schema(description = "강의실 ID", example = "1")
    private Long roomId;

    @NotNull(message = "예약 날짜는 필수입니다")
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "날짜는 yyyy-MM-dd 형식이어야 합니다")
    @Schema(description = "예약 날짜", example = "2024-12-25", pattern = "yyyy-MM-dd")
    private String date;      // yyyy-MM-dd

    @NotNull(message = "시작 시간은 필수입니다")
    @Pattern(regexp = "\\d{2}:\\d{2}", message = "시작 시간은 HH:mm 형식이어야 합니다")
    @Schema(description = "시작 시간", example = "09:00", pattern = "HH:mm")
    private String startTime; // HH:mm

    @NotNull(message = "종료 시간은 필수입니다")
    @Pattern(regexp = "\\d{2}:\\d{2}", message = "종료 시간은 HH:mm 형식이어야 합니다")
    @Schema(description = "종료 시간", example = "11:00", pattern = "HH:mm")
    private String endTime;   // HH:mm
} 