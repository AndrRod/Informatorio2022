package com.Informatorio2022.Proyecto2.dtos;

import com.Informatorio2022.Proyecto2.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter @Setter
public class UserPartDto {
    private String fistName;
    private String lastName;
    private String email;
    private String password;
}
