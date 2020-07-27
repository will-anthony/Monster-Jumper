package com.jga.jumper.entity;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.utils.Pool;
import com.jga.jumper.config.GameConfig;
import com.jga.jumper.entity.abstract_classes_and_interfaces.AwarenessCollider;
import com.jga.jumper.entity.abstract_classes_and_interfaces.EnemyBase;
import com.jga.jumper.entity.abstract_classes_and_interfaces.KillCollider;

public class Bear extends EnemyBase implements Pool.Poolable, KillCollider, AwarenessCollider {

    private float angleDegreeSpeed;
    private float boundsAngleDegree;
    private int currentBearState;
    private float deathTimer;
    private int hitPoints;
    private float bearDamagedTimer;
    private boolean hasDamageAnimationStarted;
    private boolean hasSummonAnimationStarted;
    private float stompLogicTimer;
    boolean hasSpawnedSpikes;

    private Polygon killCollider;
    private Polygon awarenessCollider;

    public Bear() {
        angleDegreeSpeed = GameConfig.BEAR_START_ANGULAR_SPEED;
        setSize(GameConfig.BEAR_SIZE, GameConfig.BEAR_SIZE);
        killCollider = defineKillCollider();
        clockWise = false;
        awarenessCollider = defineAwarenessCollider();
        setRadius(GameConfig.PLANET_HALF_SIZE - GameConfig.MONSTER_SIZE);
        deathTimer = 1f;
        hitPoints = 1;
        bearDamagedTimer = 1f;
        stompLogicTimer = 2f;
    }

    @Override
    public Polygon definePolygonCollider() {

        float[] polygonCoordinatesClockwise = {4f, 0,
                0f, GameConfig.BEAR_SIZE - 1.1f,
                GameConfig.BEAR_SIZE - 0.7f, GameConfig.BEAR_SIZE - 1.1f,
                GameConfig.BEAR_SIZE - 0.7f, 0};


        float[] polygonCoordinatesAntiClockwise = {0.6f, 0,
                0.6f, GameConfig.BEAR_SIZE - 1.3f,
                GameConfig.BEAR_SIZE - 0.7f, GameConfig.BEAR_SIZE - 1.3f,
                GameConfig.BEAR_SIZE - 0.7f, 0};

        if (clockWise) {
            Polygon polygon = new Polygon(polygonCoordinatesClockwise);
            polygon.setOrigin(0, 0);

            polygon.setRotation(GameConfig.START_ANGLE - angleDegrees);

            return polygon;

        } else {
            Polygon polygon = new Polygon(polygonCoordinatesAntiClockwise);
            polygon.setOrigin(0, 0);

            polygon.setRotation(GameConfig.START_ANGLE - angleDegrees);

            return polygon;
        }
    }

    @Override
    public Polygon defineKillCollider() {

        float[] polygonCoordinates = {0.6f, GameConfig.BEAR_SIZE - 1.3f,
                0.6f, GameConfig.BEAR_SIZE - 1f,
                GameConfig.BEAR_SIZE - 0.7f, GameConfig.BEAR_SIZE - 1f,
                GameConfig.BEAR_SIZE - 0.7f, GameConfig.BEAR_SIZE - 1.3f};

        Polygon polygon = new Polygon(polygonCoordinates);
        polygon.setOrigin(0, 0);

        polygon.setRotation(GameConfig.START_ANGLE - angleDegrees);

        return polygon;
    }

    @Override
    public Polygon defineAwarenessCollider() {

        float[] polygonCoordinatesClockwise = {1.35f, -2,
                1.35f, 3,
                6.35f, 3,
                6.35f, -2};

        float[] polygonCoordinatesAntiClockwise = {1, -2,
                1, 3,
                -4, 3,
                -4, -2};

        {
            Polygon polygon = new Polygon(clockWise ? polygonCoordinatesClockwise : polygonCoordinatesAntiClockwise);
            polygon.setOrigin(0, 0);

            float rotationModifier = clockWise ? 20f : 0f;

            polygon.setRotation(GameConfig.START_ANGLE - angleDegrees + rotationModifier);

            return polygon;

        }

    }

    @Override
    public Polygon getAwarenessCollider() {
        return this.awarenessCollider;
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
        polygonCollider.setRotation(GameConfig.START_ANGLE - angleDegrees - 20f);

        killCollider.setPosition(newX, newY);
        killCollider.setRotation(GameConfig.START_ANGLE - angleDegrees - 20f);

        float clockWiseMultiplier = this.clockWise ? -1 : 1;

        awarenessCollider.setPosition(newX, newY);
        awarenessCollider.setRotation(GameConfig.START_ANGLE - angleDegrees + 20 * clockWiseMultiplier);

    }

    public void reset() {
        setPosition(0, 0);
        currentBearState = GameConfig.ENEMY_SPAWNING_STATE;
        angleDegreeSpeed = GameConfig.BEAR_START_ANGULAR_SPEED;
        radius = GameConfig.PLANET_HALF_SIZE - GameConfig.MONSTER_SIZE;
        deathTimer = 0.5f;
        clockWise = false;
        awarenessCollider = defineAwarenessCollider();
        hitPoints = 1;
        bearDamagedTimer = 1f;
        stompLogicTimer = 2f;
        hasSpawnedSpikes = false;
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

    public boolean hasDamageAnimationStarted() {
        return hasDamageAnimationStarted;
    }

    public void setHasDamageAnimationStarted(boolean hasDamageAnimationStarted) {
        this.hasDamageAnimationStarted = hasDamageAnimationStarted;
    }

    public boolean hasSummonBeginAnimationStarted() {
        return hasSummonAnimationStarted;
    }

    public void setHasSummonBeginAnimationStarted(boolean hasStompAnimationStarted) {
        this.hasSummonAnimationStarted = hasStompAnimationStarted;
    }

    @Override
    public Polygon getKillCollider() {
        return this.killCollider;
    }

    public float getSummonLogicTimer() {
        return stompLogicTimer;
    }

    public void setSummonLogicTimer(float stompLogicTimer) {
        this.stompLogicTimer = stompLogicTimer;
    }

    public boolean isHasSpawnedSpikes() {
        return hasSpawnedSpikes;
    }

    public void setHasSpawnedSpikes(boolean hasSpawnedSpikes) {
        this.hasSpawnedSpikes = hasSpawnedSpikes;
    }
}
