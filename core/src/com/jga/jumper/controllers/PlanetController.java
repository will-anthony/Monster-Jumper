package com.jga.jumper.controllers;

import com.badlogic.gdx.utils.Array;
import com.jga.jumper.config.GameConfig;
import com.jga.jumper.entity.Planet;

public class PlanetController {

    // == attributes==
    private Array<Planet> planets = new Array<>();
    private Planet planet;

    // == constructors ==
    public PlanetController() {
        planet = new Planet();
        init();
    }

    // == init ==
    private void init() {
        planet.setPosition(
                GameConfig.WORLD_CENTER_X - GameConfig.PLANET_HALF_SIZE,
                GameConfig.WORLD_CENTER_Y - GameConfig.PLANET_HALF_SIZE);

        planets.add(planet);
    }

    // public methods ==
//    public Planet getPlanet() {
//        return planet;
//    }

    public Array<Planet> getPlanets() {
        return planets;
    }
}
