package com.onwelo.election.voting.domain.model;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.within;

class VoteTest {

    @Test
    void shouldCreateVoteWithValidParameters() {
        UUID voterId = UUID.randomUUID();
        UUID electionId = UUID.randomUUID();
        UUID candidateId = UUID.randomUUID();
        Instant beforeCreation = Instant.now();

        Vote vote = new Vote(voterId, electionId, candidateId);

        Instant afterCreation = Instant.now();

        assertThat(vote.getVoterId()).isEqualTo(voterId);
        assertThat(vote.getElectionId()).isEqualTo(electionId);
        assertThat(vote.getCandidateId()).isEqualTo(candidateId);
        assertThat(vote.getCreatedAt()).isNotNull();
        assertThat(vote.getCreatedAt()).isBetween(beforeCreation, afterCreation);
    }

    @Test
    void shouldSetCreatedAtToCurrentTime() {
        UUID voterId = UUID.randomUUID();
        UUID electionId = UUID.randomUUID();
        UUID candidateId = UUID.randomUUID();

        Vote vote = new Vote(voterId, electionId, candidateId);

        assertThat(vote.getCreatedAt()).isCloseTo(Instant.now(), within(1, java.time.temporal.ChronoUnit.SECONDS));
    }

    @Test
    void shouldThrowExceptionWhenVoterIdIsNull() {
        UUID electionId = UUID.randomUUID();
        UUID candidateId = UUID.randomUUID();

        assertThatThrownBy(() -> new Vote(null, electionId, candidateId))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("Voter ID cannot be null");
    }

    @Test
    void shouldThrowExceptionWhenElectionIdIsNull() {
        UUID voterId = UUID.randomUUID();
        UUID candidateId = UUID.randomUUID();

        assertThatThrownBy(() -> new Vote(voterId, null, candidateId))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("Election ID cannot be null");
    }

    @Test
    void shouldThrowExceptionWhenCandidateIdIsNull() {
        UUID voterId = UUID.randomUUID();
        UUID electionId = UUID.randomUUID();

        assertThatThrownBy(() -> new Vote(voterId, electionId, null))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("Candidate ID cannot be null");
    }

    @Test
    void shouldBeEqualWhenIdsAreTheSame() {
        UUID voterId = UUID.randomUUID();
        UUID electionId = UUID.randomUUID();
        UUID candidateId = UUID.randomUUID();
        Vote vote1 = new Vote(voterId, electionId, candidateId);

        assertThat(vote1).isEqualTo(vote1);
    }

    @Test
    void shouldHaveConsistentHashCode() {
        UUID voterId = UUID.randomUUID();
        UUID electionId = UUID.randomUUID();
        UUID candidateId = UUID.randomUUID();
        Vote vote = new Vote(voterId, electionId, candidateId);

        int hashCode1 = vote.hashCode();
        int hashCode2 = vote.hashCode();

        assertThat(hashCode1).isEqualTo(hashCode2);
    }
}
