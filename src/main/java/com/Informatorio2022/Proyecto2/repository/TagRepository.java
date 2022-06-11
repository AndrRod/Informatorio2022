package com.Informatorio2022.Proyecto2.repository;

import com.Informatorio2022.Proyecto2.model.Tags;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface TagRepository extends JpaRepository<Tags, Long> {
    Tags findByName(String name);
}
