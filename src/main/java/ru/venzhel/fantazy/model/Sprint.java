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
@Table(name = "sprint_results")
@Data
@NoArgsConstructor
@SuperBuilder
@OnDelete(action = OnDeleteAction.CASCADE)
public class Sprint extends Result {
    @Column(name = "result_time")
    private String resultTime;
}
