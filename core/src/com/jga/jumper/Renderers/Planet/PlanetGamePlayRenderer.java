package com.jga.jumper.Renderers.Planet;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.jga.jumper.assets.RegionNames;
import com.jga.jumper.entity.Planet;

public class PlanetGamePlayRenderer {

    // == attributes ==
    private TextureAtlas gamePlayAtlas;

    // == constructors ==
    public PlanetGamePlayRenderer(TextureAtlas gamePlayAtlas) {
        this.gamePlayAtlas = gamePlayAtlas;
    }

    // == public methods ==
    public void renderPlanetGamePlay(SpriteBatch batch, Array<Planet> planets) {

        TextureRegion planetRegion = gamePlayAtlas.findRegion(RegionNames.PLANET);
        for (Planet planet : planets) {

            batch.draw(planetRegion,
                    planet.getX(), planet.getY(),
                    planet.getWidth(), planet.getHeight()
            );
        }
    }


}
