package com.Informatorio2022.Proyecto2.dtos;
import com.Informatorio2022.Proyecto2.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class UserLoginResponseDto {
    private String email;
    private Role role;
    private String inputToken;
    private String updateToken;
}
