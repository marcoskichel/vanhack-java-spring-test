package com.hackerrank.github.service;

import com.hackerrank.github.dto.ActorEventResumeDto;
import com.hackerrank.github.model.Actor;
import com.hackerrank.github.repository.ActorRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class ActorService {

    private final ActorRepository repository;

    public ActorService(ActorRepository repository) {
        this.repository = repository;
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
        return repository.listActorsSortedByMaxStreak();
    }
}
