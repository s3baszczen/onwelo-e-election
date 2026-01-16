package com.onwelo.election.election.interfaces;

import java.util.Set;
import java.util.UUID;

public record ElectionResponse(
    UUID id,
    String name,
    Set<CandidateResponse> candidates
) {
}
