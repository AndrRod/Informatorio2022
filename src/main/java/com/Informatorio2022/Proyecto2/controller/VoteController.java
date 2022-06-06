package com.Informatorio2022.Proyecto2.controller;

import com.Informatorio2022.Proyecto2.model.Vote;
import com.Informatorio2022.Proyecto2.service.VoteService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<List<Vote>> getAllVotes(){
        return ResponseEntity.status(HttpStatus.OK).body(voteService.listVotes());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Vote> getById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.CREATED).body(voteService.findVotesEventById(id));
    }
// Queries
    @GetMapping("/rakingByEvent/{id}")
    ResponseEntity<?> rankingByEvent(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(voteService.rankingByEvents(id));
    }
    @GetMapping("/voteByEntrepName/{id}")
    ResponseEntity<?> findByEntreName(@RequestBody FormName name, @PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(voteService.countVoteEntrepName(name.getName(), id));
    }
}
@Data
class FormName{
    private String name;
}
