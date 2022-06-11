package com.Informatorio2022.Proyecto2.service;
import com.Informatorio2022.Proyecto2.dtos.EntrepreneurshipPartDto;
import com.Informatorio2022.Proyecto2.exception.MessagePag;
import com.Informatorio2022.Proyecto2.model.Entrepreneurship;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface EntrepreneurshipService {
    MessagePag getAllPageable(int page, HttpServletRequest request);
    Entrepreneurship createEntrepreneurship(Entrepreneurship entrepreneurship);
    List<Entrepreneurship> findByTagName(String tags);
    EntrepreneurshipPartDto getById(Long id);
}
