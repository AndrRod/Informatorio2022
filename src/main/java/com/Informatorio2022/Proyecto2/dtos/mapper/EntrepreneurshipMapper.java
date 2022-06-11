package com.Informatorio2022.Proyecto2.dtos.mapper;

import com.Informatorio2022.Proyecto2.dtos.EntrepreneurshipPartDto;
import com.Informatorio2022.Proyecto2.model.Entrepreneurship;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EntrepreneurshipMapper {
    @Autowired
    private TagMapper tagMapper;
    public EntrepreneurshipPartDto entityToDto(Entrepreneurship entrepreneurship){
        return new EntrepreneurshipPartDto(entrepreneurship.getName(), entrepreneurship.getDescription(), entrepreneurship.getCollections(), tagMapper.listEntityToDto(entrepreneurship.getTags()));
    }
    public List<Object> listEntityToDto(List<Entrepreneurship> entrepreneurshipList) {return entrepreneurshipList.stream().map(e-> entityToDto(e)).collect(Collectors.toList());}
}
