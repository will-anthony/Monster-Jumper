package com.jga.jumper.Renderers.bear;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.jga.jumper.config.GameConfig;
import com.jga.jumper.entity.Bear;

public class BearDebugRenderer {

    public void renderObstacleDebug(ShapeRenderer renderer, Array<Bear> bears) {
        for (Bear bear : bears) {
//            Rectangle bearBounds = bear.getBounds();
//            renderer.rect(bearBounds.x, bearBounds.y, 0 ,0,
//                    bearBounds.width, bearBounds.height,
//                    1,1,
//                    GameConfig.START_ANGLE - bear.getAngleDegrees());

//            renderer.setColor(Color.WHITE);
//            Rectangle sensorBounds = bear.getSensor();
//            renderer.rect(sensorBounds.x, sensorBounds.y, 0,0,
//                    sensorBounds.getWidth(), sensorBounds.getHeight(),
//                    1,1,
//                    GameConfig.START_ANGLE - bear.getAngleDegrees());
        }
    }
}
