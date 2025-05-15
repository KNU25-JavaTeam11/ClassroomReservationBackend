package org.javateam11.ClassroomReservationBackend.repository;

import org.javateam11.ClassroomReservationBackend.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
} 