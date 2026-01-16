package com.onwelo.election.voting;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.reactive.server.WebTestClient.ResponseSpec;

@Component
public class VotingApi {

    private static final String VOTES_URI = "/api/v1/votes";
    private final WebTestClient webTestClient;

    public VotingApi(WebTestClient webTestClient) {
        this.webTestClient = webTestClient;
    }

    public ResponseSpec castVote(VoteRequest voteRequest) {
        return webTestClient.post()
                .uri(VOTES_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(voteRequest)
                .exchange();
    }
}
