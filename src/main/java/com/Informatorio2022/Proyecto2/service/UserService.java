package com.Informatorio2022.Proyecto2.service;

import com.Informatorio2022.Proyecto2.dtos.UserCompleteDto;
import com.Informatorio2022.Proyecto2.dtos.UserPartDto;
import com.Informatorio2022.Proyecto2.exception.MessagePag;
import org.springframework.web.context.request.WebRequest;

public interface UserService {
    UserCompleteDto findUserById(Long id);
    UserPartDto createUser(UserPartDto userDto);
    MessagePag findPageBy10Users(int page, WebRequest request);
}
