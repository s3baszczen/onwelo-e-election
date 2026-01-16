package com.onwelo.election.election;

import com.onwelo.election.election.interfaces.CandidateRequest;

public class CandidateRequestBuilder {

    public static CandidateRequest candidate(String name) {
        return new CandidateRequest(name);
    }
}