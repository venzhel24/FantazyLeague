package ru.venzhel.fantazy.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "individual_results")
@Data
@NoArgsConstructor
@SuperBuilder
@OnDelete(action = OnDeleteAction.CASCADE)
public class Individual extends Result {
    @Column(name = "prone_shooting_2")
    private int proneShooting2;

    @Column(name = "standing_shooting_2")
    private int standingShooting2;

    @Column(name = "ski_time")
    private String skiTime;

    @Column(name = "result_time")
    private String resultTime;
}
