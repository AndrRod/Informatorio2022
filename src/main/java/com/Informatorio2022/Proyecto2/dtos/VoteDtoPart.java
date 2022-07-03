package com.Informatorio2022.Proyecto2.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter @AllArgsConstructor
public class VoteDtoPart {
    private  Long Id;
    private String createdBy;
    private String entrepreneurshipVoted;
    private String event;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime createDate;
}
