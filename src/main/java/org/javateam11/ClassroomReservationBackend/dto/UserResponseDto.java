package org.javateam11.ClassroomReservationBackend.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserResponseDto {
    private String username;
    private String token;
} 