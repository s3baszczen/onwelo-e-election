package com.onwelo.election.voting.domain;

import java.util.UUID;

public class AlreadyVotedException extends RuntimeException {

    public AlreadyVotedException(UUID voterId, UUID electionId) {
        super("Voter " + voterId + " has already voted in election " + electionId);
    }

    public AlreadyVotedException(UUID voterId, UUID electionId, Throwable cause) {
        super("Voter " + voterId + " has already voted in election " + electionId, cause);
    }
}
