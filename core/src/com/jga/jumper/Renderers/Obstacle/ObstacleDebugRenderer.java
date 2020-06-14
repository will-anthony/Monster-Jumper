package com.jga.jumper.Renderers.Obstacle;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.utils.Array;
import com.jga.jumper.entity.Obstacle;

public class ObstacleDebugRenderer {

    // == constructors ==
    public ObstacleDebugRenderer() {

    }

    // == public methods ==
    public void renderObstacleDebug(ShapeRenderer renderer, Array<Obstacle> obstacles) {
        for (Obstacle obstacle : obstacles) {
            Circle obstacleBounds = obstacle.getBounds();
            renderer.circle(obstacleBounds.x, obstacleBounds.y, obstacleBounds.radius, 30);

            renderer.setColor(Color.WHITE);
            Circle sensorBounds = obstacle.getSensor();
            renderer.circle(sensorBounds.x, sensorBounds.y, sensorBounds.radius, 30);
        }
    }
}
