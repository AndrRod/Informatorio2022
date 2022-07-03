package com.Informatorio2022.Proyecto2.controller;

import com.Informatorio2022.Proyecto2.dtos.EntrepreneurshipPartDto;
import com.Informatorio2022.Proyecto2.exception.MessageInfo;
import com.Informatorio2022.Proyecto2.exception.MessagePag;
import com.Informatorio2022.Proyecto2.exception.MessageResum;
import com.Informatorio2022.Proyecto2.model.Entrepreneurship;
import com.Informatorio2022.Proyecto2.service.EntrepreneurshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/entrep")
public class EntrepreneurshipController {
    @Autowired
    private EntrepreneurshipService entrepreneurshipService;
    @Autowired
    private MessageResum messageResum;
    @PostMapping
    public ResponseEntity<Entrepreneurship> createEntrep(@RequestBody EntrepreneurshipPartDto entrepreneurship, HttpServletRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(entrepreneurshipService.createEntrepreneurship(entrepreneurship, request));
    }
    @GetMapping
    public ResponseEntity<MessagePag> getListEntrep(@RequestParam int page, HttpServletRequest request){
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(entrepreneurshipService.getAllPageable(page, request));
    }
    @GetMapping("/{id}")
    public ResponseEntity<EntrepreneurshipPartDto> geBytId(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(entrepreneurshipService.getById(id));
    }
    @GetMapping("/bytagName")
    public ResponseEntity<List<Entrepreneurship>> findByTagsName(@RequestBody FormName formName, @RequestParam int page){
        return ResponseEntity.ok(entrepreneurshipService.findByTagName(formName.getName(), page));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<MessageInfo> deleteById(@PathVariable Long id, HttpServletRequest request){
        entrepreneurshipService.deleteEntrepById(id, request);
        return ResponseEntity.ok().body(new MessageInfo(messageResum.message("entrep.delete.id", id.toString()), HttpStatus.OK.value(), request.getRequestURI()));

    }
    @PutMapping("/{id}")
    public ResponseEntity<EntrepreneurshipPartDto> udpateEntityByDto(@PathVariable Long id, @RequestBody EntrepreneurshipPartDto dto){
        return ResponseEntity.ok(entrepreneurshipService.updateEntreById(id, dto));
    }
}
