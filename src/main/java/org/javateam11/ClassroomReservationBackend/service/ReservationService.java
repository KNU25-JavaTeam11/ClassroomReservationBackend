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
        // TODO: 이 함수는 새로운 예약을 생성하는 역할을 합니다.
        // 1. 예약이 이미 존재하는지 확인해야 합니다. (동일한 방, 날짜, 시간대에 이미 예약이 있는지)
        //    - reservationRepository.findByRoomIdAndDate(room.getId(), date)로 해당 방과 날짜의 예약 목록을 가져올 수 있습니다.
        //    - Java Stream의 anyMatch를 사용해 예약 시간대가 겹치는지 확인할 수 있습니다.
        //      (예: 기존 예약의 startTime < 새 endTime && 기존 예약의 endTime > 새 startTime)
        // 2. 이미 예약이 있다면 예외를 발생시켜야 합니다. (예: throw new IllegalArgumentException("이미 예약된 시간입니다."))
        // 3. 예약이 없다면 Reservation 객체를 생성해야 합니다.
        //    - Reservation.builder()를 사용해 객체를 만들고, room, user, date, startTime, endTime을 설정합니다.
        // 4. reservationRepository.save(reservation)으로 DB에 저장하고, 저장된 객체를 반환합니다.
        //
        // 위의 단계에 맞춰 구현해 주세요.
        return null; // 임시 반환값. 구현 후에는 저장된 Reservation 객체를 반환해야 합니다.
    }

    public List<Reservation> findByRoomAndDate(Long roomId, LocalDate date) {
        return reservationRepository.findByRoomIdAndDate(roomId, date);
    }

    public void cancel(Long reservationId, User user) {
        // TODO: 이 함수는 예약을 취소하는 역할을 합니다.
        // 1. reservationRepository.findById(reservationId)로 예약을 조회합니다.
        //    - Optional로 반환되므로, orElseThrow를 사용해 예약이 없으면 예외를 던질 수 있습니다.
        // 2. 예약의 user id와 현재 사용자의 id가 같은지 확인해야 합니다.
        //    - 같지 않으면 예외를 발생시켜야 합니다. (예: throw new IllegalArgumentException("본인 예약만 취소할 수 있습니다."))
        // 3. 조건이 맞으면 reservationRepository.delete(reservation)으로 예약을 삭제합니다.
        //
        // 위의 단계에 맞춰 구현해 주세요.
    }
} 