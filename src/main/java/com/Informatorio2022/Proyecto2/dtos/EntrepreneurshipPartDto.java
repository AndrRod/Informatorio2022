package com.Informatorio2022.Proyecto2.dtos;
import com.Informatorio2022.Proyecto2.model.Tags;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Collection;
@Getter @Setter @AllArgsConstructor
public class EntrepreneurshipPartDto {
    private String name;
    private String description;
    private BigDecimal collections;
    private Collection<TagDtoPart> tags;
}
