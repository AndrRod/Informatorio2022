package com.Informatorio2022.Proyecto2.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Data @Entity
@AllArgsConstructor
@NoArgsConstructor
public class Tags {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotEmpty(message = "can't be empty or null") @Column(unique = true)
    private String name;
}
