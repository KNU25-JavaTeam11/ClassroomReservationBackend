package org.javateam11.ClassroomReservationBackend.service;

import lombok.RequiredArgsConstructor;
import org.javateam11.ClassroomReservationBackend.model.Reservation;
import org.javateam11.ClassroomReservationBackend.model.Room;
import org.javateam11.ClassroomReservationBackend.model.User;
import org.javateam11.ClassroomReservationBackend.repository.ReservationRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {
    // 예약 정보를 데이터베이스에서 조회, 저장, 삭제 등 CRUD 작업을 할 수 있게 해주는 JPA Repository 객체입니다.
    // 그냥 데이터베이스 함수의 집합이라고 생각하고 메서드에서 가져와서 사용하면 됩니다.
    // Spring이 자동으로 구현체를 주입해줍니다. (ReservationRepository 인터페이스 참고)
    private final ReservationRepository reservationRepository;

    public Reservation create(Room room, User user, LocalDate date, LocalTime startTime, LocalTime endTime) {
        // 1. 해당 방과 날짜의 예약 목록 조회
        List<Reservation> reservations = reservationRepository.findByRoomIdAndDate(room.getId(), date);

        // 2. 예약 시간대가 겹치는지 확인
        boolean isOverlapped = reservations.stream().anyMatch(r ->
                r.getStartTime().isBefore(endTime) && r.getEndTime().isAfter(startTime)
        );
        if (isOverlapped) {
            throw new IllegalArgumentException("이미 예약된 시간입니다.");
        }

        // 3. 예약 생성 및 저장
        Reservation reservation = Reservation.builder()
                .room(room)
                .user(user)
                .date(date)
                .startTime(startTime)
                .endTime(endTime)
                .build();
        return reservationRepository.save(reservation);
    }

    public List<Reservation> findByRoomAndDate(Long roomId, LocalDate date) {
        return reservationRepository.findByRoomIdAndDate(roomId, date);
    }

    public void cancel(Long reservationId, User user) {
        // 1. 예약 조회 (없으면 예외)
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("예약을 찾을 수 없습니다."));

        // 2. 본인 예약인지 확인
        if (!reservation.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("본인 예약만 취소할 수 있습니다.");
        }

        // 3. 예약 삭제
        reservationRepository.delete(reservation);
    }
} 