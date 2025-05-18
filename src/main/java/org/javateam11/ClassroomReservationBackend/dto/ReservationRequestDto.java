package org.javateam11.ClassroomReservationBackend.dto;

import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Getter
@Setter
public class ReservationRequestDto {
    @NotNull
    private Long roomId;

    @NotNull
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}")
    private String date;      // yyyy-MM-dd

    @NotNull
    @Pattern(regexp = "\\d{2}:\\d{2}")
    private String startTime; // HH:mm

    @NotNull
    @Pattern(regexp = "\\d{2}:\\d{2}")
    private String endTime;   // HH:mm
} 