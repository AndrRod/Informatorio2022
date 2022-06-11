package com.Informatorio2022.Proyecto2.repository;
import com.Informatorio2022.Proyecto2.dtos.EntrepreneurshipPartDto;
import com.Informatorio2022.Proyecto2.model.Entrepreneurship;
import com.Informatorio2022.Proyecto2.model.Tags;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface EntrepreneurshipRepository extends JpaRepository<Entrepreneurship, Long> {
    @Query("SELECT e FROM Entrepreneurship e JOIN FETCH e.tags t WHERE t.name IN (:tag)")
    List<Entrepreneurship> listOfEnreneurshipByTagName(@Param("tag") Collection<String> tag);
// jpa ayuda a hacer la query anterior de la siguiente manera
//    List<Entrepreneurship> findByTagsNameIn(Collection<String> names);
}
