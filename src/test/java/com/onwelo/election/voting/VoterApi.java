package com.onwelo.election.voting;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.reactive.server.WebTestClient.ResponseSpec;

@Component
public class VoterApi {

    private static final String VOTERS_URI = "/api/v1/voters";
    private static final String UPDATE_VOTER_STATUS = VOTERS_URI + "/{voterId}/status";
    private final WebTestClient webTestClient;

    public VoterApi(WebTestClient webTestClient) {
        this.webTestClient = webTestClient;
    }

    public ResponseSpec registerVoter(RegisterVoterRequest voterRequest) {
        return webTestClient.post()
                .uri(VOTERS_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(voterRequest)
                .exchange();
    }

    public ResponseSpec updateVoterStatus(UpdateVoterStatusRequest updateVoterStatusRequest) {
        return webTestClient.patch()
                .uri(UPDATE_VOTER_STATUS, updateVoterStatusRequest.voterId())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(updateVoterStatusRequest)
                .exchange();
    }
}
