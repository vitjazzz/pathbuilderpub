package com.vitja.study.degree.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode(exclude = {"id"})
@Entity
@Table(name = "rating")
public class Rating {

    @EmbeddedId
    private RatingId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("venueId")
    private Venue venue;

    private Integer rating;

    private Rating(){}

    public Rating(User user, Venue venue, Integer rating){
        this.user = user;
        this.venue = venue;
        this.id = new RatingId(user.getId(), venue.getId());
        this.rating = rating;
    }
}
