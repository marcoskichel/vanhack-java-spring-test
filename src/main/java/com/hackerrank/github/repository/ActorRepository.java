package com.hackerrank.github.repository;

import com.hackerrank.github.dto.ActorEventResumeDto;
import com.hackerrank.github.model.Actor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Long> {

    @Query("select new com.hackerrank.github.dto.ActorEventResumeDto(a, count(e), max(e.createdAt)) " +
            "from Event e " +
            "join e.actor a " +
            "group by a " +
            "order by count(e) desc, max(e.createdAt) desc, a.login asc")
    List<ActorEventResumeDto> listActorsSortedByNumberOfEvents();

    @Query("select a from Event e " +
            "join e.actor a " +
            "group by a " +
            "order by a.maxStreak desc, max(e.createdAt) desc, a.login asc")
    List<Actor> listActorsSortedByMaxStreak();

}
