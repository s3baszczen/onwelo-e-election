package com.onwelo.election.voting.it;

import com.onwelo.election.common.it.IntegrationTest;
import com.onwelo.election.election.domain.model.Candidate;
import com.onwelo.election.election.domain.model.Election;
import com.onwelo.election.election.domain.model.ElectionName;
import com.onwelo.election.election.infrastructure.ElectionJpaRepository;
import com.onwelo.election.voting.VoteRequest;
import com.onwelo.election.voting.VotingApi;
import com.onwelo.election.voting.domain.VoteRepository;
import com.onwelo.election.voting.domain.VoterRepository;
import com.onwelo.election.voting.domain.model.Voter;
import com.onwelo.election.voting.domain.model.VoterStatus;
import com.onwelo.election.voting.infrastructure.VoteJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient.ResponseSpec;

import java.util.Set;
import java.util.UUID;

@IntegrationTest
public class VotingTestsIT {

    @Autowired
    VotingApi votingApi;

    @Autowired
    VoterRepository voterRepository;

    @Autowired
    ElectionJpaRepository electionRepository;

    @Autowired
    VoteJpaRepository voteJpaRepository;

    private Voter activeVoter;
    private Voter blockedVoter;
    private Election election;
    private Candidate candidate1;
    private Candidate candidate2;

    @BeforeEach
    void setUp() {
        voteJpaRepository.deleteAll();
        electionRepository.deleteAll();

        // Create test voters
        activeVoter = voterRepository.save(new Voter(UUID.randomUUID(), VoterStatus.ACTIVE));
        blockedVoter = voterRepository.save(new Voter(UUID.randomUUID(), VoterStatus.BLOCKED));

        // Create test election with candidates
        candidate1 = new Candidate("Jan Kowalski");
        candidate2 = new Candidate("Tomasz Nowak");
        election = new Election(
                new ElectionName("Wybory na Wójta 2025"),
                Set.of(candidate1, candidate2)
        );
        election = ((org.springframework.data.jpa.repository.JpaRepository<Election, UUID>) electionRepository).save(election);

        // Refresh candidates after save to get their IDs
        candidate1 = election.getCandidates().stream()
                .filter(c -> c.getName().equals("Jan Kowalski"))
                .findFirst()
                .orElseThrow();
        candidate2 = election.getCandidates().stream()
                .filter(c -> c.getName().equals("Tomasz Nowak"))
                .findFirst()
                .orElseThrow();
    }

    @Test
    public void givenActiveVoterAndValidElection_whenCastVote_thenVoteShouldBeCreated() {
        VoteRequest voteRequest = new VoteRequest(
                activeVoter.getId(),
                election.getId(),
                candidate1.getId()
        );

        ResponseSpec responseSpec = votingApi.castVote(voteRequest);

        itShouldCreateVote(responseSpec);
        itShouldReturnVoteWithCorrectData(voteRequest, responseSpec);
    }

    @Test
    public void givenVoterAlreadyVoted_whenCastVoteAgain_thenShouldReturnConflict() {
        VoteRequest firstVote = new VoteRequest(
                activeVoter.getId(),
                election.getId(),
                candidate1.getId()
        );
        votingApi.castVote(firstVote);

        VoteRequest secondVote = new VoteRequest(
                activeVoter.getId(),
                election.getId(),
                candidate2.getId()
        );
        ResponseSpec responseSpec = votingApi.castVote(secondVote);

        itShouldReturnConflictForDuplicateVote(responseSpec);
    }

    @Test
    public void givenBlockedVoter_whenCastVote_thenShouldReturnBadRequest() {
        VoteRequest voteRequest = new VoteRequest(
                blockedVoter.getId(),
                election.getId(),
                candidate1.getId()
        );

        ResponseSpec responseSpec = votingApi.castVote(voteRequest);

        itShouldReturnBadRequestForBlockedVoter(responseSpec);
    }

