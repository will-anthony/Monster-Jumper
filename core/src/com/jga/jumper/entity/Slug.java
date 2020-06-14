package com.jga.jumper.entity;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Pool;
import com.jga.jumper.config.GameConfig;

public class Slug extends EntityBase implements Pool.Poolable {

    private boolean clockWise;
    private Circle killCollider = new Circle();

    public Slug() {
        setSize(GameConfig.SLUG_SIZE, GameConfig.SLUG_SIZE);
        clockWise = MathUtils.randomBoolean();
    }

    public void update(float delta) {

    }

    public void setAngleDegree(float value) {
        angleDegree = value % 360;

        float radius = GameConfig.PLANET_HALF_SIZE;

        float originX = GameConfig.WORLD_CENTER_X;
        float originY = GameConfig.WORLD_CENTER_Y;

        float newX = originX + MathUtils.cosDeg(-angleDegree) * radius;
        float newY = originY + MathUtils.cosDeg(-angleDegree) * radius;

        setPosition(newX, newY);

        float killColliderX = originX + MathUtils.cosDeg(-angleDegree) * radius + 0.1f;
        float killColliderY = originY + MathUtils.cosDeg(-angleDegree) * radius + 0.1f;

        killCollider.set(killColliderX, killColliderY, GameConfig.OBSTACLE_HALF_SIZE);
    }

    @Override
    public void reset() {

    }
}
