package ru.venzhel.fantazy.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "events")
@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "city")
    private String city;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;


    @Transient
    private String status = "finished";

    @PostLoad
    public void postLoad() {
        if (LocalDate.now().isBefore(this.getStartDate())) {
            this.status = "upcoming";
        } else if (LocalDate.now().isAfter(this.getStartDate().minusDays(1)) && LocalDate.now().isBefore(this.getEndDate().plusDays(1))) {
            this.status = "current";
        }
    }
}
