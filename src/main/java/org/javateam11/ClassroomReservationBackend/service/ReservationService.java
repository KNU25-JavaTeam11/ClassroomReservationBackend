package org.javateam11.ClassroomReservationBackend.service;

import lombok.RequiredArgsConstructor;
import org.javateam11.ClassroomReservationBackend.model.Reservation;
import org.javateam11.ClassroomReservationBackend.model.Room;
import org.javateam11.ClassroomReservationBackend.model.User;
import org.javateam11.ClassroomReservationBackend.repository.ReservationRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;

    public Reservation create(Room room, User user, LocalDate date, String timeSlot) {
        // 예약 중복 체크
        boolean exists = reservationRepository.findByRoomIdAndDate(room.getId(), date).stream()
                .anyMatch(r -> r.getTimeSlot().equals(timeSlot));
        if (exists) {
            throw new IllegalArgumentException("이미 예약된 시간입니다.");
        }
        Reservation reservation = Reservation.builder()
                .room(room)
                .user(user)
                .date(date)
                .timeSlot(timeSlot)
                .build();
        return reservationRepository.save(reservation);
    }

    public List<Reservation> findByRoomAndDate(Long roomId, LocalDate date) {
        return reservationRepository.findByRoomIdAndDate(roomId, date);
    }

    public void cancel(Long reservationId, User user) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("예약을 찾을 수 없습니다."));
        if (!reservation.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("본인 예약만 취소할 수 있습니다.");
        }
        reservationRepository.delete(reservation);
    }
} 