package com.Informatorio2022.Proyecto2.service.serviceImpl;

import com.Informatorio2022.Proyecto2.config.PaginationMessage;
import com.Informatorio2022.Proyecto2.exception.BadRequestException;
import com.Informatorio2022.Proyecto2.exception.MessagePag;
import com.Informatorio2022.Proyecto2.exception.MessageResum;
import com.Informatorio2022.Proyecto2.dtos.UserCompleteDto;
import com.Informatorio2022.Proyecto2.dtos.UserPartDto;
import com.Informatorio2022.Proyecto2.dtos.mapper.UserMapper;
import com.Informatorio2022.Proyecto2.exception.NotFoundException;
import com.Informatorio2022.Proyecto2.model.User;
import com.Informatorio2022.Proyecto2.repository.UserRepository;
import com.Informatorio2022.Proyecto2.service.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;
import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@AllArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private static final int SIZE_TEN = 10;
    @Autowired
    private MessageResum messageResum;
    @Autowired
    private PaginationMessage paginationMessage;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;
    @Override
    public UserCompleteDto findUserById(Long id) {
        return userMapper.userEntityToCompleteDto(Optional.ofNullable(userRepository.findById(id).orElseThrow(() -> new NotFoundException(messageResum.message("user.id.not.found", String.valueOf(id))))).get());
    }
    @Override
    public UserPartDto createUser(UserPartDto userPartDto) {
        if(userRepository.existsByEmail(userPartDto.getEmail())) throw new BadRequestException(messageResum.message("email.already.exist", userPartDto.getEmail()));
        return userMapper.userEntityToPartDto(userRepository.save(userMapper.dtoPartToUserEntity(userPartDto)));
    }
    @Override
    public MessagePag findPageBy10Users(int page, WebRequest request) {
        Page<User> userPage = userRepository.findAll(PageRequest.of(page, SIZE_TEN));
        return paginationMessage.messageInfo(userPage, userMapper.listUserCompleteToListDto(userPage.getContent()), request);
    }
    @Override
    public UserPartDto updateUser(Long id, UserPartDto dto) {
        if(userRepository.findById(id).get() == userRepository.findByEmail(dto.getEmail())) dto.setEmail(null);
        if(userRepository.existsByEmail(dto.getEmail())) throw new BadRequestException(messageResum.message("email.already.exist", dto.getEmail()));
        Optional<User> user = Optional.ofNullable(userRepository.findById(id).orElseThrow(() -> new NotFoundException(messageResum.message("user.id.not.found", String.valueOf(id)))));
        user.stream().forEach((u)-> {
            if(dto.getFirstName() != null) u.setFirstName(dto.getFirstName());
            if(dto.getLastName() != null) u.setLastName(dto.getLastName());
            if(dto.getEmail() != null) u.setEmail(dto.getEmail());
            if(dto.getPassword() != null) u.setPassword(dto.getPassword());
        });
        return userMapper.userEntityToPartDto(userRepository.save(user.get()));
    }

    @Override
    public void updateUserRol(Long idUser,String roleName){

    }
}
