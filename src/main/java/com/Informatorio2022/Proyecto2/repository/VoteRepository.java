package com.Informatorio2022.Proyecto2.repository;
import com.Informatorio2022.Proyecto2.dtos.EntreprAndVotes;
import com.Informatorio2022.Proyecto2.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoteRepository extends JpaRepository <Vote, Long> {
    @Query("SELECT v FROM Vote v WHERE v.event.id = :id")
    List<Vote> listVoteByEventId(@Param("id") Long id);
    @Query("SELECT COUNT(v) FROM Vote v WHERE v.entrepreneurshipVoted.name = :name AND v.event.id = :id")
    Long countVoteByEntreprNameAndByEventId(@Param("name") String name, @Param("id") Long id);
    @Query("SELECT new com.Informatorio2022.Proyecto2.dtos.EntreprAndVotes(v.entrepreneurshipVoted.name, COUNT(v)) FROM Vote v WHERE v.entrepreneurshipVoted.name = :name AND v.event.id = :id")
    EntreprAndVotes entreprAndVotes(@Param("name") String name, @Param("id") Long id);
    @Query("SELECT new com.Informatorio2022.Proyecto2.dtos.EntreprAndVotes(v.entrepreneurshipVoted.name, COUNT(v)) FROM Vote v WHERE v.event.id= :id GROUP BY v.entrepreneurshipVoted.name")
    List<EntreprAndVotes> entreprAndVotesByGroups(@Param("id") Long id);

}
