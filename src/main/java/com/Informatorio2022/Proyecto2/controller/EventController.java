package com.Informatorio2022.Proyecto2.controller;

import com.Informatorio2022.Proyecto2.dtos.EventDtoPart;
import com.Informatorio2022.Proyecto2.exception.MessagePag;
import com.Informatorio2022.Proyecto2.exception.MessageResum;
import com.Informatorio2022.Proyecto2.model.Event;
import com.Informatorio2022.Proyecto2.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/event")
public class EventController {
    @Autowired
    private EventService eventService;
    @Autowired
    private MessageResum messageResum;
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id){
        eventService.deleteEventById(id);
        return ResponseEntity.ok(messageResum.message("event.deleted", String.valueOf(id)));
    }
    @PostMapping
    public ResponseEntity<EventDtoPart> createEvent(@RequestBody EventDtoPart eventDtoPart){
        return ResponseEntity.ok(eventService.createEvent(eventDtoPart));
    }
    @PutMapping("/{id}")
    public ResponseEntity<EventDtoPart> updateEvent(@PathVariable Long id, @RequestBody EventDtoPart event){
        return ResponseEntity.ok(eventService.modifyEventById(id, event));
    }
    @GetMapping
    public ResponseEntity<MessagePag> evensListPagination(@RequestParam int page, HttpServletRequest request){
       return ResponseEntity.ok(eventService.listEventsPagintation(page, request));
    }
}
