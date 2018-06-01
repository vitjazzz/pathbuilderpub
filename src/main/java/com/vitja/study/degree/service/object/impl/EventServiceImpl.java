package com.vitja.study.degree.service.object.impl;


import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.ParamExpressionImpl;
import com.querydsl.core.types.dsl.PathBuilder;
import com.vitja.study.degree.model.Event;
import com.vitja.study.degree.repository.EventRepository;
import com.vitja.study.degree.service.object.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.data.querydsl.QSort;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class EventServiceImpl implements EventService {
    private EventRepository eventRepository;

    @Autowired
    public EventServiceImpl(EventRepository eventRepository){
        this.eventRepository = eventRepository;
    }

    @Override
    public Optional<Event> getEvent(String id) {
        return eventRepository.findById(id);
    }

    @Override
    public Page<Event> getSortedActiveEvents(Integer page, Integer size) {
        Pageable pageable = new QPageRequest(page - 1, size);
        Timestamp currentDate = new Timestamp(new Date().getTime());
        return eventRepository.findByEndDateGreaterThanOrderByStartDateAsc(currentDate, pageable);
    }

    @Override
    public Page<Event> getSortedActiveEventsInVenue(String venueId, Integer page, Integer size) {
        Pageable pageable = new QPageRequest(page - 1, size);
        Timestamp currentDate = new Timestamp(new Date().getTime());
        return eventRepository.findByEndDateGreaterThanAndVenueIdEqualsOrderByStartDateAsc(currentDate, venueId, pageable);
    }

    @Override
    public Event save(Event event) {
        event.setId(UUID.randomUUID().toString());
        return eventRepository.save(event);
    }

    @Override
    public void deleteEvent(String id) {
        eventRepository.deleteById(id);
    }
}
