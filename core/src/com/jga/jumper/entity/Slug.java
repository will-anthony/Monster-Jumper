package com.jga.jumper.entity;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Pool;
import com.jga.jumper.config.GameConfig;
import com.jga.jumper.state_machines.SlugState;

public class Slug extends EntityBase implements Pool.Poolable {

    private boolean clockWise;
    private float angleDegreeSpeed = GameConfig.SLUG_START_ANGULAR_SPEED;
    private Circle killCollider = new Circle();
    private SlugState slugState = SlugState.IDLE;

    public Slug() {
        setSize(GameConfig.SLUG_SIZE, GameConfig.SLUG_SIZE);
        clockWise = MathUtils.randomBoolean();
    }

    public void update(float delta) {
        switch (slugState){
            case IDLE:
                break;
            case DEAD:
                break;
            case WALKING:
                break;

        }
    }

    public void setAngleDegree(float value) {
        angleDegrees = value % 360;

        float radius = GameConfig.PLANET_HALF_SIZE - 0.15f;
        float originX = GameConfig.WORLD_CENTER_X;
        float originY = GameConfig.WORLD_CENTER_Y;

        float newX = originX + MathUtils.cosDeg(-angleDegrees) * radius;
        float newY = originY + MathUtils.sinDeg(-angleDegrees) * radius;

        setPosition(newX, newY);

//        angleDegrees += angleDegreeSpeed * delta;
//        angleDegrees = angleDegrees % 360;
//
//        float radius = GameConfig.PLANET_HALF_SIZE;
//        float originX = GameConfig.WORLD_CENTER_X;
//        float originY = GameConfig.WORLD_CENTER_Y;
//
//        float newX = originX + MathUtils.cosDeg(-angleDegrees) * radius;
//        float newY = originY + MathUtils.sinDeg(-angleDegrees) * radius;
//
//        setPosition(newX, newY);

//        float killColliderX = originX + MathUtils.cosDeg(-angleDegrees) * radius + 0.1f;
//        float killColliderY = originY + MathUtils.cosDeg(-angleDegrees) * radius + 0.1f;
//
//        killCollider.set(killColliderX, killColliderY, GameConfig.OBSTACLE_HALF_SIZE);
    }

    @Override
    public void reset() {
        setPosition(0,0);
    }

    public SlugState getState() {
        return slugState;
    }
}
