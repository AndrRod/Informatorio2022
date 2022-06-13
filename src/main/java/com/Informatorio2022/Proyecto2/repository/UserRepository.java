package com.Informatorio2022.Proyecto2.repository;
import com.Informatorio2022.Proyecto2.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    boolean existsByEmail(String email);
    @Query(value = "SELECT u FROM User u WHERE u.firstName LIKE %:name% OR u.lastName LIKE %:name%")
    Page<User> findByNameAprox(@Param("name") String firstOrLastName, Pageable pageable);
    @Query(value = "SELECT u FROM User u WHERE u.creationDate >= :startDate AND u.creationDate <= :finishDate")
    Page<User> findByCreationDateAprox(@Param("startDate") LocalDateTime startDate, @Param("finishDate") LocalDateTime finishDate, Pageable pageable);
}
