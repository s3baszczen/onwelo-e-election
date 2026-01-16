package com.onwelo.election.election.domain;

import java.util.UUID;

public class CandidateNotFoundException extends RuntimeException {

    public CandidateNotFoundException(UUID candidateId) {
        super("Candidate not found: " + candidateId);
    }
}
