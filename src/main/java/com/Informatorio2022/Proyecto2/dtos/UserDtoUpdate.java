package com.Informatorio2022.Proyecto2.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor
public class UserDtoUpdate {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String role;
}
