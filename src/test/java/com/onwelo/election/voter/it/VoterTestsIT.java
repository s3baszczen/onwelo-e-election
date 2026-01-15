package com.onwelo.election.voter.it;

import com.onwelo.election.common.it.IntegrationTest;
import com.onwelo.election.voter.RegisterVoterRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

@IntegrationTest
public class VoterTestsIT {

    @Autowired
    WebTestClient webTestClient;
    private static final String VOTERS_URI = "/api/v1/voters";

    @Test
    public void givenIAmNotAUser_whenIRegister() {
        webTestClient.post()
                .uri(VOTERS_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new RegisterVoterRequest("Adam", "Kowalski"))
                .exchange()
                .expectStatus().isCreated();
    }

}
