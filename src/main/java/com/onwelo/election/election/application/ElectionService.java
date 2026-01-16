package com.onwelo.election.election.application;

import com.onwelo.election.election.domain.ElectionAlreadyExistsException;
import com.onwelo.election.election.domain.ElectionNotFoundException;
import com.onwelo.election.election.domain.ElectionRepository;
import com.onwelo.election.election.domain.model.Candidate;
import com.onwelo.election.election.domain.model.Election;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ElectionService {

    private final ElectionRepository electionRepository;

    public Election registerElection(Election election) {
        try {
            return electionRepository.save(election);
        } catch (DataIntegrityViolationException e) {
            throw new ElectionAlreadyExistsException(election.getName().getValue(), e);
        }
    }

    @Transactional
    public Election updateElectionCandidates(UUID electionId, Set<Candidate> newCandidates) {
        Election election = electionRepository.findById(electionId)
                .orElseThrow(() -> new ElectionNotFoundException(electionId));

        election.updateCandidates(newCandidates);
        return electionRepository.save(election);
    }

    @Transactional(readOnly = true)
    public Election getElection(UUID electionId) {
        return electionRepository.findByIdWithCandidates(electionId)
                .orElseThrow(() -> new ElectionNotFoundException(electionId));
    }
}
