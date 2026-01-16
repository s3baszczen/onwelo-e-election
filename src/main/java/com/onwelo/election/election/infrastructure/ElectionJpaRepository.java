package com.onwelo.election.election.infrastructure;

import com.onwelo.election.election.domain.ElectionRepository;
import com.onwelo.election.election.domain.model.Election;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ElectionJpaRepository extends JpaRepository<Election, UUID>, ElectionRepository {
}
