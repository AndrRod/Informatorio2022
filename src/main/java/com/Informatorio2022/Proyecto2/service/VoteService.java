package com.Informatorio2022.Proyecto2.service;

import com.Informatorio2022.Proyecto2.dtos.EntreprAndVotes;
import com.Informatorio2022.Proyecto2.dtos.VoteDtoPart;
import com.Informatorio2022.Proyecto2.exception.MessagePag;
import com.Informatorio2022.Proyecto2.model.Vote;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface VoteService {
    MessagePag listVotes(int page, HttpServletRequest request);
    VoteDtoPart findVotesEventById(Long id);
    Map<String, Integer> rankingByEvents(Long id);
//    HashMap<String, Long> countVoteEntrepName(String nameEntrep, Long idEvent);
    EntreprAndVotes findEntrepreAndVotes(String name, Long id);
//    List<EntreprAndVotes> listRankingEvents(Long id);
    List<EntreprAndVotes> listRankingEventsByGroups(Long id);
    VoteDtoPart createVote(Long idEvent, String Entrepr, HttpServletRequest request);
}
