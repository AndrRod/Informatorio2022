package com.Informatorio2022.Proyecto2.dtos.mapper;

import com.Informatorio2022.Proyecto2.dtos.UserCompleteDto;
import com.Informatorio2022.Proyecto2.dtos.UserPageDto;
import com.Informatorio2022.Proyecto2.dtos.UserPartDto;
import com.Informatorio2022.Proyecto2.enums.Role;
import com.Informatorio2022.Proyecto2.model.User;
import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {
    public UserCompleteDto userEntityToCompleteDto(User user){return new UserCompleteDto(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getRole(), user.getCreationDate());}
    public List<Object> listUsersToListDtoComplete(List<User> userList){return userList.stream().map(user -> userEntityToCompleteDto(user)).collect(Collectors.toList());}
    public List<Object> listUsersToListDtoPart(List<User> userList){return userList.stream().map(u-> userEntityToPartDto(u)).collect(Collectors.toList());}
    public User dtoUserPartCreateToEntity(UserPartDto user){return new User(null, user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword(), Role.USER, LocalDateTime.now(), false);};
    public UserPartDto userEntityToPartDto(User user){return new UserPartDto(user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword());}
    public UserPageDto userEntittyToPageDto(User user){return new UserPageDto(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getRole(), user.getCreationDate());}
    public List<Object> listUserEntityToPageDto(List<User> usersList){return usersList.stream().map(u -> userEntittyToPageDto(u)).collect(Collectors.toList());}
}
