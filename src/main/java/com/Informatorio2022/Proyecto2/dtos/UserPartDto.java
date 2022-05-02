package com.Informatorio2022.Proyecto2.dtos;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter @Setter
public class UserPartDto {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
