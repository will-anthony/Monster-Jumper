package com.jga.jumper.Renderers.Planet;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.utils.Array;
import com.jga.jumper.entity.Planet;

public class PlanetDebugRenderer {

    // == constructors ==
    public PlanetDebugRenderer() {
    }

    // == public methods ==
    public void renderPlanetDebug(ShapeRenderer renderer, Array<Planet> planets) {
        for (Planet planet : planets) {
            Circle planetBounds = planet.getBounds();
            renderer.circle(planetBounds.x, planetBounds.y, planetBounds.radius, 30);
        }
    }
}