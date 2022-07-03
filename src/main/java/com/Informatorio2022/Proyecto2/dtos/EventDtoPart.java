package com.Informatorio2022.Proyecto2.dtos;

import com.Informatorio2022.Proyecto2.enums.EventState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter @Setter @AllArgsConstructor
public class EventDtoPart {
    private Long id;
    private String eventDetails;
    private LocalDateTime creationDate;
    private EventState eventState;
    private BigDecimal awards;
}
