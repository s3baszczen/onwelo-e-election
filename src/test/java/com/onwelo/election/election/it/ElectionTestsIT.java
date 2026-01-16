package com.onwelo.election.election.it;

import com.onwelo.election.common.it.IntegrationTest;
import com.onwelo.election.election.ElectionApi;
import com.onwelo.election.election.infrastructure.ElectionJpaRepository;
import com.onwelo.election.election.interfaces.CandidateRequest;
import com.onwelo.election.election.interfaces.RegisterElectionRequest;
import com.onwelo.election.election.interfaces.UpdateElectionCandidatesRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient.ResponseSpec;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

import static org.assertj.core.api.Assertions.assertThat;

import static com.onwelo.election.election.CandidateRequestBuilder.candidate;
import static com.onwelo.election.election.RegisterElectionRequestBuilder.electionRequest;
import static com.onwelo.election.election.UpdateElectionCandidatesRequestBuilder.updateCandidatesRequest;

@IntegrationTest
public class ElectionTestsIT {

    @Autowired
    ElectionApi electionApi;

    @Autowired
    ElectionJpaRepository electionRepository;

    @BeforeEach
    void setUp() {
        electionRepository.deleteAll();
    }

    @Test
    public void givenUniqueElectionName_whenRegister_thenElectionShouldBeCreated() {
        RegisterElectionRequest electionRequest = electionRequest(
                "Wybory na Wójta w 2025",
                candidate("Jan Kowalski"),
                candidate("Tomasz Nowak")
        );

        ResponseSpec responseSpec = electionApi.registerElection(electionRequest);
        UUID electionId = extractElectionId(responseSpec);

        itShouldCreateElection(responseSpec);
        itShouldReturnElectionWithCorrectData(electionRequest, responseSpec);
        itShouldPersistElectionInDatabase(electionId, electionRequest);
        itShouldBlockNextRegistrationWithSameName(electionRequest);
    }

    @Test
    public void givenElectionExists_whenGetElection_thenElectionShouldBeReturned() {
        RegisterElectionRequest electionRequest = electionRequest(
                "Wybory na Wójta w 2025",
                candidate("Jan Kowalski"),
                candidate("Tomasz Nowak")
        );
        ResponseSpec createResponse = electionApi.registerElection(electionRequest);
        UUID electionId = extractElectionId(createResponse);

        ResponseSpec responseSpec = electionApi.getElection(electionId);

        itShouldReturnElection(responseSpec);
        itShouldReturnElectionWithCorrectData(electionRequest, responseSpec);
        itShouldPersistElectionInDatabase(electionId, electionRequest);
    }

    @Test
    public void givenElectionExists_whenUpdateCandidates_thenCandidatesShouldBeUpdated() {
        RegisterElectionRequest electionRequest = electionRequest(
                "Wybory na Wójta w 2025",
                candidate("Jan Kowalski"),
                candidate("Tomasz Nowak")
        );
        ResponseSpec createResponse = electionApi.registerElection(electionRequest);
        UUID electionId = extractElectionId(createResponse);

        UpdateElectionCandidatesRequest updateRequest = updateCandidatesRequest(
                candidate("Karol Nowak"),
                candidate("Jan Kowalski"),
                candidate("Tomasz Nowak")
        );
        ResponseSpec responseSpec = electionApi.updateElectionCandidates(electionId, updateRequest);

        itShouldReturnUpdatedCandidates(updateRequest, responseSpec);
        itShouldPersistUpdatedCandidatesInDatabase(electionId, updateRequest);
    }

    private UUID extractElectionId(ResponseSpec responseSpec) {
        AtomicReference<UUID> electionId = new AtomicReference<>();
        responseSpec.expectBody()
                .jsonPath("$.id").value(id -> electionId.set(UUID.fromString(id.toString())));
        return electionId.get();
    }

    private void itShouldCreateElection(ResponseSpec responseSpec) {
        responseSpec
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.id").exists()
                .jsonPath("$.name").exists()
                .jsonPath("$.candidates").isArray();
    }

    private void itShouldReturnElection(ResponseSpec responseSpec) {
        responseSpec
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").exists()
                .jsonPath("$.name").exists()
                .jsonPath("$.candidates").isArray();
    }

    private void itShouldReturnElectionWithCorrectData(RegisterElectionRequest electionRequest, ResponseSpec responseSpec) {
        responseSpec
                .expectBody()
                .jsonPath("$.name").isEqualTo(electionRequest.name())
                .jsonPath("$.candidates.length()").isEqualTo(electionRequest.candidates().size());

        for (CandidateRequest candidate : electionRequest.candidates()) {
            responseSpec
                    .expectBody()
                    .jsonPath(String.format("$.candidates[?(@.name == '%s')]", candidate.name())).exists();
        }
    }

    private void itShouldBlockNextRegistrationWithSameName(RegisterElectionRequest electionRequest) {
        ResponseSpec responseSpec = electionApi.registerElection(electionRequest);
        responseSpec.expectStatus().isEqualTo(409)
                .expectBody()
                .jsonPath("$.status").isEqualTo(409)
                .jsonPath("$.title").isEqualTo("Conflict")
                .jsonPath("$.detail").value(detail -> detail.toString().contains("already exists"));
    }

    private void itShouldReturnUpdatedCandidates(UpdateElectionCandidatesRequest updateRequest, ResponseSpec responseSpec) {
        responseSpec
                .expectBody()
                .jsonPath("$.candidates.length()").isEqualTo(updateRequest.candidates().size());

        for (CandidateRequest candidate : updateRequest.candidates()) {
            responseSpec
                    .expectBody()
                    .jsonPath(String.format("$.candidates[?(@.name == '%s')]", candidate.name())).exists();
        }
    }

    private void itShouldPersistElectionInDatabase(UUID electionId, RegisterElectionRequest electionRequest) {
        var persistedElection = electionRepository.findByIdWithCandidates(electionId);

        assertThat(persistedElection).isPresent();
        assertThat(persistedElection.get().getName().getValue()).isEqualTo(electionRequest.name());
        assertThat(persistedElection.get().getCandidates()).hasSize(electionRequest.candidates().size());

        for (CandidateRequest candidate : electionRequest.candidates()) {
            assertThat(persistedElection.get().getCandidates())
                    .anyMatch(c -> c.getName().equals(candidate.name()));
        }
    }

    private void itShouldPersistUpdatedCandidatesInDatabase(UUID electionId, UpdateElectionCandidatesRequest updateRequest) {
        var persistedElection = electionRepository.findByIdWithCandidates(electionId);

        assertThat(persistedElection).isPresent();
        assertThat(persistedElection.get().getCandidates()).hasSize(updateRequest.candidates().size());

        for (CandidateRequest candidate : updateRequest.candidates()) {
            assertThat(persistedElection.get().getCandidates())
                    .anyMatch(c -> c.getName().equals(candidate.name()));
        }
    }

}
