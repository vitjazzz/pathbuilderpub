package com.vitja.study.degree.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.stream.IntStream;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(exclude = {"id", "events", "ratings"})
@ToString(exclude = {"id", "events", "ratings"})
@Builder
@Entity
@Table(name = "venue")
public class Venue implements Serializable {
    @Id
    private String id;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(nullable = false)
    private Double lat;

    @Column(nullable = false)
    private Double lon;

    @Column()
    private String photoURL;

    @OneToMany(mappedBy = "venue"/*,
            cascade = CascadeType.ALL,
            orphanRemoval = true*/)
    @JsonIgnore
    private List<Event> events;

    @OneToMany(mappedBy = "venue")
    @JsonIgnore
    @Setter(AccessLevel.NONE)
    private List<Rating> ratings = new ArrayList<>();

    @OneToMany(mappedBy = "venue")
    @JsonIgnore
    @Setter(AccessLevel.NONE)
    private List<Review> reviews = new ArrayList<>();

    @Setter(AccessLevel.NONE)
    @Transient
    private Double rating;

    public Double getRating() {
        if(this.getRatings() == null){
            return -1.0;
        }
        OptionalDouble result = this.getRatings().stream().mapToInt(Rating::getRating).average();
        return result.isPresent() ? result.getAsDouble() : -1.0;
    }

}
