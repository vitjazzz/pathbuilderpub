package com.vitja.study.degree.service.object;

import com.vitja.study.degree.model.Event;
import com.vitja.study.degree.model.Venue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface EventService {
    Event save(Event event);
    Optional<Event> getEvent(String id);
    void deleteEvent(String id);
    Page<Event> getSortedActiveEvents(Integer page, Integer size);
    Page<Event> getSortedActiveEventsInVenue(String venueId, Integer page, Integer size);
}
