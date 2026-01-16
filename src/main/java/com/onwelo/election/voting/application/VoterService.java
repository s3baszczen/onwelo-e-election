package com.onwelo.election.voting.application;

import com.onwelo.election.voting.domain.VoterAlreadyExistsException;
import com.onwelo.election.voting.domain.VoterNotFoundException;
import com.onwelo.election.voting.domain.VoterRepository;
import com.onwelo.election.voting.domain.model.Voter;
import com.onwelo.election.voting.domain.model.VoterStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class VoterService {

    private final VoterRepository voterRepository;

    public Voter registerUserAsNewVoter(Voter voter) {
        try {
            return voterRepository.save(voter);
        } catch (DataIntegrityViolationException e) {
            throw new VoterAlreadyExistsException(voter.getExternalUserId(), e);
        }
    }

    @Transactional
    public Voter updateVoterStatus(UUID voterId, VoterStatus newStatus) {
        Voter voter = voterRepository.findById(voterId)
                .orElseThrow(() -> new VoterNotFoundException(voterId));

        if (newStatus == VoterStatus.BLOCKED) {
            voter.block();
        } else {
            voter.activate();
        }
        return voter;
    }

    public Voter getVoter(UUID voterId) {
        return voterRepository.findById(voterId)
                .orElseThrow(() -> new VoterNotFoundException(voterId));
    }
}
