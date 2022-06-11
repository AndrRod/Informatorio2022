package com.Informatorio2022.Proyecto2.service;
import com.Informatorio2022.Proyecto2.model.Entrepreneurship;
import org.springframework.data.domain.Page;

import java.util.List;

public interface EntrepreneurshipService {
    Page<Entrepreneurship> getAllPageable(int page);
    Entrepreneurship createEntrepreneurship(Entrepreneurship entrepreneurship);
    List<Entrepreneurship> findByTagName(String tags);
}
