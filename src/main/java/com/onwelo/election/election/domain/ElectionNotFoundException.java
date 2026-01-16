package com.onwelo.election.election.domain;

import java.util.UUID;

public class ElectionNotFoundException extends RuntimeException {

    public ElectionNotFoundException(UUID electionId) {
        super(String.format("Election with id %s not found", electionId));
    }
}
