package com.Informatorio2022.Proyecto2.service.serviceImpl;

import com.Informatorio2022.Proyecto2.model.Entrepreneurship;
import com.Informatorio2022.Proyecto2.model.Tags;
import com.Informatorio2022.Proyecto2.repository.EntrepreneurshipRepository;
import com.Informatorio2022.Proyecto2.repository.TagRepository;
import com.Informatorio2022.Proyecto2.service.EntrepreneurshipService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EntrepreneurshipServiceImpl implements EntrepreneurshipService {
    private static final int SIZE_TEN = 10;
    @Autowired
    private EntrepreneurshipRepository entrepreneurshipRepository;
    @Autowired
    private TagRepository tagRepository;
    @Override
    public Page<Entrepreneurship> getAllPageable(int page) {
        return entrepreneurshipRepository.findAll(PageRequest.of(page, SIZE_TEN));
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
}
