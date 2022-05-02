package com.Informatorio2022.Proyecto2.dtos.mapper;

import com.Informatorio2022.Proyecto2.dtos.UserCompleteDto;
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
    public User dtoCompleteToUserEntity(UserCompleteDto user){return new User(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword(), user.getRole(), user.getCreationDate(), false);};
    public UserCompleteDto userEntityToCompleteDto(User user){return new UserCompleteDto(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword(), user.getRole(), user.getCreationDate());}
    public List<Object> listUserCompleteToListDto(List<User> userList){return userList.stream().map(user -> userEntityToCompleteDto(user)).collect(Collectors.toList());}

    public User dtoUserPartCreateToEntity(UserPartDto user){return new User(null, user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword(), Role.USER, LocalDateTime.now(), false);};
    public UserPartDto userEntityToPartDto(User user){return new UserPartDto(user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword());}
    public List<UserPartDto> listUserToListPartDto(List<User> userList){return userList.stream().map(user -> userEntityToPartDto(user)).collect(Collectors.toList());}
}
