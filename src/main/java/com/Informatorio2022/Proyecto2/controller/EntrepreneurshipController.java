package com.Informatorio2022.Proyecto2.controller;

import com.Informatorio2022.Proyecto2.dtos.EntrepreneurshipPartDto;
import com.Informatorio2022.Proyecto2.model.Entrepreneurship;
import com.Informatorio2022.Proyecto2.service.EntrepreneurshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/Entrep")
public class EntrepreneurshipController {
    @Autowired
    private EntrepreneurshipService entrepreneurshipService;
    @PostMapping
    public ResponseEntity<Entrepreneurship> createEntrep(@RequestBody Entrepreneurship entrepreneurship){
        return ResponseEntity.status(HttpStatus.CREATED).body(entrepreneurshipService.createEntrepreneurship(entrepreneurship));
    }
    @GetMapping
    public ResponseEntity<Page<Entrepreneurship>> getListEntrep(@PathVariable int page){
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(entrepreneurshipService.getAllPageable(page));
    }
    @GetMapping("/byTagName")
    public ResponseEntity<?> findByTagsName(@RequestBody FormName formName){
        return ResponseEntity.ok(entrepreneurshipService.findByTagName(formName.getName()));
    }
}
