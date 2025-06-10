package org.javateam11.ClassroomReservationBackend.repository;

import org.javateam11.ClassroomReservationBackend.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByRoomIdAndDate(Long roomId, LocalDate date);
    List<Reservation> findByRoomId(Long roomId);
    List<Reservation> findByDate(LocalDate date);
}