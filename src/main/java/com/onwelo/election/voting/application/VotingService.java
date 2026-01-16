package com.onwelo.election.voting.application;

import com.onwelo.election.election.application.ElectionService;
import com.onwelo.election.election.domain.model.Candidate;
import com.onwelo.election.election.domain.model.Election;
import com.onwelo.election.voting.domain.AlreadyVotedException;
import com.onwelo.election.voting.domain.VoteRepository;
import com.onwelo.election.voting.domain.model.Vote;
import com.onwelo.election.voting.domain.model.Voter;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class VotingService {

    private final VoterService voterService;
    private final VoteRepository voteRepository;
    private final ElectionService electionService;

    public Vote vote(UUID voterId, UUID electionId, UUID candidateId) {
        Voter voter = voterService.getVoter(voterId);
        Election election = electionService.getElection(electionId);
        Candidate candidate = election.findCandidateById(candidateId);

        Vote vote = voter.vote(electionId, candidateId);

        try {
            return voteRepository.save(vote);
        } catch (DataIntegrityViolationException e) {
            throw new AlreadyVotedException(voterId, electionId, e);
        }
    }
}
