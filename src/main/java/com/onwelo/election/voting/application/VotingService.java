package com.onwelo.election.voting.application;

import com.onwelo.election.election.application.ElectionService;
import com.onwelo.election.election.domain.model.Candidate;
import com.onwelo.election.election.domain.model.Election;
import com.onwelo.election.voting.domain.*;
import com.onwelo.election.voting.domain.model.Vote;
import com.onwelo.election.voting.domain.model.Voter;
import com.onwelo.election.voting.domain.model.VoterStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class VotingService {

    private final VoterService voterService;
    private final VoteRepository voteRepository;
    private final ElectionService electionService;

    @Transactional
    public Vote vote(UUID voterId, UUID electionId, UUID candidateId) {
        Voter voter = voterService.getVoter(voterId);

        // 2. Sprawdź czy wyborca nie jest zablokowany
        if (voter.getStatus() == VoterStatus.BLOCKED) {
            throw new VoterBlockedException(voterId);
        }

        Election election = electionService.getElection(electionId);

        Candidate candidate = election.getCandidates().stream()
                .filter(c -> c.getId().equals(candidateId))
                .findFirst()
                .orElseThrow(() -> new CandidateNotFoundException(candidateId));

        if (!candidate.getElection().getId().equals(electionId)) {
            throw new CandidateNotInElectionException(candidateId, electionId);
        }

        Vote vote = new Vote(voterId, electionId, candidateId);
        return voteRepository.save(vote);
    }
}
