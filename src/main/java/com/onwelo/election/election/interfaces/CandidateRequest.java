package com.onwelo.election.election.interfaces;

import jakarta.validation.constraints.NotEmpty;

public record CandidateRequest(@NotEmpty String name) {
}
