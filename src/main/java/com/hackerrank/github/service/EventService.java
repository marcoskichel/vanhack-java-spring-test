package com.hackerrank.github.service;

import com.hackerrank.github.model.Actor;
import com.hackerrank.github.model.Event;
import com.hackerrank.github.repository.ActorRepository;
import com.hackerrank.github.repository.EventRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class EventService {

    private final EventRepository repository;
    private final ActorRepository actorRepository;

    public EventService(EventRepository repository, ActorRepository actorRepository) {
        this.repository = repository;
        this.actorRepository = actorRepository;
    }

    public List<Event> listAllOrderedById() {
        return repository.findAllByOrderByIdAsc();
    }

    public Event save(Event toSave) {
        if (repository.findById(toSave.getId()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        actorRepository.findById(toSave.getActor().getId())
                .ifPresent(actor -> {
                    actor = updateActorStreakBeforeSaving(actor, toSave.getCreatedAt().toLocalDateTime());
                    toSave.setActor(actor);
                });
        return repository.save(toSave);
    }

    private Actor updateActorStreakBeforeSaving(Actor actor, LocalDateTime date) {
        boolean eventsActualDate = countEventsOfActorAtDate(actor, date) > 0;
        boolean eventsDayBefore = countEventsOfActorAtDate(actor, date.minusDays(1)) > 0;
        if (!eventsDayBefore) {
            actor.setCurrentStreak(1);
        } else if (!eventsActualDate) {
            actor.setCurrentStreak(actor.getCurrentStreak() + 1);
        }
        return actor;
    }

    private Long countEventsOfActorAtDate(Actor actor, LocalDateTime date) {
        Timestamp yesterdayStart = Timestamp.valueOf(date.withHour(0).withMinute(0).withSecond(0));
        Timestamp yesterdayEnd = Timestamp.valueOf(date.withHour(23).withMinute(59).withSecond(59));
        return repository.countByActorIdAndCreatedAtBetween(actor.getId(), yesterdayStart, yesterdayEnd);
    }

    public List<Event> listByActorId(Long actorId) {
        if (!actorRepository.findById(actorId).isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return repository.findByActorIdOrderByIdAsc(actorId);
    }

}
