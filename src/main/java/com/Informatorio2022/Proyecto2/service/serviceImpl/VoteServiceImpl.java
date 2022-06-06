package com.Informatorio2022.Proyecto2.service.serviceImpl;

import com.Informatorio2022.Proyecto2.model.Vote;
import com.Informatorio2022.Proyecto2.repository.VoteRepository;
import com.Informatorio2022.Proyecto2.service.VoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VoteServiceImpl implements VoteService {
    @Autowired
    private VoteRepository voteRepository;
    @Override
    public List<Vote> listVotes() {
        return voteRepository.findAll();
    }
    @Override
    public Vote findVotesEventById(Long id) {
        return voteRepository.findById(id).get();
    }

    @Override
    public Map<String, Integer> rankingByEvents(Long id) {
        List<Vote> listVoteByEvent = voteRepository.listVoteByEventId(id);
        Map<String, Integer> mapRanking = new HashMap();
        listVoteByEvent.stream().forEach(v->
                mapRanking.put(v.getEntrepreneurshipVoted().getName() + " votes", Math.toIntExact(voteRepository.countVoteByEntreprNameAndByEventId(v.getEntrepreneurshipVoted().getName(), id)))
//                        (int) listVoteByEvent.stream().filter(vf-> vf.getEntrepreneurshipVoted().getName()== v.getEntrepreneurshipVoted().getName()).count())
            );
        return mapRanking.entrySet()
                .stream()
                .sorted((Map.Entry.<String, Integer>comparingByValue().reversed()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }

    @Override
    public HashMap<String, Long> countVoteEntrepName(String name, Long id) {
        return new HashMap<>(){{put("votes", voteRepository.countVoteByEntreprNameAndByEventId(name, id));}};
    }
}

