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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
@Tag(name = "예약", description = "강의실 예약 관리 API")
@SecurityRequirement(name = "bearerAuth")
public class ReservationController {
    private final ReservationService reservationService;
    private final RoomService roomService;
    private final UserService userService;

    @PostMapping
    @Operation(summary = "예약 생성", description = "새로운 강의실 예약을 생성합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "예약 생성 성공", 
                    content = @Content(schema = @Schema(implementation = ReservationResponseDto.class))),
        @ApiResponse(responseCode = "400", description = "잘못된 요청 (시간 겹침, 존재하지 않는 강의실 등)")
    })
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
    @Operation(summary = "예약 조회", description = "특정 강의실과 날짜의 예약 목록을 조회합니다.", security = {})
    @ApiResponse(responseCode = "200", description = "예약 목록 조회 성공")
    public List<ReservationResponseDto> getReservations(
            @Parameter(description = "강의실 ID") @RequestParam Long roomId, 
            @Parameter(description = "날짜 (YYYY-MM-DD 형식)") @RequestParam String date) {
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
    @Operation(summary = "예약 취소", description = "자신의 예약을 취소합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "예약 취소 성공"),
        @ApiResponse(responseCode = "400", description = "권한 없음 또는 존재하지 않는 예약")
    })
    public ResponseEntity<?> cancelReservation(
            @Parameter(description = "예약 ID") @PathVariable Long id, 
            Principal principal) {
        try {
            User user = userService.findByUsername(principal.getName());
            reservationService.cancel(id, user);
            return ResponseEntity.ok("예약 취소 완료");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
} 