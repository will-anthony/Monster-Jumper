package com.jga.jumper.entity.entity_providers;

import com.badlogic.gdx.utils.Array;
import com.jga.jumper.controllers.PlanetController;
import com.jga.jumper.entity.Planet;

public class PlanetEntityProvider implements EntityProvider<Planet> {

    private PlanetController planetController;

    public PlanetEntityProvider(PlanetController planetController) {
        this.planetController = planetController;
    }

    @Override
    public Array<Planet> getEntities() {
        return planetController.getPlanets();
    }
}
