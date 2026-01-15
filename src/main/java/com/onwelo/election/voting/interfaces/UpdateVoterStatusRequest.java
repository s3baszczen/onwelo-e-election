package com.onwelo.election.voting.interfaces;

import com.onwelo.election.voting.domain.model.VoterStatus;
import jakarta.validation.constraints.NotNull;

record UpdateVoterStatusRequest(@NotNull VoterStatus status) {
}
