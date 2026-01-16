package com.onwelo.election.voting;

import java.util.UUID;

public record VoteRequest(
        UUID voterId,
        UUID electionId,
        UUID candidateId
) {
}
