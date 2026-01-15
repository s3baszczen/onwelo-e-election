package com.onwelo.election.voting.interfaces;

import com.onwelo.election.voting.domain.model.Voter;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.Mapping;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
interface VoterMapper {

    @Mapping(target = "status", source = "voterStatus")
    @Mapping(target = "id", ignore = true)
    Voter toDomain(RegisterVoterRequest request);

    VoterResponse toResponse(Voter voter);
}