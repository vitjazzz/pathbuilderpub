package com.vitja.study.degree.controller;

import com.vitja.study.degree.dto.EventDTO;
import com.vitja.study.degree.exception.EventNotFoundException;
import com.vitja.study.degree.exception.VenueNotFoundException;
import com.vitja.study.degree.model.Event;
import com.vitja.study.degree.model.Venue;
import com.vitja.study.degree.service.object.EventService;
import com.vitja.study.degree.service.object.VenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.sql.Timestamp;
import java.util.Optional;

@RestController
@RequestMapping("/degree/event")
public class EventController {
    private EventService eventService;
    private VenueService venueService;

    @Autowired
    public EventController(EventService eventService, VenueService venueService){
        this.eventService = eventService;
        this.venueService = venueService;
    }

    @PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_ADMIN')")
    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<Event> createEvent(@RequestBody EventDTO eventDTO){
        Event event = Event.builder()
                .name(eventDTO.name)
                .description(eventDTO.description)
                .startDate(new Timestamp(eventDTO.startDate.getTime()))
                .endDate(new Timestamp(eventDTO.endDate.getTime()))
                .build();
        Optional<Venue> parentVenue = venueService.getVenueByName(eventDTO.venueName);
        if(!parentVenue.isPresent()){
            throw new VenueNotFoundException("Name: "+ eventDTO.venueName);
        }
        event.setVenue(parentVenue.get());
        Event savedEvent = eventService.save(event);
        return ResponseEntity.ok().body(savedEvent);
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Event> getEvent(@PathVariable String id){
        Optional<Event> event = eventService.getEvent(id);
        if(!event.isPresent()){
            throw new EventNotFoundException("ID: " + id);
        }
        return ResponseEntity.ok(event.get());
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = RequestMethod.GET, params = {"page", "size", "venueId"})
    public ResponseEntity<Page<Event>> getEvents(@RequestParam int page,
                                                 @RequestParam int size,
                                                 @RequestParam(required = false) String venueId){
        Page<Event> events = venueId.isEmpty() ?
                eventService.getSortedActiveEvents(page, size) :
                eventService.getSortedActiveEventsInVenue(venueId, page, size);

        return ResponseEntity.ok(events);
    }

    @PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_ADMIN')")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteEvent(@PathVariable String id){
        eventService.deleteEvent(id);
    }
}
