package com.onwelo.election.election;

import com.onwelo.election.election.interfaces.CandidateRequest;
import com.onwelo.election.election.interfaces.RegisterElectionRequest;

import java.util.Set;

import static com.onwelo.election.election.CandidateRequestBuilder.candidate;

public class RegisterElectionRequestBuilder {

    public static RegisterElectionRequest electionRequest(String name, CandidateRequest... candidates) {
        return new RegisterElectionRequest(name, Set.of(candidates));
    }

}