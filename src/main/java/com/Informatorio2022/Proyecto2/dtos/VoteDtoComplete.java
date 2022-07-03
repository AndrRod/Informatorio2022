package com.Informatorio2022.Proyecto2.dtos;

import com.Informatorio2022.Proyecto2.model.Entrepreneurship;
import com.Informatorio2022.Proyecto2.model.Event;
import com.Informatorio2022.Proyecto2.model.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Setter @Getter @AllArgsConstructor
public class VoteDtoComplete {
    private Long Id;
    private User createdBy;
    private Entrepreneurship entrepreneurshipVoted;
    private Event event;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime createDate;
}
