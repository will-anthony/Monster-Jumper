package com.jga.jumper.entity;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.jga.jumper.config.GameConfig;

public class Bear extends EnemyBase {

    private float angleDegreeSpeed;
    private float sensorAngleDegree;
    private float boundsAngleDegree;
    private int currentBearState;
    private float deathTimer;
    private int hitPoints;
    private float bearDamagedTimer;
    private boolean hasDamageAnimationStarted;

    public Bear() {
        angleDegreeSpeed = GameConfig.BEAR_START_ANGULAR_SPEED;
        super.setSize(GameConfig.BEAR_SIZE, GameConfig.BEAR_SIZE);
        super.setRadius(GameConfig.PLANET_HALF_SIZE - GameConfig.MONSTER_SIZE);
        deathTimer = 0.5f;
        hitPoints = 1;
        bearDamagedTimer = 1f;
    }

    public void update(float delta) {

    }

    public void move(float delta) {
        int directionalMultiplier = 1;

        if (clockWise) {
            directionalMultiplier = -1;
        }
        angleDegrees -= (angleDegreeSpeed * directionalMultiplier) * delta;
        if(angleDegreeSpeed > GameConfig.BEAR_START_ANGULAR_SPEED) {
            angleDegreeSpeed -= 0.5f;
        }
        setAngleDegree();
    }

    public void charge(float delta) {
        int directionalMultiplier = 1;

        if (clockWise) {
            directionalMultiplier = -1;
        }
        angleDegrees -= (angleDegreeSpeed * directionalMultiplier) * delta;
        if(angleDegreeSpeed < GameConfig.BEAR_CHARGE_SPEED) {
            angleDegreeSpeed += 0.5f;
        }
        setAngleDegree();

    }

    public void setStartingPosition(float value) {
        angleDegrees = value % 360;
        setAngleDegree();
    }

    @Override
    public void setAngleDegree() {

        boundsAngleDegree = angleDegrees + 7f;
        sensorAngleDegree = angleDegrees + 7f;

        super.setAngleDegree();

        float originX = GameConfig.WORLD_CENTER_X;
        float originY = GameConfig.WORLD_CENTER_Y;

        float boundsX = originX + MathUtils.cosDeg(-boundsAngleDegree) * (radius);
        float boundsY = originY + MathUtils.sinDeg(-boundsAngleDegree) * (radius);

        float sensorX = originX + MathUtils.cosDeg(-sensorAngleDegree) * (radius + 0.2f);
        float sensorY = originY + MathUtils.sinDeg(-sensorAngleDegree) * (radius + 0.2f);

        updateBounds(boundsX, boundsY);
        updateSensorBounds(sensorX, sensorY);
    }

    public void reset() {
        setPosition(0, 0);
        currentBearState = GameConfig.ENEMY_SPAWNING_STATE;
        radius = GameConfig.PLANET_HALF_SIZE - GameConfig.MONSTER_SIZE;
        deathTimer = 0.5f;
        super.clockWise = MathUtils.randomBoolean();
        hitPoints = 1;
        bearDamagedTimer = 1f;
    }

    public Rectangle getSensor() {
        return sensor;
    }

    public float getRadius() {
        return radius;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public void setHitPoints(int hitPoints) {
        this.hitPoints = hitPoints;
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

    public int getCurrentBearState() {
        return currentBearState;
    }

    public void setCurrentBearState(int currentBearState) {
        this.currentBearState = currentBearState;
    }

    public float getBearDamagedTimer() {
        return bearDamagedTimer;
    }

    public void setBearDamagedTimer(float bearDamagedTimer) {
        this.bearDamagedTimer = bearDamagedTimer;
    }

    public boolean isHasDamageAnimationStarted() {
        return hasDamageAnimationStarted;
    }

    public void setHasDamageAnimationStarted(boolean hasDamageAnimationStarted) {
        this.hasDamageAnimationStarted = hasDamageAnimationStarted;
    }
}
