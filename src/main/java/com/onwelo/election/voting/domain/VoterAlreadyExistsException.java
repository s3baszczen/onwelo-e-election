package com.onwelo.election.voting.domain;

import com.onwelo.election.common.error.BusinessException;
import lombok.Getter;

import java.util.UUID;

@Getter
public class VoterAlreadyExistsException extends BusinessException {
    private final UUID externalUserId;

    public VoterAlreadyExistsException(UUID externalUserId) {
        super("Voter with externalUserId [" + externalUserId + "] already exists");
        this.externalUserId = externalUserId;
    }

}