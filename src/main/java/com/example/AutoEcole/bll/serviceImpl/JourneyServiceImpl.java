package com.example.AutoEcole.bll.serviceImpl;

import com.example.AutoEcole.dal.repository.JourneyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class JourneyServiceImpl {

    private final JourneyRepository journeyRepository;

    public Journey createJourney(Journey journey) {
        return Journey.save(journey);
    }
}
