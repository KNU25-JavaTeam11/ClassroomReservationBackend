package org.javateam11.ClassroomReservationBackend.model;

import lombok.*;
import jakarta.persistence.*;

@Entity
@Table(name = "users")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String studentId;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String password;
} 