package com.vitja.study.degree.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(exclude = {"id"})
@ToString(exclude = {"id"})
@Builder
@Entity
@Table(name = "event")
public class Event implements Serializable {
    @Id
    private String id;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(length = 500)
    private String description;

    @Column(name = "start_date", nullable = false)
    private Timestamp startDate;

    @Column(name = "end_date", nullable = false)
    private Timestamp endDate;

    @ManyToOne
    @JoinColumn(name = "venue_id", referencedColumnName = "id", nullable = false)
    private Venue venue;
}
