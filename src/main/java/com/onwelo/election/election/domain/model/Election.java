package com.onwelo.election.election.domain.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "election", uniqueConstraints = {
    @UniqueConstraint(name = "election_name", columnNames = "value")
})
public class Election {

    private static final int MIN_CANDIDATES = 2;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Embedded
    private ElectionName name;

    private LocalDateTime startsAt;
    private LocalDateTime endsAt;

    @OneToMany(mappedBy = "election", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Candidate> candidates = new HashSet<>();

    public Election(ElectionName name, Set<Candidate> candidates) {
        this.name = Objects.requireNonNull(name, "Election name cannot be null");
        setCandidates(candidates);
    }

    public void addCandidate(Candidate candidate) {
        Objects.requireNonNull(candidate, "Candidate cannot be null");
        validateUniqueCandidateName(candidate.getName());
        candidate.setElection(this);
        this.candidates.add(candidate);
    }

    public void removeCandidate(Candidate candidate) {
        Objects.requireNonNull(candidate, "Candidate cannot be null");
        if (this.candidates.size() <= MIN_CANDIDATES) {
            throw new IllegalStateException(
                String.format("Cannot remove candidate. Election must have at least %d candidates", MIN_CANDIDATES)
            );
        }
        this.candidates.remove(candidate);
    }

    public void updateCandidates(Set<Candidate> newCandidates) {
        validateCandidates(newCandidates);
        this.candidates.clear();
        setCandidates(newCandidates);
    }

    private void setCandidates(Set<Candidate> candidates) {
        validateUniqueCandidateNames(candidates);
        candidates.forEach(candidate -> {
            candidate.setElection(this);
            this.candidates.add(candidate);
        });
    }

    private void validateCandidates(Set<Candidate> candidates) {
        Objects.requireNonNull(candidates, "Candidates cannot be null");
        if (candidates.size() < MIN_CANDIDATES) {
            throw new IllegalArgumentException(
                String.format("Election must have at least %d candidates", MIN_CANDIDATES)
            );
        }
    }

    private void validateUniqueCandidateNames(Set<Candidate> candidates) {
        Set<String> names = new HashSet<>();
        for (Candidate candidate : candidates) {
            if (!names.add(candidate.getName())) {
                throw new IllegalArgumentException(
                    String.format("Duplicate candidate name: %s", candidate.getName())
                );
            }
        }
    }

    private void validateUniqueCandidateName(String name) {
        boolean nameExists = this.candidates.stream()
            .anyMatch(c -> c.getName().equals(name));
        if (nameExists) {
            throw new IllegalArgumentException(
                String.format("Candidate with name '%s' already exists in this election", name)
            );
        }
    }

    public Set<Candidate> getCandidates() {
        return Collections.unmodifiableSet(candidates);
    }
}
