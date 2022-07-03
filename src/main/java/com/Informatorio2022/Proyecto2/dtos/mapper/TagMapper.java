package com.Informatorio2022.Proyecto2.dtos.mapper;

import com.Informatorio2022.Proyecto2.dtos.TagDtoPart;
import com.Informatorio2022.Proyecto2.model.Tags;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TagMapper {
    public TagDtoPart entityToDto(Tags tags){ return new TagDtoPart(tags.getId(), tags.getName());}
    public Tags dtoToEntity(TagDtoPart tagDtoPart){return new Tags(tagDtoPart.getId(), tagDtoPart.getName());}
    public Collection<TagDtoPart> listEntityToDto(Collection<Tags> tags){return tags.stream().map(t-> entityToDto(t)).collect(Collectors.toList());}

    public Collection<Tags> listDtoToEntity(Collection<TagDtoPart> tags) {
        return tags.stream().map(t-> dtoToEntity(t)).collect(Collectors.toList());
    }
}
