package com.Informatorio2022.Proyecto2.dtos.mapper;

import com.Informatorio2022.Proyecto2.dtos.TagDtoPart;
import com.Informatorio2022.Proyecto2.model.Tags;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TagMapper {
    TagDtoPart entityToDto(Tags tags){ return new TagDtoPart(tags.getName());}
    Collection<TagDtoPart> listEntityToDto(Collection<Tags> tags){return tags.stream().map(t-> entityToDto(t)).collect(Collectors.toList());}
}
