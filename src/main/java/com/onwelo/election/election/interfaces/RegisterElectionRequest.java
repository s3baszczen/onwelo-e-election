package com.onwelo.election.election.interfaces;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.Set;

public enum RegisterElectionRequest(@NotEmpty String name, Set<CandiateRequest> candiates) {
}