    @Test
    public void givenNonExistentVoter_whenCastVote_thenShouldReturnNotFound() {
        VoteRequest voteRequest = new VoteRequest(
                UUID.randomUUID(),
                election.getId(),
                candidate1.getId()
        );

        ResponseSpec responseSpec = votingApi.castVote(voteRequest);

        itShouldReturnNotFoundForNonExistentVoter(responseSpec);
    }

    @Test
    public void givenNonExistentElection_whenCastVote_thenShouldReturnNotFound() {
        VoteRequest voteRequest = new VoteRequest(
                activeVoter.getId(),
                UUID.randomUUID(),
                candidate1.getId()
        );

        ResponseSpec responseSpec = votingApi.castVote(voteRequest);

        itShouldReturnNotFoundForNonExistentElection(responseSpec);
    }

    @Test
    public void givenNonExistentCandidate_whenCastVote_thenShouldReturnNotFound() {
        VoteRequest voteRequest = new VoteRequest(
                activeVoter.getId(),
                election.getId(),
                UUID.randomUUID()
        );

        ResponseSpec responseSpec = votingApi.castVote(voteRequest);

        itShouldReturnNotFoundForNonExistentCandidate(responseSpec);
    }

    private void itShouldCreateVote(ResponseSpec responseSpec) {
        responseSpec
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.id").exists()
                .jsonPath("$.voterId").exists()
                .jsonPath("$.electionId").exists()
                .jsonPath("$.candidateId").exists()
                .jsonPath("$.votedAt").exists();
    }

    private void itShouldReturnVoteWithCorrectData(VoteRequest voteRequest, ResponseSpec responseSpec) {
        responseSpec
                .expectBody()
                .jsonPath("$.voterId").isEqualTo(voteRequest.voterId().toString())
                .jsonPath("$.electionId").isEqualTo(voteRequest.electionId().toString())
                .jsonPath("$.candidateId").isEqualTo(voteRequest.candidateId().toString());
    }

    private void itShouldReturnConflictForDuplicateVote(ResponseSpec responseSpec) {
        responseSpec
                .expectStatus().isEqualTo(409)
                .expectBody()
                .jsonPath("$.status").isEqualTo(409)
                .jsonPath("$.title").isEqualTo("Conflict")
                .jsonPath("$.detail").value(detail -> detail.toString().contains("already voted"));
    }

    private void itShouldReturnBadRequestForBlockedVoter(ResponseSpec responseSpec) {
        responseSpec
                .expectStatus().isEqualTo(400)
                .expectBody()
                .jsonPath("$.status").isEqualTo(400)
                .jsonPath("$.title").isEqualTo("Bad Request")
                .jsonPath("$.detail").value(detail -> detail.toString().contains("blocked"));
    }

    private void itShouldReturnNotFoundForNonExistentVoter(ResponseSpec responseSpec) {
        responseSpec
                .expectStatus().isEqualTo(404)
                .expectBody()
                .jsonPath("$.status").isEqualTo(404)
                .jsonPath("$.title").isEqualTo("Not Found")
                .jsonPath("$.detail").value(detail -> detail.toString().contains("Voter not found"));
    }

    private void itShouldReturnNotFoundForNonExistentElection(ResponseSpec responseSpec) {
        responseSpec
                .expectStatus().isEqualTo(404)
                .expectBody()
                .jsonPath("$.status").isEqualTo(404)
                .jsonPath("$.title").isEqualTo("Not Found")
                .jsonPath("$.detail").value(detail -> detail.toString().contains("Election not found"));
    }

    private void itShouldReturnNotFoundForNonExistentCandidate(ResponseSpec responseSpec) {
        responseSpec
                .expectStatus().isEqualTo(404)
                .expectBody()
                .jsonPath("$.status").isEqualTo(404)
                .jsonPath("$.title").isEqualTo("Not Found")
                .jsonPath("$.detail").value(detail -> detail.toString().contains("Candidate not found"));
    }
}
