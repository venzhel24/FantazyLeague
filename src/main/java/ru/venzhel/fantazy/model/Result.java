package ru.venzhel.fantazy.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "results")
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@NoArgsConstructor
@SuperBuilder
public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "race_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Race race;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "athlete_id")
    private Athlete athlete;

    @Column(name = "rank")
    private int rank;

    @Column(name = "bib")
    private int bib;

    @Column(name = "prone_shooting")
    private int proneShooting;

    @Column(name = "standing_shooting")
    private int standingShooting;

    @Column(name = "total_misses")
    private int totalMisses;

    @Column(name = "points")
    private int points;

    @Column(name = " behind")
    private String behind;
}
