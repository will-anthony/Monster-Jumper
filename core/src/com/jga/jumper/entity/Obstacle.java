package com.jga.jumper.entity;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Pool;
import com.jga.jumper.config.GameConfig;

public class Obstacle extends EntityBase implements Pool.Poolable {

    // == constants ==
    private static final float SCALE_MAX = 1.0f;

    // == attributes ==
    private Rectangle sensor = new Rectangle();
    private float sensorAngleDegree;
    private float scale;

    @Override
    protected Polygon definePolygonCollider() {
        return null;
    }

    // == constructors ==
    public Obstacle() {
        setSize(GameConfig.OBSTACLE_SIZE, GameConfig.OBSTACLE_SIZE);
    }

    // == public methods ==
    public void update(float delta) {
        if (scale < SCALE_MAX) {
            scale += delta;
        }
    }

    public void setAngleDegree(float value) {
        angleDegrees = value % 360;
        sensorAngleDegree = angleDegrees + 20f;

        float radius = GameConfig.PLANET_HALF_SIZE;

        float originX = GameConfig.WORLD_CENTER_X;
        float originY = GameConfig.WORLD_CENTER_Y;

        float newX = originX + MathUtils.cosDeg(-angleDegrees) * radius;
        float newY = originY + MathUtils.sinDeg(-angleDegrees) * radius;

        setPosition(newX, newY);

        float sensorX = originX + MathUtils.cosDeg(-sensorAngleDegree) * radius;
        float sensorY = originY + MathUtils.sinDeg(-sensorAngleDegree) * radius;

        sensor.set(sensorX, sensorY, width, height);
    }

    public Rectangle getSensor() {
        return sensor;
    }

    public float getScale() {
        return scale;
    }

    public float getSensorAngleDegree() {
        return sensorAngleDegree;
    }


    @Override
    public void reset() {
        setPosition(0,0);
        scale = 0.0f;
    }
}
