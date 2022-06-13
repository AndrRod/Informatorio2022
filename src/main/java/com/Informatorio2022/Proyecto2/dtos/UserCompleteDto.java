package com.Informatorio2022.Proyecto2.dtos;
import com.Informatorio2022.Proyecto2.enums.Role;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter @Setter
public class UserCompleteDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Role role;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime creationDate;
}
