package com.example.AutoEcole.bll.service;

import java.util.List;

public interface InscriptionService {
    CreatePlanetResponseBody createPlanet(CreatePlanetRequestBody request);
    public List<Planet> getAllPlanets();
    public Planet getPlanetById(Long id);

    boolean update(Long id, Planet planet);

    boolean delete(Long id);
}
