package com.onwelo.election.voter;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class RegisterVoterRequest {

    private final String firstName;
    private final String secondName;
}
