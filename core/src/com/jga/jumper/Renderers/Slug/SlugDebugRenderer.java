package com.jga.jumper.Renderers.Slug;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.utils.Array;
import com.jga.jumper.entity.Obstacle;
import com.jga.jumper.entity.Slug;

public class SlugDebugRenderer {

    // == public methods ==
    public void renderObstacleDebug(ShapeRenderer renderer, Array<Slug> slugs) {
        for (Slug slug : slugs) {
            Circle slugBounds = slug.getBounds();
            renderer.circle(slugBounds.x, slugBounds.y, slugBounds.radius, 30);

            renderer.setColor(Color.WHITE);
            Circle sensorBounds = slug.getSensor();
            renderer.circle(sensorBounds.x, sensorBounds.y, sensorBounds.radius, 30);
        }
    }
}
