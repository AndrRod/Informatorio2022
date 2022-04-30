package com.Informatorio2022.Proyecto2.service.serviceImpl;

import com.Informatorio2022.Proyecto2.config.PaginationMessage;
import com.Informatorio2022.Proyecto2.exception.BadRequestException;
import com.Informatorio2022.Proyecto2.exception.MessagePag;
import com.Informatorio2022.Proyecto2.exception.MessageResum;
import com.Informatorio2022.Proyecto2.dtos.UserCompleteDto;
import com.Informatorio2022.Proyecto2.dtos.UserPartDto;
import com.Informatorio2022.Proyecto2.dtos.mapper.UserMapper;
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
import java.util.Collections;

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
    return userMapper.userEntityToCompleteDto(userRepository.findById(id).get());
    }
    @Override
    public UserPartDto createUser(UserPartDto userPartDto) {
        if(userRepository.existsByEmail(userPartDto.getEmail())) throw new BadRequestException(messageResum.message("email.already.exist", userPartDto.getEmail()));
        return userMapper.userEntityToPartDto(userRepository.save(userMapper.dtoPartToUserEntity(userPartDto)));
    }

    @Override
    public MessagePag findPageBy10Users(int page, WebRequest request) {
        Page<User> userPage = userRepository.findAll(PageRequest.of(page, SIZE_TEN));
        return paginationMessage.messageInfo(userPage, Collections.singletonList(userMapper.listUserCompleteToListDto(userPage.getContent())), request);
    }
}
