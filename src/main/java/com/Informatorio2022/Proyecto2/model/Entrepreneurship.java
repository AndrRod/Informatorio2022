package com.Informatorio2022.Proyecto2.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;

@Entity @Table @Data
@AllArgsConstructor @NoArgsConstructor
public class Entrepreneurship {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true) @NotEmpty(message = "can't be null or empty")
    private String name;
    private String description;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime creationDate;
    private BigDecimal collections;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Collection<Event> eventSubscribed = new HashSet<>();
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Collection<Tags> tags = new HashSet<>();
    @ManyToMany(fetch = FetchType.LAZY)
    private Collection<User> users = new HashSet<>();
}
