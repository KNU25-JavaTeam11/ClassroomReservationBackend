package org.javateam11.ClassroomReservationBackend.repository;

import org.javateam11.ClassroomReservationBackend.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {
    Optional<Room> findByBuildingAndFloorAndName(String building, int floor, String name);
} 