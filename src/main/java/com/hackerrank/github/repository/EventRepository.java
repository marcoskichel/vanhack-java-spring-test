package com.hackerrank.github.repository;

import com.hackerrank.github.model.Actor;
import com.hackerrank.github.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findAllByOrderByIdAsc();
    List<Event> findByActorOrderByIdAsc(Actor actor);
    Long countByActorIdAndCreatedAtBetween(Long actorId, Timestamp start, Timestamp end);
    List<Event> findAllByOrderByCreatedAtAsc();

}
