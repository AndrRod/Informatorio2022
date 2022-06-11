package com.Informatorio2022.Proyecto2.dtos;
import com.Informatorio2022.Proyecto2.model.Tags;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.util.Collection;
@Getter @Setter @AllArgsConstructor
public class EntrepreneurshipPartDto {
    private String name;
    private Collection<Tags> tags;
}
