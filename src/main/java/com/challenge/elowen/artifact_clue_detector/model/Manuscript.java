package com.challenge.elowen.artifact_clue_detector.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Manuscript {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime createdAt = LocalDateTime.now();
    private boolean hasClue;

    private String clueCharacter;
    private String clueDirection;

    @Column(name = "from_position")
    private String fromPosition;

    @Column(name = "to_position")
    private String toPosition;

    @Column(unique = true, nullable = false)
    private String manuscriptHash;
}