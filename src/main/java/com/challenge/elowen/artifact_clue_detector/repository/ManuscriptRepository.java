package com.challenge.elowen.artifact_clue_detector.repository;

import com.challenge.elowen.artifact_clue_detector.model.Manuscript;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ManuscriptRepository extends JpaRepository<Manuscript, Long> {
    long countByHasClueTrue();
    long countByHasClueFalse();
    Optional<Manuscript> findByManuscriptHash(String manuscriptHash);
}