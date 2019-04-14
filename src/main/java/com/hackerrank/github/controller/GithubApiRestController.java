package com.hackerrank.github.controller;

import com.hackerrank.github.model.Actor;
import com.hackerrank.github.model.Event;
import com.hackerrank.github.service.ActorService;
import com.hackerrank.github.service.EventService;
import com.hackerrank.github.service.RepoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
public class GithubApiRestController {

    private final EventService eventService;
    private final ActorService actorService;
    private final RepoService repoService;

    public GithubApiRestController(EventService eventService, ActorService actorService, RepoService repoService) {
        this.eventService = eventService;
        this.actorService = actorService;
        this.repoService = repoService;
    }

    /**
     * Adding new events: The eventService should be able to add a new event by the POST request at /events.
     * The event JSON is sent in the request body. If an event with the same id already exists then the HTTP
     * response code should be 400, otherwise, the response code should be 201.
     */
    @PostMapping("/events")
    public ResponseEntity<?> insertEvent(@RequestBody Event event, UriComponentsBuilder uriBuilder) {
        eventService.save(event);
        UriComponents uriComponents =
                uriBuilder.path("/events").build();
        return ResponseEntity.created(uriComponents.toUri()).build();
    }

    /**
     * Returning all the events: The eventService should be able to return the JSON array of all the events by the GET
     * request at /events. The HTTP response code should be 200. The JSON array should be sorted in ascending order
     * by event ID.
     */
    @GetMapping
    public Iterable<Event> listEvents() {
        return eventService.listAllOrderedById();
    }

    /**
     * Returning the event records filtered by the actor ID: The eventService should be able to return the JSON array of all
     * the events which are performed by the actor ID by the GET request at /events/actors/{actorID}.
     * If the requested actor does not exist then HTTP response code should be 404, otherwise, the response
     * code should be 200. The JSON array should be sorted in ascending order by event ID.
     */
    @GetMapping("/events/actors/{actorId}")
    public List<Event> eventsByActor(@PathVariable("actorId") Long actorId) {
        return eventService.listByActorId(actorId);
    }

    /**
     * Updating the avatar URL of the actor: The actorService should be able to update the avatar URL of the actor by the
     * PUT request at /actors. The actor JSON is sent in the request body. If the actor with the id does not exist
     * then the response code should be 404, or if there are other fields being updated for the actor then
     * the HTTP response code should be 400, otherwise, the response code should be 200.
     */
    @PutMapping("/actors")
    public Actor updateActor(@RequestBody Actor actor) {
        return actorService.save(actor);
    }

    /**
     * Returning the actor records ordered by the total number of events: The actorService should be able to return
     * the JSON array of all the actors sorted by the total number of associated events with each actor in descending
     * order by the GET request at /actors. If there are more than one actors with the same number of events,
     * then order them by the timestamp of the latest event in the descending order. If more than one actors have
     * the same timestamp for the latest event, then order them by the alphabetical order of login. The HTTP
     * response code should be 200.
     */
    @GetMapping("/actors")
    public List<Actor> listActorsSortedByNumberOfEvents() {
        return actorService.listActorsSortedByNumberOfEvents();
    }

    /**
     * Returning the actor records ordered by the maximum streak: The actorService should be able to return the JSON array
     * of all the actors sorted by the maximum streak (i.e., the total number of consecutive days actor has pushed an
     * event to the system) in descending order by the GET request at /actors/streak. If there are more than one actors
     * with the same maximum streak, then order them by the timestamp of the latest event in the descending order.
     * If more than one actors have the same timestamp for the latest event, then order them by the alphabetical
     * order of login. The HTTP response code should be 200.
     */
    @GetMapping("/actors/streak")
    public List<Actor> listActorsByStreak() {
        return actorService.listActorsSortedByMaxStreak();
    }

    @DeleteMapping("/erase")
    public void erase() {
        this.eventService.deleteAll();
        this.actorService.deleteAll();
        this.repoService.deleteAll();
    }

}
