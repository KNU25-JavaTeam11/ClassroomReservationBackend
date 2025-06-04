package org.javateam11.ClassroomReservationBackend.controller;

import lombok.RequiredArgsConstructor;
import org.javateam11.ClassroomReservationBackend.model.Room;
import org.javateam11.ClassroomReservationBackend.service.RoomService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
@Tag(name = "강의실", description = "강의실 관리 API")
public class RoomController {
    private final RoomService roomService;

    @GetMapping
    @Operation(summary = "모든 강의실 조회", description = "시스템에 등록된 모든 강의실을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "강의실 목록 조회 성공")
    @SecurityRequirement(name = "bearerAuth")
    public List<Room> getAllRooms() {
        return roomService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "특정 강의실 조회", description = "ID로 특정 강의실의 정보를 조회합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "강의실 조회 성공", 
                    content = @Content(schema = @Schema(implementation = Room.class))),
        @ApiResponse(responseCode = "400", description = "존재하지 않는 강의실 ID")
    })
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> getRoom(@Parameter(description = "강의실 ID") @PathVariable Long id) {
        try {
            return ResponseEntity.ok(roomService.findById(id));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
} 