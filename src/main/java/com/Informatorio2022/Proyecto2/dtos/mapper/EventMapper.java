package com.Informatorio2022.Proyecto2.dtos.mapper;

import com.Informatorio2022.Proyecto2.dtos.EventDtoPart;
import com.Informatorio2022.Proyecto2.model.Event;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class EventMapper {

    public Event createEventDto(EventDtoPart eventDtoPart){
        return new Event(null, eventDtoPart.getEventDetails(), LocalDateTime.now(), eventDtoPart.getEventState(), eventDtoPart.getAwards());
    }
    public EventDtoPart entityToDto(Event event){
        return new EventDtoPart(event.getId(), event.getEventDetails(), event.getCreationDate(), event.getEventState(), event.getAwards());
    }
    public List<Object> eventEntityToDtoPartList(List<Event> eventList){
        return eventList.stream().map(e-> entityToDto(e)).collect(Collectors.toList());
    }
}
