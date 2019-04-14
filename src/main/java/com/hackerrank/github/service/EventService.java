package com.hackerrank.github.service;

import com.hackerrank.github.model.Event;
import com.hackerrank.github.repository.ActorRepository;
import com.hackerrank.github.repository.EventRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class EventService {

    private final EventRepository repository;
    private final ActorRepository actorRepository;

    public EventService(EventRepository repository, ActorRepository actorRepository) {
        this.repository = repository;
        this.actorRepository = actorRepository;
    }

    public void deleteAll() {
        repository.deleteAll();
    }

    public List<Event> listAllOrderedById() {
        return repository.findAllByOrderByIdAsc();
    }

    public Event save(Event toSave) {
        if (repository.findById(toSave.getId()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return repository.save(toSave);
    }

    public List<Event> listByActorId(Long actorId) {
        return actorRepository.findById(actorId)
                .map(repository::findByActorOrderByIdAsc)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

}
