package com.jga.jumper.Renderers.Slug;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.jga.jumper.config.GameConfig;
import com.jga.jumper.entity.EnemyBase;
import com.jga.jumper.entity.Slug;

public class SlugDebugRenderer {

    // == public methods ==
    public void renderObstacleDebug(ShapeRenderer renderer, Array<Slug> slugs) {
        for (Slug slug : slugs) {
            Rectangle slugBounds = slug.getBounds();
            renderer.rect(slugBounds.x, slugBounds.y, 0 ,0,
                    slugBounds.width, slugBounds.height,
                    1,1,
                    GameConfig.START_ANGLE - slug.getAngleDegrees());

            renderer.setColor(Color.WHITE);
            Rectangle sensorBounds = slug.getSensor();
            renderer.rect(sensorBounds.x, sensorBounds.y, 0,0,
                    sensorBounds.getWidth(), sensorBounds.getHeight(),
                    1,1,
                    GameConfig.START_ANGLE - slug.getAngleDegrees());

            Rectangle distanceSensor = slug.getDistanceSensor();
            renderer.rect(distanceSensor.x, distanceSensor.y, 0,0,
                    distanceSensor.getWidth(), distanceSensor.getHeight(),
                    1,1,
                    GameConfig.START_ANGLE - slug.getAngleDegrees());

        }
    }
}
