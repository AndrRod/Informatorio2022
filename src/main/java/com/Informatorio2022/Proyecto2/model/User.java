package com.Informatorio2022.Proyecto2.model;

import com.Informatorio2022.Proyecto2.enums.Role;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.annotations.CreationTimestamp;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
@SQLDelete(sql = "UPDATE users SET deleted = true WHERE id=?")
@Where(clause = "deleted = false")
public class User {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String fistName;
    private String lastName;
    @NotNull(message = "can't be empty or null")
    @Column(unique = true)
    @Email(regexp = "^[\\w!#$%&'+/=?`{|}~^-]+(?:\\.[\\w!#$%&'+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$", message="Email format error")
    private String email;
    @NotNull(message = "can't be empty or null")
    @Size(min = 8, message = "The password must be at least 8 characters")
    private String password;
    @NotNull(message = "can't be empty or null")
    private Role role = Role.USER;
    @CreationTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime creationDate;
    private Boolean deleted = Boolean.FALSE;
}
