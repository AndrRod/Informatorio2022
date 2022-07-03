package com.Informatorio2022.Proyecto2.service;

import com.Informatorio2022.Proyecto2.dtos.EventDtoPart;
import com.Informatorio2022.Proyecto2.exception.MessagePag;
import com.Informatorio2022.Proyecto2.model.Event;

import javax.servlet.http.HttpServletRequest;

public interface EventService {
    EventDtoPart createEvent(EventDtoPart event);
    void deleteEventById(Long id);
    EventDtoPart modifyEventById(Long id, EventDtoPart event);
    Event findById(Long id);
    EventDtoPart findDtoById(Long id);
    MessagePag listEventsPagintation(int page, HttpServletRequest request);
}
