package com.vitja.study.degree.repository;

import com.vitja.study.degree.model.Venue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VenueRepository extends JpaRepository<Venue, String> {
    Optional<Venue> findByName(String name);

    @Query("SELECT v from Venue v where (v.lat < :lat + :rad) and (v.lat > :lat - :rad) " +
            "and (v.lon > :lon - :rad) and (v.lon < :lon + :rad)")
    List<Venue> findByLocation(@Param("lat") Double lat, @Param("lon") Double lon,
                               @Param("rad") Double halfSquareSideLength);
}
