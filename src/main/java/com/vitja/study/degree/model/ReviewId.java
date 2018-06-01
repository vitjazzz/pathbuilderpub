package com.vitja.study.degree.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class ReviewId implements Serializable {
    @Column(name = "user_id")
    private String userId;

    @Column(name = "venue_id")
    private String venueId;

    public ReviewId() {
    }
}
