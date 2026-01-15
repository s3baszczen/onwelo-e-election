package com.onwelo.election.voting.interfaces;

import com.onwelo.election.voting.RegisterVoterRequest;
import com.onwelo.election.voting.Status;
import com.onwelo.election.voting.VoterApi;
import com.onwelo.election.voting.application.VoterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.boot.webtestclient.autoconfigure.AutoConfigureWebTestClient;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.UUID;


@WebMvcTest(VoterController.class)
@AutoConfigureWebTestClient
public class VoterControllerTests {

    @Autowired
    private WebTestClient webTestClient;

    @MockitoBean
    private VoterService voterService;

    @MockitoBean
    private VoterMapper voterMapper;

    private VoterApi voterApi;

    @BeforeEach
    void setUp() {
        voterApi = new VoterApi(webTestClient);
    }

    @Test
    public void shouldReturnBadRequestWhenExternalUserIdIsNull() {
        voterApi.registerVoter(new RegisterVoterRequest(null, Status.ACTIVE))
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("$.detail").isEqualTo("Request validation failed")
                .jsonPath("$.errors.externalUserId").isEqualTo("must not be null");
    }

    @Test
    public void shouldReturnBadRequestWhenVoterStatusIsNull() {
        voterApi.registerVoter(new RegisterVoterRequest(UUID.randomUUID(), null))
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("$.detail").isEqualTo("Request validation failed")
                .jsonPath("$.errors.voterStatus").isEqualTo("must not be null");
    }
}