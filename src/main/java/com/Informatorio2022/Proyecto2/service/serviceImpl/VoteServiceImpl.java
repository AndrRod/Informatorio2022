package com.Informatorio2022.Proyecto2.service.serviceImpl;

import com.Informatorio2022.Proyecto2.dtos.EntreprAndVotes;
import com.Informatorio2022.Proyecto2.model.Vote;
import com.Informatorio2022.Proyecto2.repository.VoteRepository;
import com.Informatorio2022.Proyecto2.service.VoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VoteServiceImpl implements VoteService {
    private static final int RANKING = 10;
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

//    Esta Query resume el metodo de abajo
//    @Query("SELECT new com.Informatorio2022.Proyecto2.dtos.EntreprAndVotes(v.entrepreneurshipVoted.name, COUNT(v)) FROM Vote v WHERE v.event.id= :id GROUP BY v.entrepreneurshipVoted.name")
//    List<EntreprAndVotes> entreprAndVotesByGroups(@Param("id") Long id);
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
//    @Override
//    public HashMap<String, Long> countVoteEntrepName(String name, Long id) {
//        return new HashMap<>(){{put("votes", voteRepository.countVoteByEntreprNameAndByEventId(name, id));}};
//    }
    @Override
    public EntreprAndVotes findEntrepreAndVotes(String name, Long id) {
        return voteRepository.entreprAndVotes(name, id);
    }
//    @Override
//    public List<EntreprAndVotes> listRankingEvents(Long id) {
//        List<Vote> listVoteByEvent = voteRepository.listVoteByEventId(id);
//        List<EntreprAndVotes> entreprAndVotes = new ArrayList<>();
//        listVoteByEvent.stream().forEach((l)-> {
//            if(!entreprAndVotes.stream().anyMatch(e-> e.getName().equals(l.getEntrepreneurshipVoted().getName())) && entreprAndVotes.size()<RANKING)
//                entreprAndVotes.add(voteRepository.entreprAndVotes(l.getEntrepreneurshipVoted().getName(), id));});
//        return entreprAndVotes.stream().sorted(Comparator.comparing(EntreprAndVotes::getVotes).reversed()).collect(Collectors.toList());
//    }

    @Override
    public List<EntreprAndVotes> listRankingEventsByGroups(Long id) {
        return voteRepository.entreprAndVotesByGroups(id);
    }
}

