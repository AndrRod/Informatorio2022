package com.Informatorio2022.Proyecto2.service;

import com.Informatorio2022.Proyecto2.dtos.EntreprAndVotes;
import com.Informatorio2022.Proyecto2.model.Vote;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface VoteService {
    List<Vote> listVotes();
    Vote findVotesEventById(Long id);
    Map<String, Integer> rankingByEvents(Long id);
    HashMap<String, Long> countVoteEntrepName(String nameEntrep, Long idEvent);
    EntreprAndVotes findEntrepreAndVotes(String name, Long id);
    List<EntreprAndVotes> listRankingEvents(Long id);
}
