package com.vitja.study.degree.repository;

import com.vitja.study.degree.model.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

@Repository
public interface EventRepository extends JpaRepository<Event, String> {
    Page<Event> findByEndDateGreaterThanOrderByStartDateAsc(Timestamp date, Pageable pageable);
    Page<Event> findByEndDateGreaterThanAndVenueIdEqualsOrderByStartDateAsc(Timestamp date, String venueId, Pageable pageable);
}
