package com.Informatorio2022.Proyecto2.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity @Table @Data
@AllArgsConstructor
@NoArgsConstructor
public class Vote {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;
    @ManyToOne
    @JoinColumn(name = "created_by_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User createdBy;
    @ManyToOne
    @JoinColumn(name = "entrepreneurship_voted_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Entrepreneurship entrepreneurshipVoted;
    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private LocalDateTime createDate;
}
