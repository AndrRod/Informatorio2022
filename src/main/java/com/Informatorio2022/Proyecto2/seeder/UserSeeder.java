package com.Informatorio2022.Proyecto2.seeder;

import com.Informatorio2022.Proyecto2.enums.Role;
import com.Informatorio2022.Proyecto2.model.User;
import com.Informatorio2022.Proyecto2.repository.UserRepository;
import com.Informatorio2022.Proyecto2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;

@Component
public class UserSeeder implements CommandLineRunner {

    @Autowired
    private UserRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public void run(String... args) throws Exception {
    if(repository.count() == 0){
        repository.saveAll(List.of(
                new User(null, "Victor","Salvatierra","victor@mail.com",passwordEncoder.encode("12345678"), Role.USER, null, false),
        new User(null, "Facundo","Villalba","facundo@mail.com",passwordEncoder.encode("12345678"), Role.USER, null, false),
        new User(null, "Andres","Rodirguez","andres@mail.com",passwordEncoder.encode("12345678"), Role.OWNER, null, false),
        new User(null, "Pablo","Ibañez","pablo@mail.com",passwordEncoder.encode("12345678"), Role.USER, null, false),
        new User(null, "Mickaela","Tarazaga","mickaela@mail.com",passwordEncoder.encode("12345678"), Role.USER, null, false),
        new User(null, "Agustin","Garcia","agustin@mail.com",passwordEncoder.encode("12345678"), Role.USER, null, false),
        new User(null, "Luz","Brito","luz@mail.com",passwordEncoder.encode("12345678"), Role.USER, null, false),
        new User(null, "Luciano","Lattante","luciano@mail.com",passwordEncoder.encode("12345678"), Role.USER, null, false),
        new User(null, "Gabriel","Rosa","gabriel@mail.com",passwordEncoder.encode("12345678"), Role.USER, null, false),
        new User(null, "Horacio","Cruz","horacio@mail.com",passwordEncoder.encode("12345678"), Role.USER, null, false),
        new User(null, "Diego","Padilla","diego@mail.com",passwordEncoder.encode("12345678"), Role.USER, null, false),
        new User(null, "Carlos","Rodriguez","carlos@mail.com",passwordEncoder.encode("12345678"), Role.USER, null, false),
        new User(null, "Juan","Morata","juan@mail.com",passwordEncoder.encode("12345678"), Role.USER, null, false),
        new User(null, "Valeria","Villalba","valeria@mail.com",passwordEncoder.encode("12345678"), Role.USER, null, false),
        new User(null, "Nicole","Lopez","nicole@mail.com",passwordEncoder.encode("12345678"), Role.USER, null, false),
        new User(null, "Norma","Gomez","norma@mail.com",passwordEncoder.encode("12345678"), Role.USER, null, false))

        );}
    };

}
