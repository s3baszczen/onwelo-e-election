package com.onwelo.election.voter.interfaces;


import com.onwelo.election.voter.interfaces.constraints.Pesel;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
class RegisterVoterRequest {

    @NotEmpty
    private final String firstName;
    @NotEmpty
    private final String secondName;
    @Pesel
    private final String pesel;

}
