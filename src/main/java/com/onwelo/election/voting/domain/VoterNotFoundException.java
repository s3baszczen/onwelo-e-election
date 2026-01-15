package com.onwelo.election.voting.domain;

import java.util.UUID;

public class VoterNotFoundException extends RuntimeException {

    public VoterNotFoundException(UUID voterId) {
        super("Voter not found: " + voterId);
    }
}
