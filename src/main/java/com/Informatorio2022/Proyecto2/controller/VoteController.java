package com.Informatorio2022.Proyecto2.controller;

import com.Informatorio2022.Proyecto2.dtos.EntreprAndVotes;
import com.Informatorio2022.Proyecto2.dtos.VoteDtoPart;
import com.Informatorio2022.Proyecto2.exception.MessageInfo;
import com.Informatorio2022.Proyecto2.exception.MessagePag;
import com.Informatorio2022.Proyecto2.model.Vote;
import com.Informatorio2022.Proyecto2.service.VoteService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@RestController
@RequestMapping("/votes")
public class VoteController {
    @Autowired
    private VoteService voteService;

    @GetMapping
    public ResponseEntity<MessagePag> getAllVotes(@RequestParam int page, HttpServletRequest request){
        return ResponseEntity.status(HttpStatus.OK).body(voteService.listVotes(page, request));
    }
    @GetMapping("/{id}")
    public ResponseEntity<VoteDtoPart> getById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(voteService.findVotesEventById(id));
    }
// controllers en los que se usaron Queries
    @GetMapping("/rakingByEvent/{id}")
    public ResponseEntity<?> rankingByEvent(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(voteService.rankingByEvents(id));
    }

    @GetMapping("/EntrepNameEventId/{id}")
    public ResponseEntity<EntreprAndVotes> findEntreprAndVotes(@PathVariable Long id, @RequestBody FormName formName){
        return ResponseEntity.status(HttpStatus.OK).body(voteService.findEntrepreAndVotes(formName.getName(), id));
    }

    @GetMapping("/rakingEventsByGroups/{id}")
    public ResponseEntity<?> rankingEventsGrops(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(voteService.listRankingEventsByGroups(id));
    }
//    @GetMapping("/rakingEvents/{id}")
//    public ResponseEntity<?> rankingEvents(@PathVariable Long id){
//        return ResponseEntity.status(HttpStatus.OK).body(voteService.listRankingEvents(id));
//    }

    //    @GetMapping("/voteByEntrepName/{id}")
//    public ResponseEntity<?> findByEntreName(@RequestBody FormName name, @PathVariable Long id){
//        return ResponseEntity.status(HttpStatus.OK).body(voteService.countVoteEntrepName(name.getName(), id));
//    }
}
@Data
class FormName{
    private String name;
}
