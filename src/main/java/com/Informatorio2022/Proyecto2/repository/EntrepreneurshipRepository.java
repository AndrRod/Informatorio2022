package com.Informatorio2022.Proyecto2.repository;
import com.Informatorio2022.Proyecto2.model.Entrepreneurship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EntrepreneurshipRepository extends JpaRepository<Entrepreneurship, Long> {
}
