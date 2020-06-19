package com.jga.jumper.entity;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Pool;
import com.jga.jumper.config.GameConfig;

public class Slug extends EntityBase implements Pool.Poolable {

    private float angleDegreeSpeed = GameConfig.SLUG_START_ANGULAR_SPEED;
    private Circle sensor = new Circle();
    private int currentSlugState;
    private float radius;
    private float deathTimer = 0.5f;

    public Slug() {
        setSize(GameConfig.SLUG_SIZE, GameConfig.SLUG_SIZE);
        radius = GameConfig.PLANET_HALF_SIZE - GameConfig.MONSTER_SIZE;
    }

    public void update(float delta) {

    }

    public void move(float delta) {
        angleDegrees -= angleDegreeSpeed * delta;
        setAngleDegree();
    }

    public void setStartingPosition(float value) {
        angleDegrees = value % 360;
        setAngleDegree();
    }

    public void setAngleDegree() {

        float sensorAngleDegree = angleDegrees + 3f;

        float originX = GameConfig.WORLD_CENTER_X;
        float originY = GameConfig.WORLD_CENTER_Y;

        float newX = originX + MathUtils.cosDeg(-angleDegrees) * radius;
        float newY = originY + MathUtils.sinDeg(-angleDegrees) * radius;

        setPosition(newX, newY);

        float sensorX = originX + MathUtils.cosDeg(-sensorAngleDegree) * (radius + 0.2f);
        float sensorY = originY + MathUtils.sinDeg(-sensorAngleDegree) * (radius + 0.2f);

        sensor.set(sensorX, sensorY, GameConfig.OBSTACLE_HALF_SIZE);
    }

    @Override
    public void reset() {
        setPosition(0, 0);
        currentSlugState = 0;
        radius = GameConfig.PLANET_HALF_SIZE - GameConfig.MONSTER_SIZE;
        deathTimer = 0.5f;
    }

    public Circle getSensor() {
        return sensor;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
        setAngleDegree();
    }

    public float getDeathTimer() {
        return deathTimer;
    }

    public void setDeathTimer(float deathTimer) {
        this.deathTimer = deathTimer;
    }

    public int getCurrentSlugState() {
        return currentSlugState;
    }

    public void setCurrentSlugState(int currentSlugState) {
        this.currentSlugState = currentSlugState;
    }
}
