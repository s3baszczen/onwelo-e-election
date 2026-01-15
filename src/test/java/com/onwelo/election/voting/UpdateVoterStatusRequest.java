package com.onwelo.election.voting;

import com.onwelo.election.voting.domain.model.VoterStatus;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UpdateVoterStatusRequest(@NotNull UUID voterId, @NotNull VoterStatus status) {
}
