package com.onwelo.election.election;

import com.onwelo.election.election.interfaces.RegisterElectionRequest;
import com.onwelo.election.election.interfaces.UpdateElectionCandidatesRequest;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.reactive.server.WebTestClient.ResponseSpec;

import java.util.UUID;

@Component
public class ElectionApi {

    private static final String ELECTIONS_URI = "/api/v1/elections";
    private static final String GET_ELECTION_URI = ELECTIONS_URI + "/{electionId}";
    private static final String UPDATE_CANDIDATES_URI = ELECTIONS_URI + "/{electionId}/candidates";
    private final WebTestClient webTestClient;

    public ElectionApi(WebTestClient webTestClient) {
        this.webTestClient = webTestClient;
    }

    public ResponseSpec registerElection(RegisterElectionRequest electionRequest) {
        return webTestClient.post()
                .uri(ELECTIONS_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(electionRequest)
                .exchange();
    }

    public ResponseSpec getElection(UUID electionId) {
        return webTestClient.get()
                .uri(GET_ELECTION_URI, electionId)
                .exchange();
    }

    public ResponseSpec updateElectionCandidates(UUID electionId, UpdateElectionCandidatesRequest request) {
        return webTestClient.patch()
                .uri(UPDATE_CANDIDATES_URI, electionId)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange();
    }
}
