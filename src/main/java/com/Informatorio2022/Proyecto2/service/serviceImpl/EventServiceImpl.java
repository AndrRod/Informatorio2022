package com.Informatorio2022.Proyecto2.service.serviceImpl;

import com.Informatorio2022.Proyecto2.dtos.EventDtoPart;
import com.Informatorio2022.Proyecto2.dtos.mapper.EventMapper;
import com.Informatorio2022.Proyecto2.exception.MessagePag;
import com.Informatorio2022.Proyecto2.exception.MessageResum;
import com.Informatorio2022.Proyecto2.exception.NotFoundException;
import com.Informatorio2022.Proyecto2.exception.PaginationMessage;
import com.Informatorio2022.Proyecto2.model.Event;
import com.Informatorio2022.Proyecto2.repository.EventRepository;
import com.Informatorio2022.Proyecto2.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
@Service
public class EventServiceImpl implements EventService {
    private static final int SIZE_TEN = 10;
    @Autowired
    private MessageResum messageResum;
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private PaginationMessage paginationMessage;
    @Autowired
    private EventMapper eventMapper;
    @Override
    public EventDtoPart createEvent(EventDtoPart event) {
        return eventMapper.entityToDto(eventRepository.save(eventMapper.createEventDto(event)));
    }
    @Override
    public void deleteEventById(Long id) {
        eventRepository.delete(findById(id));
    }
    @Override
    public EventDtoPart modifyEventById(Long id, EventDtoPart event) {
        Event ev = findById(id);
        Optional.of(ev).stream().forEach(
                (e)->{
                    if(event.getEventDetails()!= null) e.setEventDetails(event.getEventDetails());
                    if(event.getEventState()!= null) e.setEventState(event.getEventState());
                    if(event.getAwards()!= null) e.setAwards(event.getAwards());
                }
        );
        return eventMapper.entityToDto(eventRepository.save(ev));
    }

    @Override
    public Event findById(Long id) {
        return eventRepository.findById(id).orElseThrow(()-> new NotFoundException(messageResum.message("event.not.found", String.valueOf(id))));
    }

    @Override
    public MessagePag listEventsPagintation(int page, HttpServletRequest request) {
        Page<Event> events = eventRepository.findAll(PageRequest.of(page, SIZE_TEN));
        return paginationMessage.messageInfo(events,  eventMapper.eventEntityToDtoPartList(events.getContent()), request);
    }
}
