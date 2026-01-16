package com.onwelo.election.voting.interfaces;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record VoteRequest(
        @NotNull(message = "Voter ID is required")
        UUID voterId,

        @NotNull(message = "Election ID is required")
        UUID electionId,

        @NotNull(message = "Candidate ID is required")
        UUID candidateId
) {
}
