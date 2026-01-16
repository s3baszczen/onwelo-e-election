package com.onwelo.election.voting.domain;

import com.onwelo.election.voting.domain.model.Vote;

import java.util.UUID;

public interface VoteRepository {

    Vote save(Vote vote);

    boolean existsByVoterIdAndElectionId(UUID voterId, UUID electionId);
}
