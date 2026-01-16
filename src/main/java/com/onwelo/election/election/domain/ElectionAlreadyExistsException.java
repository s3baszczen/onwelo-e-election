package com.onwelo.election.election.domain;

public class ElectionAlreadyExistsException extends RuntimeException {

    public ElectionAlreadyExistsException(String electionName) {
        super(String.format("Election with name '%s' already exists", electionName));
    }
}
