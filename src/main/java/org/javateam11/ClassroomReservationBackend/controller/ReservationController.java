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

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;
    private final RoomService roomService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<?> createReservation(@RequestBody Map<String, String> req, Principal principal) {
        try {
            Room room = roomService.findById(Long.parseLong(req.get("roomId")));
            User user = userService.findByUsername(principal.getName());
            LocalDate date = LocalDate.parse(req.get("date"));
            LocalTime startTime = LocalTime.parse(req.get("startTime"));
            LocalTime endTime = LocalTime.parse(req.get("endTime"));
            Reservation reservation = reservationService.create(room, user, date, startTime, endTime);
            return ResponseEntity.ok(reservation);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public List<Reservation> getReservations(@RequestParam Long roomId, @RequestParam String date) {
        return reservationService.findByRoomAndDate(roomId, LocalDate.parse(date));
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