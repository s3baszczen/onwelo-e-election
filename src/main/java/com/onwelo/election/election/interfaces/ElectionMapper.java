package com.onwelo.election.election.interfaces;

import com.onwelo.election.election.domain.model.Candidate;
import com.onwelo.election.election.domain.model.Election;
import com.onwelo.election.election.domain.model.ElectionName;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
interface ElectionMapper {

    ElectionResponse toResponse(Election election);

    CandidateResponse toResponse(Candidate candidate);

    default Election toDomain(RegisterElectionRequest request, Set<Candidate> candidates) {
        ElectionName electionName = new ElectionName(request.name());
        return new Election(electionName, candidates);
    }

    default Set<Candidate> candidateRequestsToDomain(Set<CandidateRequest> requests) {
        return requests.stream()
            .map(req -> new Candidate(req.name()))
            .collect(Collectors.toSet());
    }

    default Set<CandidateResponse> candidatesToResponses(Set<Candidate> candidates) {
        return candidates.stream()
            .map(this::toResponse)
            .collect(Collectors.toSet());
    }

    default String electionNameToString(ElectionName electionName) {
        return electionName.getValue();
    }
}
