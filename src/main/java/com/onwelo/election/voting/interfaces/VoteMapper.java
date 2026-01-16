package com.onwelo.election.voting.interfaces;

import com.onwelo.election.voting.domain.model.Vote;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface VoteMapper {

    @Mapping(target = "votedAt", source = "createdAt")
    VoteResponse toResponse(Vote vote);
}
