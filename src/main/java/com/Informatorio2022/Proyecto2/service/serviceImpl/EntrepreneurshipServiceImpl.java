package com.Informatorio2022.Proyecto2.service.serviceImpl;

import com.Informatorio2022.Proyecto2.dtos.EntrepreneurshipPartDto;
import com.Informatorio2022.Proyecto2.dtos.mapper.EntrepreneurshipMapper;
import com.Informatorio2022.Proyecto2.exception.MessagePag;
import com.Informatorio2022.Proyecto2.exception.PaginationMessage;
import com.Informatorio2022.Proyecto2.model.Entrepreneurship;
import com.Informatorio2022.Proyecto2.repository.EntrepreneurshipRepository;
import com.Informatorio2022.Proyecto2.repository.TagRepository;
import com.Informatorio2022.Proyecto2.service.EntrepreneurshipService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
    @Override
    public MessagePag getAllPageable(int page, HttpServletRequest request) {
        Page<Entrepreneurship> pageList = entrepreneurshipRepository.findAll(PageRequest.of(page, SIZE_TEN));
        return paginationMessage.messageInfo(pageList, entrepreneurshipMapper.listEntityToDto(pageList.getContent()), request);
    }
    @Override
    public Entrepreneurship createEntrepreneurship(Entrepreneurship entrepreneurship) {
        return entrepreneurshipRepository.save(entrepreneurship);
    }
    @Override
    public List<Entrepreneurship> findByTagName(String tags){
//        return entrepreneurshipRepository.findByTagsNameIn(Arrays.asList(tags));
        return entrepreneurshipRepository.listOfEnreneurshipByTagName(Arrays.asList(tags));
    }
    @Override
    public EntrepreneurshipPartDto getById(Long id) {
        return entrepreneurshipMapper.entityToDto(entrepreneurshipRepository.findById(id).get());
    }
}
