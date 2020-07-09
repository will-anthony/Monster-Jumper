package com.jga.jumper.entity;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.utils.Pool;
import com.jga.jumper.config.GameConfig;

public class Bear extends EnemyBase implements Pool.Poolable, KillCollider {

    private float angleDegreeSpeed;
    private float boundsAngleDegree;
    private int currentBearState;
    private float deathTimer;
    private int hitPoints;
    private float bearDamagedTimer;
    private boolean hasDamageAnimationStarted;
    private Polygon killCollider;

    public Bear() {
        angleDegreeSpeed = GameConfig.BEAR_START_ANGULAR_SPEED;
        setSize(GameConfig.BEAR_SIZE, GameConfig.BEAR_SIZE);
        killCollider = defineKillCollider();
        setRadius(GameConfig.PLANET_HALF_SIZE - GameConfig.MONSTER_SIZE);
        deathTimer = 1f;
        hitPoints = 1;
        bearDamagedTimer = 1f;
    }

    @Override
    protected Polygon definePolygonCollider() {

        float[] polygonCoordinates = {0, 0,
                0, GameConfig.BEAR_SIZE - 0.55f,
                GameConfig.BEAR_SIZE - 0.65f, GameConfig.BEAR_SIZE - 0.55f,
                GameConfig.BEAR_SIZE - 0.65f, 0};

        Polygon polygon = new Polygon(polygonCoordinates);
        polygon.setOrigin(0, 0);

        polygon.setRotation(GameConfig.START_ANGLE - angleDegrees);

        return polygon;
    }

    @Override
    public Polygon defineKillCollider() {

        float[] polygonCoordinates = {0, GameConfig.BEAR_SIZE - 0.55f,
                0, GameConfig.BEAR_SIZE - 0.45f,
                GameConfig.BEAR_SIZE - 0.65f, GameConfig.BEAR_SIZE - 0.45f,
                GameConfig.BEAR_SIZE - 0.65f, GameConfig.BEAR_SIZE - 0.55f};

        Polygon polygon = new Polygon(polygonCoordinates);
        polygon.setOrigin(0, 0);

        polygon.setRotation(GameConfig.START_ANGLE - angleDegrees);

        return polygon;
    }

    @Override
    public Polygon getKillCollider() {
        return this.killCollider;
    }

    public void update(float delta) {

    }

    public void move(float delta) {

        int directionalMultiplier = 1;

        if (clockWise) {
            directionalMultiplier = -1;
        }
        angleDegrees -= (angleDegreeSpeed * directionalMultiplier) * delta;

        if (angleDegreeSpeed > GameConfig.BEAR_START_ANGULAR_SPEED) {
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
        if (angleDegreeSpeed < GameConfig.BEAR_CHARGE_SPEED) {
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

        super.setAngleDegree();

        float originX = GameConfig.WORLD_CENTER_X;
        float originY = GameConfig.WORLD_CENTER_Y;

        float newX = originX + MathUtils.cosDeg(-boundsAngleDegree) * (radius);
        float newY = originY + MathUtils.sinDeg(-boundsAngleDegree) * (radius);

        polygonCollider.setPosition(newX, newY);
        polygonCollider.setRotation(GameConfig.START_ANGLE - angleDegrees - 10f);

        killCollider.setPosition(newX, newY);
        killCollider.setRotation(GameConfig.START_ANGLE - angleDegrees - 10f);

    }

    public void reset() {
        setPosition(0, 0);
        currentBearState = GameConfig.ENEMY_SPAWNING_STATE;
        angleDegreeSpeed = GameConfig.BEAR_START_ANGULAR_SPEED;
        radius = GameConfig.PLANET_HALF_SIZE - GameConfig.MONSTER_SIZE;
        deathTimer = 0.5f;
        super.clockWise = MathUtils.randomBoolean();
        hitPoints = 1;
        bearDamagedTimer = 1f;
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
