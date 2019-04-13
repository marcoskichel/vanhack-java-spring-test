package com.hackerrank.github.service;

import com.hackerrank.github.dto.ActorEventResumeDto;
import com.hackerrank.github.model.Actor;
import com.hackerrank.github.model.Event;
import com.hackerrank.github.repository.ActorRepository;
import com.hackerrank.github.repository.EventRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ActorService {

    private final ActorRepository repository;
    private final EventRepository eventRepository;

    public ActorService(ActorRepository repository, EventRepository eventRepository) {
        this.repository = repository;
        this.eventRepository = eventRepository;
    }

    public void deleteAll() {
        repository.deleteAll();
    }

    public Actor save(Actor actor) {
        Actor persisted = repository.findById(actor.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (!persisted.getLogin().equals(actor.getLogin())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return repository.save(actor);
    }

    public List<Actor> listActorsSortedByNumberOfEvents() {
        List<ActorEventResumeDto> resumes = repository.listActorsSortedByNumberOfEvents();
        List<Actor> actors = new ArrayList<>();
        resumes.forEach(r -> actors.add(r.getActor()));
        return actors;
    }

    public List<Actor> listActorsSortedByMaxStreak() {
        Map<Actor, Integer> curStreaks = new HashMap<>();
        Map<Actor, Integer> maxStreaks = new HashMap<>();
        Map<Actor, LocalDateTime> actorLastEvaluatedEventDate = new HashMap<>();
        List<Event> events = eventRepository.findAllByOrderByCreatedAtAsc();
        for (Event e : events) {
            Actor actor = e.getActor();
            LocalDateTime eventDate = e.getCreatedAt().toLocalDateTime();
            if (actorLastEvaluatedEventDate.containsKey(actor)) {
                LocalDateTime lastEvaluatedActorEvent = actorLastEvaluatedEventDate.get(actor);
                if (lastEvaluatedActorEvent.toLocalDate().isEqual(eventDate.toLocalDate())) {
                    continue;
                }
                if (lastEvaluatedActorEvent.toLocalDate().plusDays(1).isEqual(eventDate.toLocalDate())) {
                    int curStreakValue = curStreaks.get(actor) + 1;
                    curStreaks.put(actor, curStreakValue);
                    if (curStreakValue > maxStreaks.get(actor)) {
                        maxStreaks.put(actor, curStreakValue);
                    }
                } else {
                    curStreaks.put(actor, 1);
                }
            } else {
                actorLastEvaluatedEventDate.put(actor, eventDate);
                curStreaks.put(actor, 1);
                maxStreaks.put(actor, 1);
            }
        }
        return actorLastEvaluatedEventDate.keySet().stream()
                .sorted((a1, a2) -> {
                    Integer a1MaxStreak = maxStreaks.get(a1);
                    Integer a2MaxStreak = maxStreaks.get(a2);
                    if (!a2MaxStreak.equals(a1MaxStreak)) {
                        return a2MaxStreak.compareTo(a1MaxStreak);
                    }
                    LocalDateTime a1LastEventDate = actorLastEvaluatedEventDate.get(a1);
                    LocalDateTime a2LastEventDate = actorLastEvaluatedEventDate.get(a2);
                    if (!a1LastEventDate.isEqual(a2LastEventDate)) {
                        return a2LastEventDate.compareTo(a1LastEventDate);
                    }
                    return a1.getLogin().compareTo(a2.getLogin());
                })
                .collect(Collectors.toList());
    }
}
