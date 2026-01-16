package com.onwelo.election.election.domain.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ElectionNameTest {

    @Test
    void shouldCreateElectionNameWithValidValue() {
        String validName = "Presidential Election 2024";

        ElectionName electionName = new ElectionName(validName);

        assertThat(electionName.getValue()).isEqualTo(validName);
    }

    @Test
    void shouldTrimWhitespacesWhenCreating() {
        String nameWithSpaces = "  Presidential Election  ";

        ElectionName electionName = new ElectionName(nameWithSpaces);

        assertThat(electionName.getValue()).isEqualTo("Presidential Election");
    }

    @Test
    void shouldThrowExceptionWhenNameIsNull() {
        assertThatThrownBy(() -> new ElectionName(null))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("Election name cannot be null");
    }

    @Test
    void shouldThrowExceptionWhenNameIsEmpty() {
        assertThatThrownBy(() -> new ElectionName(""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Election name cannot be empty");
    }

    @Test
    void shouldThrowExceptionWhenNameIsBlank() {
        assertThatThrownBy(() -> new ElectionName("   "))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Election name cannot be empty");
    }

    @Test
    void shouldThrowExceptionWhenNameIsTooShort() {
        String tooShortName = "AB";

        assertThatThrownBy(() -> new ElectionName(tooShortName))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Election name must be at least 3 characters long");
    }

    @Test
    void shouldCreateElectionNameWithMinimumLength() {
        String minLengthName = "ABC";

        ElectionName electionName = new ElectionName(minLengthName);

        assertThat(electionName.getValue()).isEqualTo(minLengthName);
    }

    @Test
    void shouldCreateElectionNameWithMaximumLength() {
        String maxLengthName = "A".repeat(200);

        ElectionName electionName = new ElectionName(maxLengthName);

        assertThat(electionName.getValue()).isEqualTo(maxLengthName);
    }

    @Test
    void shouldThrowExceptionWhenNameIsTooLong() {
        String tooLongName = "A".repeat(201);

        assertThatThrownBy(() -> new ElectionName(tooLongName))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Election name must not exceed 200 characters");
    }

    @Test
    void shouldBeEqualWhenValuesAreTheSame() {
        ElectionName name1 = new ElectionName("Presidential Election");
        ElectionName name2 = new ElectionName("Presidential Election");

        assertThat(name1).isEqualTo(name2);
        assertThat(name1.hashCode()).isEqualTo(name2.hashCode());
    }

    @Test
    void shouldNotBeEqualWhenValuesAreDifferent() {
        ElectionName name1 = new ElectionName("Presidential Election");
        ElectionName name2 = new ElectionName("Parliamentary Election");

        assertThat(name1).isNotEqualTo(name2);
    }
}
