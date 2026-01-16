package com.onwelo.election.election.domain;

import com.onwelo.election.common.error.BusinessException;

public class ElectionAlreadyExistsException extends BusinessException {

    public ElectionAlreadyExistsException(String electionName) {
        super(String.format("Election with name '%s' already exists", electionName));
    }
}
