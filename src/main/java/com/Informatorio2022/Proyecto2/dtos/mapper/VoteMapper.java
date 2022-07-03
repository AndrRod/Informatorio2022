package com.Informatorio2022.Proyecto2.dtos.mapper;

import com.Informatorio2022.Proyecto2.dtos.VoteDtoComplete;
import com.Informatorio2022.Proyecto2.dtos.VoteDtoPart;
import com.Informatorio2022.Proyecto2.model.Vote;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class VoteMapper {
    public VoteDtoPart createVoteDto(VoteDtoComplete vote) {
       return new VoteDtoPart(null, vote.getCreatedBy().getFirstName() + vote.getCreatedBy().getLastName(), vote.getEntrepreneurshipVoted().getName(), vote.getEvent().getEventDetails(), null);
    }
    public VoteDtoPart getDtoFromEntity(Vote vote){
        return new VoteDtoPart(vote.getId(), vote.getCreatedBy().getFirstName() + vote.getCreatedBy().getLastName(), vote.getEntrepreneurshipVoted().getName(), vote.getEvent().getEventDetails(), vote.getCreateDate());
    }
    public List<Object> ListDtoFromListEntity(List<Vote> votes){
        return votes.stream().map(v-> getDtoFromEntity(v)).collect(Collectors.toList());
    }

}
