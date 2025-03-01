package com.example.AutoEcole.dal.domain.entity;

import jakarta.persistence.*;
import org.springframework.security.core.userdetails.User;


import java.util.Date;
import java.util.List;

@Entity


public class Inscription {

    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name="planet_id")
    private Planet planet;

    @Column(nullable = false)
    private Date date;

    @OneToMany(mappedBy = "réservation")
    private Set<Passenger> passengers;
    private Integer nombreParticipants;

    @OneToMany(mappedBy = "reservation")

    private List<BookingActivity> bookingActivities;

}
