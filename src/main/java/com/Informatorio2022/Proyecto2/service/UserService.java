package com.Informatorio2022.Proyecto2.service;

import com.Informatorio2022.Proyecto2.dtos.UserCompleteDto;
import com.Informatorio2022.Proyecto2.dtos.UserPartDto;
import com.Informatorio2022.Proyecto2.model.User;
import org.springframework.data.domain.Page;

public interface UserService {
    UserCompleteDto findUserById(Long id);
    UserPartDto createUser(UserPartDto userDto);
    Page findPageBy10Users(int page);
}
