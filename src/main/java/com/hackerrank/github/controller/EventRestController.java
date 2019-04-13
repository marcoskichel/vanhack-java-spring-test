package com.hackerrank.github.controller;

import com.hackerrank.github.model.Event;
import com.hackerrank.github.service.EventService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
public class EventRestController {

    private final EventService service;

    public EventRestController(EventService service) {
        this.service = service;
    }

    /**
     * Adding new events: The service should be able to add a new event by the POST request at /events.
     * The event JSON is sent in the request body. If an event with the same id already exists then the HTTP
     * response code should be 400, otherwise, the response code should be 201.
     */
    @PostMapping("/events")
    public ResponseEntity<?> insertEvent(@RequestBody Event event, UriComponentsBuilder uriBuilder) {
        service.save(event);
        UriComponents uriComponents =
                uriBuilder.path("/events").build();
        return ResponseEntity.created(uriComponents.toUri()).build();
    }

    /**
     * Returning all the events: The service should be able to return the JSON array of all the events by the GET
     * request at /events. The HTTP response code should be 200. The JSON array should be sorted in ascending order
     * by event ID.
     */
    @GetMapping
    public Iterable<Event> listEvents() {
        return service.listAllOrderedById();
    }

    /**
     * Returning the event records filtered by the actor ID: The service should be able to return the JSON array of all
     * the events which are performed by the actor ID by the GET request at /events/actors/{actorID}.
     * If the requested actor does not exist then HTTP response code should be 404, otherwise, the response
     * code should be 200. The JSON array should be sorted in ascending order by event ID.
     */
    @GetMapping("/events/actors/{actorId}")
    public List<Event> eventsByActor(@PathVariable("actorId") Long actorId) {
        return service.listByActorId(actorId);
    }

}
