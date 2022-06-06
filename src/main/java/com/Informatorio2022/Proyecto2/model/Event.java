package com.Informatorio2022.Proyecto2.model;

import com.Informatorio2022.Proyecto2.enums.EventState;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data @Entity @Table
@AllArgsConstructor @NoArgsConstructor
public class Event {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String eventDetails;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime creationDate;
    private EventState eventState = EventState.OPEN;
    private BigDecimal awards;
}
