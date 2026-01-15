package com.onwelo.election.voting;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
@Getter
public class RegisterVoterRequest {

    @NotNull
    private final UUID externalUserId;
    @NotNull
    private final Status voterStatus;

}
