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
    @NotNull
    @Schema(description = "강의실 ID", example = "1")
    private Long roomId;

    @NotNull
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}")
    @Schema(description = "예약 날짜", example = "2024-12-25", pattern = "yyyy-MM-dd")
    private String date;      // yyyy-MM-dd

    @NotNull
    @Pattern(regexp = "\\d{2}:\\d{2}")
    @Schema(description = "시작 시간", example = "09:00", pattern = "HH:mm")
    private String startTime; // HH:mm

    @NotNull
    @Pattern(regexp = "\\d{2}:\\d{2}")
    @Schema(description = "종료 시간", example = "11:00", pattern = "HH:mm")
    private String endTime;   // HH:mm
} 