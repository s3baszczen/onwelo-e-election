package com.onwelo.election.voting.domain.model;

import com.onwelo.election.voting.domain.VoterBlockedException;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class VoterTest {

    @Test
    void shouldCreateVoterWithValidParameters() {
        UUID externalUserId = UUID.randomUUID();
        VoterStatus status = VoterStatus.ACTIVE;

        Voter voter = new Voter(externalUserId, status);

        assertThat(voter.getExternalUserId()).isEqualTo(externalUserId);
        assertThat(voter.getStatus()).isEqualTo(status);
    }

    @Test
    void shouldThrowExceptionWhenExternalUserIdIsNull() {
        assertThatThrownBy(() -> new Voter(null, VoterStatus.ACTIVE))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("externalUserId cannot be null");
    }

    @Test
    void shouldThrowExceptionWhenStatusIsNull() {
        UUID externalUserId = UUID.randomUUID();

        assertThatThrownBy(() -> new Voter(externalUserId, null))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("status cannot be null");
    }

    @Test
    void shouldBlockActiveVoter() {
        Voter voter = new Voter(UUID.randomUUID(), VoterStatus.ACTIVE);

        voter.block();

        assertThat(voter.getStatus()).isEqualTo(VoterStatus.BLOCKED);
    }

    @Test
    void shouldBlockAlreadyBlockedVoter() {
        Voter voter = new Voter(UUID.randomUUID(), VoterStatus.BLOCKED);

        voter.block();

        assertThat(voter.getStatus()).isEqualTo(VoterStatus.BLOCKED);
    }

    @Test
    void shouldActivateBlockedVoter() {
        Voter voter = new Voter(UUID.randomUUID(), VoterStatus.BLOCKED);

        voter.activate();

        assertThat(voter.getStatus()).isEqualTo(VoterStatus.ACTIVE);
    }

    @Test
    void shouldActivateAlreadyActiveVoter() {
        Voter voter = new Voter(UUID.randomUUID(), VoterStatus.ACTIVE);

        voter.activate();

        assertThat(voter.getStatus()).isEqualTo(VoterStatus.ACTIVE);
    }

    @Test
    void shouldCreateVoteWhenVoterIsActive() throws Exception {
        Voter voter = new Voter(UUID.randomUUID(), VoterStatus.ACTIVE);
        UUID voterId = UUID.randomUUID();
        setVoterId(voter, voterId);

        UUID electionId = UUID.randomUUID();
        UUID candidateId = UUID.randomUUID();

        Vote vote = voter.vote(electionId, candidateId);

        assertThat(vote).isNotNull();
        assertThat(vote.getVoterId()).isEqualTo(voterId);
        assertThat(vote.getElectionId()).isEqualTo(electionId);
        assertThat(vote.getCandidateId()).isEqualTo(candidateId);
    }

    private void setVoterId(Voter voter, UUID id) throws Exception {
        var field = Voter.class.getDeclaredField("id");
        field.setAccessible(true);
        field.set(voter, id);
    }

    @Test
    void shouldThrowExceptionWhenBlockedVoterTriesToVote() {
        Voter voter = new Voter(UUID.randomUUID(), VoterStatus.BLOCKED);
        UUID electionId = UUID.randomUUID();
        UUID candidateId = UUID.randomUUID();

        assertThatThrownBy(() -> voter.vote(electionId, candidateId))
                .isInstanceOf(VoterBlockedException.class);
    }

    @Test
    void shouldAllowBlockedVoterToVoteAfterActivation() throws Exception {
        Voter voter = new Voter(UUID.randomUUID(), VoterStatus.BLOCKED);
        setVoterId(voter, UUID.randomUUID());

        UUID electionId = UUID.randomUUID();
        UUID candidateId = UUID.randomUUID();

        assertThatThrownBy(() -> voter.vote(electionId, candidateId))
                .isInstanceOf(VoterBlockedException.class);

        voter.activate();
        Vote vote = voter.vote(electionId, candidateId);

        assertThat(vote).isNotNull();
    }

    @Test
    void shouldBeEqualWhenIdsAreTheSame() {
        UUID externalUserId = UUID.randomUUID();
        Voter voter1 = new Voter(externalUserId, VoterStatus.ACTIVE);

        assertThat(voter1).isEqualTo(voter1);
    }

    @Test
    void shouldHaveConsistentHashCode() {
        Voter voter = new Voter(UUID.randomUUID(), VoterStatus.ACTIVE);

        int hashCode1 = voter.hashCode();
        int hashCode2 = voter.hashCode();

        assertThat(hashCode1).isEqualTo(hashCode2);
    }

    @Test
    void shouldNotBeEqualToNull() {
        Voter voter = new Voter(UUID.randomUUID(), VoterStatus.ACTIVE);

        assertThat(voter).isNotEqualTo(null);
    }

    @Test
    void shouldNotBeEqualToDifferentClass() {
        Voter voter = new Voter(UUID.randomUUID(), VoterStatus.ACTIVE);

        assertThat(voter).isNotEqualTo("Not a Voter");
    }
}
