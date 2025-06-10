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
import java.util.Map;

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
            @ApiResponse(responseCode = "200", description = "예약 생성 성공", content = @Content(schema = @Schema(implementation = ReservationResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 (시간 겹침, 존재하지 않는 강의실 등)")
    })
    public ResponseEntity<?> createReservation(@RequestBody @Valid ReservationRequestDto req, Principal principal) {
        try {
            Room room = roomService.findById(req.getRoomId());
            User user = userService.findByStudentId(principal.getName());
            LocalDate date = LocalDate.parse(req.getDate());
            LocalTime startTime = LocalTime.parse(req.getStartTime());
            LocalTime endTime = LocalTime.parse(req.getEndTime());
            Reservation reservation = reservationService.create(room, user, date, startTime, endTime);
            ReservationResponseDto response = ReservationResponseDto.builder()
                    .id(reservation.getId())
                    .roomId(reservation.getRoom().getId())
                    .studentId(reservation.getUser().getStudentId())
                    .date(reservation.getDate().toString())
                    .startTime(reservation.getStartTime().toString())
                    .endTime(reservation.getEndTime().toString())
                    .build();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping
    @Operation(summary = "예약 조회", description = "강의실과 날짜로 예약 목록을 조회합니다. 파라미터가 없으면 모든 예약을 조회합니다.", security = {})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "예약 목록 조회 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 (날짜 형식 오류 등)")
    })
    public ResponseEntity<?> getReservations(
            @Parameter(description = "강의실 ID (선택적)", required = false) @RequestParam(required = false) Long roomId,
            @Parameter(description = "날짜 (YYYY-MM-DD 형식, 선택적)", required = false) @RequestParam(required = false) String date) {
        try {
            // 날짜 형식 검증 (date가 제공된 경우에만)
            LocalDate parsedDate = null;
            if (date != null && !date.trim().isEmpty()) {
                try {
                    parsedDate = LocalDate.parse(date);
                } catch (Exception e) {
                    return ResponseEntity.badRequest().body(Map.of("error", "날짜 형식이 올바르지 않습니다. YYYY-MM-DD 형식으로 입력해주세요."));
                }
            }

            List<ReservationResponseDto> reservations = reservationService.findByRoomAndDate(roomId, parsedDate)
                    .stream()
                    .map(reservation -> ReservationResponseDto.builder()
                            .id(reservation.getId())
                            .roomId(reservation.getRoom().getId())
                            .studentId(reservation.getUser().getStudentId())
                            .date(reservation.getDate().toString())
                            .startTime(reservation.getStartTime().toString())
                            .endTime(reservation.getEndTime().toString())
                            .build())
                    .toList();

            return ResponseEntity.ok(reservations);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "예약 조회 중 오류가 발생했습니다: " + e.getMessage()));
        }
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
            User user = userService.findByStudentId(principal.getName());
            reservationService.cancel(id, user);
            return ResponseEntity.ok(Map.of("message", "예약 취소 완료"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}