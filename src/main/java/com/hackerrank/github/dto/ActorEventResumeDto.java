package com.hackerrank.github.dto;

import com.hackerrank.github.model.Actor;

import java.util.Date;

public class ActorEventResumeDto {

    private Actor actor;
    private Long eventCount;
    private Date lastEvent;

    public ActorEventResumeDto(Actor actor, Long eventCount, Date lastEvent) {
        this.actor = actor;
        this.eventCount = eventCount;
        this.lastEvent = lastEvent;
    }

    public ActorEventResumeDto() {

    }

    public Actor getActor() {
        return actor;
    }

    public void setActor(Actor actor) {
        this.actor = actor;
    }

    public Long getEventCount() {
        return eventCount;
    }

    public void setEventCount(Long eventCount) {
        this.eventCount = eventCount;
    }

    public Date getLastEvent() {
        return lastEvent;
    }

    public void setLastEvent(Date lastEvent) {
        this.lastEvent = lastEvent;
    }
}
