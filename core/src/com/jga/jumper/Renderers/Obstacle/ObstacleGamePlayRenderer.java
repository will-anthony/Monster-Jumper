package com.jga.jumper.Renderers.Obstacle;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.jga.jumper.assets.RegionNames;
import com.jga.jumper.config.GameConfig;
import com.jga.jumper.entity.Obstacle;

public class ObstacleGamePlayRenderer {

    // == attributes ==
    private Animation<TextureRegion> obstacleAnimation;

    // == constructors ==
    public ObstacleGamePlayRenderer(TextureAtlas gamePlayAtlas) {

        obstacleAnimation = new Animation<TextureRegion>(0.2f,
                gamePlayAtlas.findRegions(RegionNames.OBSTACLE),
                Animation.PlayMode.LOOP_PINGPONG);
    }

    // == public methods ==
    public void renderObstacleGamePlay(SpriteBatch batch, float animationTime, Array<Obstacle> obstacles) {
        TextureRegion obstacleRegion = obstacleAnimation.getKeyFrame(animationTime);

        for (Obstacle obstacle : obstacles) {
            batch.draw(obstacleRegion,
                    obstacle.getX(), obstacle.getY(),
                    0, 0,
                    obstacle.getWidth(), obstacle.getHeight(),
                    obstacle.getScale(), obstacle.getScale(),
                    GameConfig.START_ANGLE - obstacle.getAngleDegree());
        }
    }
}
