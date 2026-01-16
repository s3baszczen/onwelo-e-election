package com.onwelo.election.election.infrastructure;

import com.onwelo.election.election.domain.ElectionRepository;
import com.onwelo.election.election.domain.model.Election;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ElectionJpaRepository extends JpaRepository<Election, UUID>, ElectionRepository {

    @Query("SELECT e FROM Election e LEFT JOIN FETCH e.candidates WHERE e.id = :id")
    Optional<Election> findByIdWithCandidates(@Param("id") UUID id);
}
