package com.onwelo.election.voting.interfaces;


import jakarta.validation.constraints.NotNull;

import java.util.UUID;

record RegisterVoterRequest(@NotNull UUID externalUserId, @NotNull VoterStatus voterStatus) {

}
