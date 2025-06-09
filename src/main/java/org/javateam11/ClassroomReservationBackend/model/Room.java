package org.javateam11.ClassroomReservationBackend.model;

import lombok.*;
import jakarta.persistence.*;

@Entity
@Table(name = "rooms")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Room {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String building;
    private int floor;
    private String name;
} 