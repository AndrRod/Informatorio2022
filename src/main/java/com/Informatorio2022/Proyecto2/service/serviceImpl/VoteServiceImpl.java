package com.Informatorio2022.Proyecto2.service.serviceImpl;

import com.Informatorio2022.Proyecto2.dtos.EntreprAndVotes;
import com.Informatorio2022.Proyecto2.dtos.VoteDtoPart;
import com.Informatorio2022.Proyecto2.dtos.mapper.VoteMapper;
import com.Informatorio2022.Proyecto2.exception.MessagePag;
import com.Informatorio2022.Proyecto2.exception.MessageResum;
import com.Informatorio2022.Proyecto2.exception.NotFoundException;
import com.Informatorio2022.Proyecto2.exception.PaginationMessage;
import com.Informatorio2022.Proyecto2.model.User;
import com.Informatorio2022.Proyecto2.model.Vote;
import com.Informatorio2022.Proyecto2.repository.EntrepreneurshipRepository;
import com.Informatorio2022.Proyecto2.repository.VoteRepository;
import com.Informatorio2022.Proyecto2.service.EntrepreneurshipService;
import com.Informatorio2022.Proyecto2.service.EventService;
import com.Informatorio2022.Proyecto2.service.UserService;
import com.Informatorio2022.Proyecto2.service.VoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VoteServiceImpl implements VoteService {
    private static final int RANKING = 10;
    private static final int SIZE_TEN = 10;
    @Autowired
    private VoteRepository voteRepository;
    @Autowired
    private EntrepreneurshipService entrepreneurshipService;
    @Autowired
    private UserService userService;
    @Autowired
    private EventService eventService;
    @Autowired
    private MessageResum messageResum;
    @Autowired
    private VoteMapper voteMapper;
    @Autowired
    private PaginationMessage paginationMessage;
    @Override
    public MessagePag listVotes(int page, HttpServletRequest request) {
        Page<Vote> votes = voteRepository.findAll(PageRequest.of(page, SIZE_TEN));
        return paginationMessage.messageInfo(votes, voteMapper.ListDtoFromListEntity(votes.getContent()), request);
    }
    @Override
    public VoteDtoPart findVotesEventById(Long id) {
        return voteMapper.getDtoFromEntity(voteRepository.findById(id).orElseThrow(()-> new NotFoundException(messageResum.message("not.found.vote.by.event", String.valueOf(id)))));
    }

//    Esta Query resume el metodo comentado abajo (ranking de emprendimientos por evento)
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

//    Esta Query resume el metodo comentado abajo (votos de de un emprendimiento por evento)
//    @Query("SELECT new com.Informatorio2022.Proyecto2.dtos.EntreprAndVotes(v.entrepreneurshipVoted.name, COUNT(v)) FROM Vote v WHERE v.entrepreneurshipVoted.name = :name AND v.event.id = :id")
//    EntreprAndVotes entreprAndVotes(@Param("name") String name, @Param("id") Long id);
//    @Override
//    public HashMap<String, Long> countVoteEntrepName(String name, Long id) {
//        return new HashMap<>(){{put("votes", voteRepository.countVoteByEntreprNameAndByEventId(name, id));}};
//    }
    @Override
    public EntreprAndVotes findEntrepreAndVotes(String name, Long id) {
        return voteRepository.entreprAndVotes(name, id);
    }

//    Esta Query resume el metodo comentado abajo (ranking de emprendimientos por evento)
//    @Query("SELECT new com.Informatorio2022.Proyecto2.dtos.EntreprAndVotes(v.entrepreneurshipVoted.name, COUNT(v)) FROM Vote v WHERE v.event.id= :id GROUP BY v.entrepreneurshipVoted.name")
//    List<EntreprAndVotes> entreprAndVotesByGroups(@Param("id") Long id);

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
        return voteRepository.entreprAndVotesByGroups(id).stream().sorted(Comparator.comparing(EntreprAndVotes::getVotes).reversed()).limit(10).collect(Collectors.toList());
    }

    @Override
    public VoteDtoPart createVote(Long idEvent, String entrep, HttpServletRequest request) {
        Vote vote = new Vote();
        Optional.of(vote).stream().forEach(
                (v)-> {
                    v.setCreatedBy(userService.findUserLogedByEmail(request));
                    v.setEvent(eventService.findById(idEvent));
                    v.setEntrepreneurshipVoted(entrepreneurshipService.findByName(entrep));
                }
        );
        return voteMapper.getDtoFromEntity(voteRepository.save(vote));   }


}

