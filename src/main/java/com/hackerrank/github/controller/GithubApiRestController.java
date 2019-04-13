package com.hackerrank.github.controller;

import com.hackerrank.github.service.ActorService;
import com.hackerrank.github.service.EventService;
import com.hackerrank.github.service.RepoService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @DeleteMapping("/erase")
    public void erase() {
        this.eventService.deleteAll();
        this.actorService.deleteAll();
        this.repoService.deleteAll();
    }
}
