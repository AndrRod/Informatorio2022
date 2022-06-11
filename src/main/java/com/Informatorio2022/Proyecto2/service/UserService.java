package com.Informatorio2022.Proyecto2.service;

import com.Informatorio2022.Proyecto2.exception.RefreshTokenForm;
import com.Informatorio2022.Proyecto2.dtos.UserCompleteDto;
import com.Informatorio2022.Proyecto2.dtos.UserLoginResponseDto;
import com.Informatorio2022.Proyecto2.dtos.UserPartDto;
import com.Informatorio2022.Proyecto2.exception.MessagePag;
import com.Informatorio2022.Proyecto2.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public interface UserService {
    UserCompleteDto findUserById(Long id);
    UserPartDto createUser(UserPartDto userDto);
    MessagePag findPageBy10Users(int page, HttpServletRequest request);
    UserPartDto updateUser(Long id, UserPartDto userPartDto);
    void updateUserRol(Long idUser, String roleName);
    Authentication authenticationFilter(String email, String password) throws AuthenticationException;
    UserLoginResponseDto userLogin(String email, String password, HttpServletRequest request);
    User findUserByEmail(String email);
    void refreshToken(RefreshTokenForm form, HttpServletRequest request, HttpServletResponse response) throws IOException;
    void deleteUserById(Long id);
//    QUERIES IN REPOSITORY
    List<Object> findListByFirstName(String name, Integer page);
    List<Object> findByCreationDate(LocalDate startDate, LocalDate finishDate, Integer page);
}

