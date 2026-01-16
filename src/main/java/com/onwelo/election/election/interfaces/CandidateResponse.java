package com.onwelo.election.election.interfaces;

import java.util.UUID;

public record CandidateResponse(
    UUID id,
    String name
) {
}
