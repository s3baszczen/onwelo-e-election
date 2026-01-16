package com.onwelo.election.election.interfaces;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record UpdateElectionCandidatesRequest(
    @NotNull @Size(min = 2) @Valid Set<CandidateRequest> candidates
) {
}
