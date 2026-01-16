package com.onwelo.election.election.domain.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CandidateTest {

    @Test
    void shouldCreateCandidateWithValidName() {
        String candidateName = "John Doe";

        Candidate candidate = new Candidate(candidateName);

        assertThat(candidate.getName()).isEqualTo(candidateName);
    }

    @Test
    void shouldThrowExceptionWhenNameIsNull() {
        assertThatThrownBy(() -> new Candidate(null))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("Candidate name cannot be null");
    }

    @Test
    void shouldThrowExceptionWhenNameIsEmpty() {
        assertThatThrownBy(() -> new Candidate(""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Candidate name cannot be empty");
    }
}
