package com.onwelo.election.election.domain;

import com.onwelo.election.election.domain.model.Election;

import java.util.Optional;
import java.util.UUID;

public interface ElectionRepository {

    Election save(Election election);

    Optional<Election> findById(UUID id);

    Optional<Election> findByIdWithCandidates(UUID id);
}
