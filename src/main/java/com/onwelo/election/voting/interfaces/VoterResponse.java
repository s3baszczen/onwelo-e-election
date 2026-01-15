package com.onwelo.election.voting.interfaces;

import com.onwelo.election.voting.domain.model.VoterStatus;

import java.util.UUID;

record VoterResponse(UUID id, VoterStatus status) {
}
