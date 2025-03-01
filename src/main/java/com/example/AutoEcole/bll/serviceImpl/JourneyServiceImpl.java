package com.example.AutoEcole.bll.serviceImpl;

import com.example.AutoEcole.dal.repository.DevisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class JourneyServiceImpl {

    private final DevisRepository journeyRepository;

    public Journey createJourney(Journey journey) {
        return Journey.save(journey);
    }
}
