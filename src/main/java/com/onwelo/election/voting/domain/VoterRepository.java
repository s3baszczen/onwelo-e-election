package com.onwelo.election.voting.domain;

import com.onwelo.election.voting.domain.model.Voter;

import java.util.Optional;
import java.util.UUID;

public interface VoterRepository {

    Voter save(Voter voter);

    Optional<Voter> findById(UUID id);
}
