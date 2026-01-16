package com.onwelo.election.voting.it;

import com.onwelo.election.common.it.IntegrationTest;
import com.onwelo.election.voting.RegisterVoterRequest;
import com.onwelo.election.voting.Status;
import com.onwelo.election.voting.UpdateVoterStatusRequest;
import com.onwelo.election.voting.VoterApi;
import com.onwelo.election.voting.domain.VoterRepository;
import com.onwelo.election.voting.domain.model.Voter;
import com.onwelo.election.voting.domain.model.VoterStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.reactive.server.WebTestClient.ResponseSpec;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@IntegrationTest
public class VoterTestsIT {

    @Autowired
    VoterApi voterApi;

    @Autowired
    VoterRepository voterRepository;

    @Test
    public void givenIAmNotAVoter_whenIRegister() {
        RegisterVoterRequest voterRequest = new RegisterVoterRequest(UUID.randomUUID(), Status.ACTIVE);

        ResponseSpec responseSpec = voterApi.registerVoter(voterRequest);

        voterStatusShouldBe(VoterStatus.ACTIVE, responseSpec);
        itShouldRegisterANewVoter(responseSpec);
        itShouldBlockNextRegistration(voterRequest);
    }

    @Test
    public void givenActiveVoter_whenIBlock_thenVoterShouldBeBlocked() {
        UUID externalUserId = UUID.randomUUID();
        Voter activeVoter = new Voter(externalUserId, VoterStatus.ACTIVE);
        Voter savedVoter = voterRepository.save(activeVoter);

        UpdateVoterStatusRequest blockRequest = new UpdateVoterStatusRequest(savedVoter.getId(), VoterStatus.BLOCKED);
        ResponseSpec responseSpec = voterApi.updateVoterStatus(blockRequest);

        voterStatusShouldBe(VoterStatus.BLOCKED, responseSpec);
    }

    private void voterStatusShouldBe(VoterStatus voterStatus, ResponseSpec responseSpec) {
        responseSpec
                .expectBody()
                .jsonPath("$.status").isEqualTo(voterStatus.name());
    }

    private void itShouldBlockNextRegistration(RegisterVoterRequest voterRequest) {
        ResponseSpec responseSpec = voterApi.registerVoter(voterRequest);
        responseSpec.expectStatus().isEqualTo(409)
                .expectBody()
                .jsonPath("$.status").isEqualTo(409)
                .jsonPath("$.title").isEqualTo("Conflict")
                .jsonPath("$.detail").value(detail -> detail.toString().contains("already exists"));
    }

    private void itShouldRegisterANewVoter(ResponseSpec responseSpec) {
        responseSpec
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.id").value(id -> {
                    assertThat(id).isNotNull();
                    Voter voter = voterRepository.findById(UUID.fromString(id.toString())).orElse(null);
                    assertThat(voter).isNotNull();
                });
    }

}
