package com.hackerrank.github.controller;

import com.hackerrank.github.model.Actor;
import com.hackerrank.github.service.ActorService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ActorsRestController {

    private final ActorService service;

    public ActorsRestController(ActorService service) {
        this.service = service;
    }

    /**
     * Updating the avatar URL of the actor: The service should be able to update the avatar URL of the actor by the
     * PUT request at /actors. The actor JSON is sent in the request body. If the actor with the id does not exist
     * then the response code should be 404, or if there are other fields being updated for the actor then
     * the HTTP response code should be 400, otherwise, the response code should be 200.
     */
    @PutMapping("/actors")
    public Actor updateActor(@RequestBody Actor actor) {
        return service.save(actor);
    }

    /**
     * Returning the actor records ordered by the total number of events: The service should be able to return
     * the JSON array of all the actors sorted by the total number of associated events with each actor in descending
     * order by the GET request at /actors. If there are more than one actors with the same number of events,
     * then order them by the timestamp of the latest event in the descending order. If more than one actors have
     * the same timestamp for the latest event, then order them by the alphabetical order of login. The HTTP
     * response code should be 200.
     */
    @GetMapping("/actors")
    public List<Actor> listActorsSortedByNumberOfEvents() {
        return service.listActorsSortedByNumberOfEvents();
    }

    /**
     * Returning the actor records ordered by the maximum streak: The service should be able to return the JSON array
     * of all the actors sorted by the maximum streak (i.e., the total number of consecutive days actor has pushed an
     * event to the system) in descending order by the GET request at /actors/streak. If there are more than one actors
     * with the same maximum streak, then order them by the timestamp of the latest event in the descending order.
     * If more than one actors have the same timestamp for the latest event, then order them by the alphabetical
     * order of login. The HTTP response code should be 200.
     */
    @GetMapping("/actors/streak")
    public List<Actor> listActorsByStreak() {
        return service.listActorsSortedByMaxStreak();
    }

}
