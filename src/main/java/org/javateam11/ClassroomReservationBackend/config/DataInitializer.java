package org.javateam11.ClassroomReservationBackend.config;

import lombok.RequiredArgsConstructor;
import org.javateam11.ClassroomReservationBackend.model.Room;
import org.javateam11.ClassroomReservationBackend.repository.RoomRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DataInitializer {
    
    private final RoomRepository roomRepository;

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void initializeData() {
        initializeRooms();
    }

    private void initializeRooms() {
        // 초기화할 교실 데이터 정의
        List<RoomData> roomDataList = Arrays.asList(
            new RoomData("IT4", 1, "104"),
            new RoomData("IT4", 1, "106"),
            new RoomData("IT4", 1, "108"),
            new RoomData("IT4", 1, "DIY"),
            
            new RoomData("IT5", 2, "224"),
            new RoomData("IT5", 2, "225"),
            new RoomData("IT5", 2, "245"),
            new RoomData("IT5", 2, "248"),
            new RoomData("IT5", 3, "342"),
            new RoomData("IT5", 3, "345"),
            new RoomData("IT5", 3, "348")
        );

        // 각 교실 데이터를 확인하고 업데이트/생성
        for (RoomData roomData : roomDataList) {
            Optional<Room> existingRoom = roomRepository.findByBuildingAndFloorAndName(
                roomData.building, roomData.floor, roomData.name);
            
            if (existingRoom.isPresent()) {
                // 기존 데이터가 있으면 필요시 업데이트 (현재는 변경사항이 없으므로 스킵)
                System.out.println("교실 데이터 확인: " + roomData.building + " " + roomData.floor + " " + roomData.name);
            } else {
                // 새로운 교실 생성
                Room newRoom = Room.builder()
                    .building(roomData.building)
                    .floor(roomData.floor)
                    .name(roomData.name)
                    .build();
                roomRepository.save(newRoom);
                System.out.println("새 교실 데이터 생성: " + roomData.building + " " + roomData.floor + " " + roomData.name);
            }
        }

        System.out.println("교실 데이터 초기화/업데이트가 완료되었습니다.");
    }

    // 교실 데이터를 담는 내부 클래스
    private static class RoomData {
        final String building;
        final int floor;
        final String name;

        RoomData(String building, int floor, String name) {
            this.building = building;
            this.floor = floor;
            this.name = name;
        }
    }
} 