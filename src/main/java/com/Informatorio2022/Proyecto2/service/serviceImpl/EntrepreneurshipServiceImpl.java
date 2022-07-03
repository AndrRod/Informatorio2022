package com.Informatorio2022.Proyecto2.service.serviceImpl;

import com.Informatorio2022.Proyecto2.dtos.EntrepreneurshipPartDto;
import com.Informatorio2022.Proyecto2.dtos.mapper.EntrepreneurshipMapper;
import com.Informatorio2022.Proyecto2.exception.*;
import com.Informatorio2022.Proyecto2.model.Entrepreneurship;
import com.Informatorio2022.Proyecto2.model.Tags;
import com.Informatorio2022.Proyecto2.model.User;
import com.Informatorio2022.Proyecto2.repository.EntrepreneurshipRepository;
import com.Informatorio2022.Proyecto2.repository.TagRepository;
import com.Informatorio2022.Proyecto2.service.EntrepreneurshipService;
import com.Informatorio2022.Proyecto2.service.UserService;
import io.vavr.collection.Array;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class EntrepreneurshipServiceImpl implements EntrepreneurshipService {
    private static final int SIZE_TEN = 10;
    @Autowired
    private EntrepreneurshipRepository entrepreneurshipRepository;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private EntrepreneurshipMapper entrepreneurshipMapper;
    @Autowired
    private PaginationMessage paginationMessage;
    @Autowired
    private UserService userService;
    @Autowired
    private MessageResum messageResum;

    @Override
    public MessagePag getAllPageable(int page, HttpServletRequest request) {
        Page<Entrepreneurship> pageList = entrepreneurshipRepository.findByUsersEmailIn(Arrays.asList(userService.emailUserLoged(request)), PageRequest.of(page, SIZE_TEN));
        return paginationMessage.messageInfo(pageList, entrepreneurshipMapper.listEntityToDto(pageList.getContent()), request);
    }
//    filtrar que solo el rol owner pueda crear
    @Override
    public Entrepreneurship createEntrepreneurship(EntrepreneurshipPartDto entrepreneurship, HttpServletRequest request) {
        Entrepreneurship entrepr = entrepreneurshipMapper.dtoToEntity(entrepreneurship);
        entrepr.getUsers().add(userService.findUserLogedByEmail(request));
        return entrepreneurshipRepository.save(entrepr);
    }
    @Override
    public void addTagToEntrepreneurship(Long id, String tagName,  HttpServletRequest request){
        Tags tag = Optional.ofNullable(tagRepository.findByName(tagName)).orElseThrow(()-> new NotFoundException(messageResum.message("tag.not.found", tagName)));
        Entrepreneurship entrepreneurship  = findById(id);
        entrepreneurship.getTags().add(tag);
        entrepreneurshipRepository.save(entrepreneurship);
    }
    @Override
    public List<Entrepreneurship> findByTagName(String tags, int page){
//        return entrepreneurshipRepository.findByTagsNameIn(Arrays.asList(tags));
        return entrepreneurshipRepository.listOfEnreneurshipByTagName(Arrays.asList(tags), PageRequest.of(page, SIZE_TEN));
    }
    @Override
    public EntrepreneurshipPartDto getById(Long id) {
        return entrepreneurshipMapper.entityToDto(entrepreneurshipRepository.findById(id).orElseThrow(()-> new NotFoundException(messageResum.message("entrep.not.found", id.toString()))));
    }
    @Override
    public Entrepreneurship findById(Long id){
        return entrepreneurshipRepository.findById(id).orElseThrow(()-> new NotFoundException(messageResum.message("entrep.not.found", id.toString())));
    }

    @Override
    public void deleteEntrepById(Long id, HttpServletRequest request) {
        isAuthorizate(findById(id).getUsers(), request);
        entrepreneurshipRepository.delete(findById(id));
    }
    public void isAuthorizate(Collection<User> users, HttpServletRequest request){
        if(!users.contains(userService.findUserLogedByEmail(request))) throw new NotFoundException(messageResum.message("not.authorizate", null));
    }
}
