package com.Informatorio2022.Proyecto2.service.serviceImpl;

import com.Informatorio2022.Proyecto2.model.Entrepreneurship;
import com.Informatorio2022.Proyecto2.repository.EntrepreneurshipRepository;
import com.Informatorio2022.Proyecto2.service.EntrepreneurshipService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EntrepreneurshipServiceImpl implements EntrepreneurshipService {
    @Autowired
    private EntrepreneurshipRepository entrepreneurshipRepository;
    @Override
    public List<Entrepreneurship> getAll() {
        return entrepreneurshipRepository.findAll();
    }
    @Override
    public Entrepreneurship createEntrepreneurship(Entrepreneurship entrepreneurship) {
        return entrepreneurshipRepository.save(entrepreneurship);
    }
}
