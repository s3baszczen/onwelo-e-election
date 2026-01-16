package com.onwelo.election.election;

import com.onwelo.election.election.interfaces.CandidateRequest;
import com.onwelo.election.election.interfaces.UpdateElectionCandidatesRequest;

import java.util.Set;

public class UpdateElectionCandidatesRequestBuilder {

    public static UpdateElectionCandidatesRequest updateCandidatesRequest(CandidateRequest... candidates) {
        return new UpdateElectionCandidatesRequest(Set.of(candidates));
    }
}