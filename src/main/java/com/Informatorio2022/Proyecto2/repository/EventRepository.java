package com.Informatorio2022.Proyecto2.repository;
import com.Informatorio2022.Proyecto2.model.Event;
import com.Informatorio2022.Proyecto2.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
}
