package com.onwelo.election.voting.domain;

import java.util.UUID;

public class VoterBlockedException extends RuntimeException {

    public VoterBlockedException(UUID voterId) {
        super("Voter is blocked and cannot vote: " + voterId);
    }
}
