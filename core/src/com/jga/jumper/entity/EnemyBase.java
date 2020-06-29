package com.jga.jumper.entity;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.jga.jumper.config.GameConfig;

public abstract class EnemyBase extends EntityBase {

    // == attributes
    protected boolean clockWise;
    protected Rectangle sensor;
    protected float radius;

    // == constructors ==
    public EnemyBase() {
        sensor = new Rectangle(x,y,width,height);
        radius = GameConfig.PLANET_HALF_SIZE;
    }

    public void setStartingPosition(float value) {
        angleDegrees = value % 360;
        setAngleDegree();
    }

    public void setAngleDegree() {

        float originX = GameConfig.WORLD_CENTER_X;
        float originY = GameConfig.WORLD_CENTER_Y;

        float newX = originX + MathUtils.cosDeg(-angleDegrees) * radius;
        float newY = originY + MathUtils.sinDeg(-angleDegrees) * radius;

        setPosition(newX, newY);
    }

    public boolean isClockWise() {
        return clockWise;
    }

    public void setClockWise(boolean clockWise) {
        this.clockWise = clockWise;
    }

    protected void updateSensorBounds(float x, float y) {
        sensor.setPosition(x,y);
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }
}
