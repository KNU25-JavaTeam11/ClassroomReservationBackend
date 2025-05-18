package org.javateam11.ClassroomReservationBackend.controller;

import lombok.RequiredArgsConstructor;
import org.javateam11.ClassroomReservationBackend.model.Reservation;
import org.javateam11.ClassroomReservationBackend.model.Room;
import org.javateam11.ClassroomReservationBackend.model.User;
import org.javateam11.ClassroomReservationBackend.service.ReservationService;
import org.javateam11.ClassroomReservationBackend.service.RoomService;
import org.javateam11.ClassroomReservationBackend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.javateam11.ClassroomReservationBackend.dto.ReservationRequestDto;
import org.javateam11.ClassroomReservationBackend.dto.ReservationResponseDto;
import jakarta.validation.Valid;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;
    private final RoomService roomService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<?> createReservation(@RequestBody @Valid ReservationRequestDto req, Principal principal) {
        try {
            Room room = roomService.findById(req.getRoomId());
            User user = userService.findByUsername(principal.getName());
            LocalDate date = LocalDate.parse(req.getDate());
            LocalTime startTime = LocalTime.parse(req.getStartTime());
            LocalTime endTime = LocalTime.parse(req.getEndTime());
            Reservation reservation = reservationService.create(room, user, date, startTime, endTime);
            ReservationResponseDto response = ReservationResponseDto.builder()
                .id(reservation.getId())
                .roomId(reservation.getRoom().getId())
                .userId(reservation.getUser().getId())
                .date(reservation.getDate().toString())
                .startTime(reservation.getStartTime().toString())
                .endTime(reservation.getEndTime().toString())
                .build();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public List<ReservationResponseDto> getReservations(@RequestParam Long roomId, @RequestParam String date) {
        return reservationService.findByRoomAndDate(roomId, LocalDate.parse(date)).stream()
            .map(reservation -> ReservationResponseDto.builder()
                .id(reservation.getId())
                .roomId(reservation.getRoom().getId())
                .userId(reservation.getUser().getId())
                .date(reservation.getDate().toString())
                .startTime(reservation.getStartTime().toString())
                .endTime(reservation.getEndTime().toString())
                .build())
            .toList();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> cancelReservation(@PathVariable Long id, Principal principal) {
        try {
            User user = userService.findByUsername(principal.getName());
            reservationService.cancel(id, user);
            return ResponseEntity.ok("예약 취소 완료");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
} 