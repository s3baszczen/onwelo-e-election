package com.onwelo.election.voting.interfaces;

import java.time.Instant;
import java.util.UUID;

public record VoteResponse(
        UUID id,
        UUID voterId,
        UUID electionId,
        UUID candidateId,
        Instant votedAt
) {
}
