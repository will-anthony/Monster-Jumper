package com.jga.jumper.Renderers.Obstacle;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.jga.jumper.entity.Obstacle;

public class ObstacleDebugRenderer {

    // == constructors ==
    public ObstacleDebugRenderer() {

    }

    // == public methods ==
    public void renderObstacleDebug(ShapeRenderer renderer, Array<Obstacle> obstacles) {
        for (Obstacle obstacle : obstacles) {
            Rectangle obstacleBounds = obstacle.getBounds();
            renderer.rect(obstacleBounds.x, obstacleBounds.y, obstacleBounds.width, obstacleBounds.height);

            renderer.setColor(Color.WHITE);
            Rectangle sensorBounds = obstacle.getSensor();
            renderer.rect(sensorBounds.x, sensorBounds.y, sensorBounds.width, sensorBounds.height);
        }
    }
}
