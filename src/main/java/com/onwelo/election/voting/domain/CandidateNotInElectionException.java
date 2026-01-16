package com.onwelo.election.voting.domain;

import java.util.UUID;

public class CandidateNotInElectionException extends RuntimeException {

    public CandidateNotInElectionException(UUID candidateId, UUID electionId) {
        super("Candidate " + candidateId + " is not part of election " + electionId);
    }
}
