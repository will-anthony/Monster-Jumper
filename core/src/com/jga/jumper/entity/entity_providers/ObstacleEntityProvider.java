package com.jga.jumper.entity.entity_providers;

import com.badlogic.gdx.utils.Array;
import com.jga.jumper.controllers.ObstacleController;
import com.jga.jumper.entity.Obstacle;

public class ObstacleEntityProvider implements EntityProvider<Obstacle> {

    private final ObstacleController obstacleController;

    public ObstacleEntityProvider(ObstacleController obstacleController) {
        this.obstacleController = obstacleController;
    }

    @Override
    public Array<Obstacle> getEntities() {
        return obstacleController.getObstacles();
    }
}
