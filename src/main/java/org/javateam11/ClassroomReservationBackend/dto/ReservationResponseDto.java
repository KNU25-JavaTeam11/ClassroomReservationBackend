package org.javateam11.ClassroomReservationBackend.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ReservationResponseDto {
    private Long id;
    private Long roomId;
    private String studentId;
    private String date;
    private String startTime;
    private String endTime;
} 