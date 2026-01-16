package com.onwelo.election.election.domain.model;

import com.onwelo.election.election.domain.CandidateNotFoundException;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ElectionTest {

    @Test
    void shouldCreateElectionWithValidNameAndCandidates() {
        ElectionName name = new ElectionName("Presidential Election");
        Set<Candidate> candidates = Set.of(
                new Candidate("John Doe"),
                new Candidate("Jane Smith")
        );

        Election election = new Election(name, candidates);

        assertThat(election.getName()).isEqualTo(name);
        assertThat(election.getCandidates()).hasSize(2);
    }

    @Test
    void shouldThrowExceptionWhenNameIsNull() {
        Set<Candidate> candidates = Set.of(
                new Candidate("John Doe"),
                new Candidate("Jane Smith")
        );

        assertThatThrownBy(() -> new Election(null, candidates))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("Election name cannot be null");
    }

    @Test
    void shouldThrowExceptionWhenCandidatesAreNull() {
        ElectionName name = new ElectionName("Presidential Election");

        assertThatThrownBy(() -> new Election(name, null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void shouldThrowExceptionWhenUpdatingWithLessThanMinimumCandidates() {
        Election election = new Election(
                new ElectionName("Presidential Election"),
                Set.of(new Candidate("John Doe"), new Candidate("Jane Smith"))
        );
        Set<Candidate> newCandidates = new HashSet<>();
        newCandidates.add(new Candidate("Bob Wilson"));

        assertThatThrownBy(() -> election.updateCandidates(newCandidates))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Election must have at least 2 candidates");
    }

    @Test
    void shouldCreateElectionWithMinimumCandidates() {
        ElectionName name = new ElectionName("Presidential Election");
        Set<Candidate> candidates = Set.of(
                new Candidate("John Doe"),
                new Candidate("Jane Smith")
        );

        Election election = new Election(name, candidates);

        assertThat(election.getCandidates()).hasSize(2);
    }

    @Test
    void shouldThrowExceptionWhenAddingCandidateWithDuplicateName() {
        Election election = new Election(
                new ElectionName("Presidential Election"),
                Set.of(new Candidate("John Doe"), new Candidate("Jane Smith"))
        );

        assertThatThrownBy(() -> election.addCandidate(new Candidate("John Doe")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Candidate with name 'John Doe' already exists");
    }

    @Test
    void shouldAddCandidateSuccessfully() {
        Election election = new Election(
                new ElectionName("Presidential Election"),
                Set.of(new Candidate("John Doe"), new Candidate("Jane Smith"))
        );
        Candidate newCandidate = new Candidate("Bob Wilson");

        election.addCandidate(newCandidate);

        assertThat(election.getCandidates()).hasSize(3);
        assertThat(election.getCandidates()).contains(newCandidate);
    }

    @Test
    void shouldThrowExceptionWhenAddingNullCandidate() {
        Election election = new Election(
                new ElectionName("Presidential Election"),
                Set.of(new Candidate("John Doe"), new Candidate("Jane Smith"))
        );

        assertThatThrownBy(() -> election.addCandidate(null))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("Candidate cannot be null");
    }


    @Test
    void shouldRemoveCandidateSuccessfully() {
        Candidate candidate1 = new Candidate("John Doe");
        Candidate candidate2 = new Candidate("Jane Smith");
        Candidate candidate3 = new Candidate("Bob Wilson");
        Election election = new Election(
                new ElectionName("Presidential Election"),
                new HashSet<>(Set.of(candidate1, candidate2, candidate3))
        );

        election.removeCandidate(candidate3);

        assertThat(election.getCandidates()).hasSize(2);
        assertThat(election.getCandidates()).doesNotContain(candidate3);
    }

    @Test
    void shouldThrowExceptionWhenRemovingNullCandidate() {
        Election election = new Election(
                new ElectionName("Presidential Election"),
                Set.of(new Candidate("John Doe"), new Candidate("Jane Smith"))
        );

        assertThatThrownBy(() -> election.removeCandidate(null))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("Candidate cannot be null");
    }

    @Test
    void shouldThrowExceptionWhenRemovingCandidateWouldGobelowMinimum() {
        Candidate candidate1 = new Candidate("John Doe");
        Candidate candidate2 = new Candidate("Jane Smith");
        Election election = new Election(
                new ElectionName("Presidential Election"),
                new HashSet<>(Set.of(candidate1, candidate2))
        );

        assertThatThrownBy(() -> election.removeCandidate(candidate1))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Cannot remove candidate. Election must have at least 2 candidates");
    }

    @Test
    void shouldUpdateCandidatesSuccessfully() {
        Election election = new Election(
                new ElectionName("Presidential Election"),
                Set.of(new Candidate("John Doe"), new Candidate("Jane Smith"))
        );
        Set<Candidate> newCandidates = Set.of(
                new Candidate("Alice Brown"),
                new Candidate("Bob Wilson")
        );

        election.updateCandidates(newCandidates);

        assertThat(election.getCandidates()).hasSize(2);
        assertThat(election.getCandidates())
                .extracting(Candidate::getName)
                .containsExactlyInAnyOrder("Alice Brown", "Bob Wilson");
    }

    @Test
    void shouldThrowExceptionWhenUpdatingWithNullCandidates() {
        Election election = new Election(
                new ElectionName("Presidential Election"),
                Set.of(new Candidate("John Doe"), new Candidate("Jane Smith"))
        );

        assertThatThrownBy(() -> election.updateCandidates(null))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("Candidates cannot be null");
    }

}
