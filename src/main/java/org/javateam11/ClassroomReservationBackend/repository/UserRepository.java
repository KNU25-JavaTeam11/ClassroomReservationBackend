package org.javateam11.ClassroomReservationBackend.repository;

import org.javateam11.ClassroomReservationBackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByStudentId(String studentId);
} 