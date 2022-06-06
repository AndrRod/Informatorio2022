package com.Informatorio2022.Proyecto2.service;

import com.Informatorio2022.Proyecto2.model.Entrepreneurship;

import java.util.List;

public interface EntrepreneurshipService {
    List<Entrepreneurship> getAll();
    Entrepreneurship createEntrepreneurship(Entrepreneurship entrepreneurship);
}
