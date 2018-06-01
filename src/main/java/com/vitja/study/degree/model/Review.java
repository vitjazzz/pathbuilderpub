package com.vitja.study.degree.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "review")
@Getter
@Setter
@EqualsAndHashCode(exclude = {"id"})
public class Review {
    @EmbeddedId
    private ReviewId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("venueId")
    private Venue venue;

    private String review;

    public Review() {
    }

    public Review(User user, Venue venue, String review) {
        this.id = new ReviewId(user.getId(), venue.getId());
        this.user = user;
        this.venue = venue;
        this.review = review;
    }
}
