package com.example.AutoEcole.bll.service;

import org.springframework.stereotype.Service;

@Service
public interface MapboxService {
    String getGeocodeData(String fullAddress);
}
