package com.jga.jumper.entity;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.utils.Pool;
import com.jga.jumper.config.GameConfig;
import com.jga.jumper.entity.abstract_classes_and_interfaces.SmallEnemyBase;

public class Skull extends SmallEnemyBase implements Pool.Poolable {

    private float timeBetweenAttacks;
    private float timeInAttackState;
    private float spikeTrapSpawnDelay;
    private boolean trapSpawned;

    private boolean shielded;

    private float deathTimer;


    public Skull() {
        super.angleDegreesSpeed = GameConfig.SKULL_START_ANGULAR_SPEED;
        super.setSize(GameConfig.SKULL_SIZE, GameConfig.SKULL_SIZE);
        super.setRadius(GameConfig.PLANET_HALF_SIZE - GameConfig.SKULL_SIZE);
        this.deathTimer = 0.5f;
        this.timeBetweenAttacks = MathUtils.random(1.5f, 3f);
        this.timeInAttackState = 1f;
        this.spikeTrapSpawnDelay = 0.5f;
        this.shielded = false;
        super.changedDirection = false;

        assignShieldProperties();

    }

    @Override
    public Polygon definePolygonCollider() {

        float[] polygonCoordinates = {0.4f, 0,
                0.4f, 1,
                GameConfig.SKULL_SIZE - 0.2f, 1,
                GameConfig.SKULL_SIZE - 0.2f, 0};

        Polygon polygon = new Polygon(polygonCoordinates);
        polygon.setOrigin(0, 0);

        return polygon;

    }

    @Override
    public void assignShieldProperties() {
        super.shieldPolygonColliderCoordinates = new float[] {0.42f, 0.8f,
                0.3f, 1.3f,
                0.38f, 1.5f,
                1.57f, 1.5f,
                1.65f, 1.3f,
                1.53f, 0.8f};

        super.shieldKillColliderCoordinates = new float[] { 0.38f, 1.5f,
                0.55f, 1.8f,
                0.7f, 1.9f,
                1.15f, 1.9f,
                1.3f, 1.8f,
                1.57f, 1.5f,
        };

        super.shieldSize = 2.2f;
        super.shieldOrbitRadius = GameConfig.PLANET_HALF_SIZE - 0.5f;
        super.shieldAngleDegrees = this.angleDegrees;
        super.shieldClockWiseOffset = -10f;
        super.shieldAntiClockWiseOffset = -5f;
    }

    public void move(float delta) {


        int directionalMultiplier = 1;

        if (clockWise) {
            directionalMultiplier = -1;
        }
        angleDegrees -= (angleDegreesSpeed * directionalMultiplier) * delta;
        setAngleDegree();
    }

    public void setStartingPosition(float value) {
        angleDegrees = value % 360;
        setAngleDegree();
    }


    @Override
    public void setAngleDegree() {

        super.setAngleDegree();

        float originX = GameConfig.WORLD_CENTER_X;
        float originY = GameConfig.WORLD_CENTER_Y;

        float newX = originX + MathUtils.cosDeg(-angleDegrees) * (radius);
        float newY = originY + MathUtils.sinDeg(-angleDegrees) * (radius);

        polygonCollider.setPosition(newX, newY);
        polygonCollider.setRotation(GameConfig.START_ANGLE - angleDegrees - 10f);

    }

    @Override
    public void reset() {
        setPosition(0, 0);
        this.currentState = GameConfig.ENEMY_SPAWNING_STATE;
        this.radius = GameConfig.PLANET_HALF_SIZE - GameConfig.SKULL_SIZE;
        this.deathTimer = 0.5f;
        this.timeBetweenAttacks = MathUtils.random(1.5f,3f);
        this.timeInAttackState = 1f;
        this.trapSpawned = false;
        super.clockWise = MathUtils.randomBoolean();
        super.moving = false;
        super.changedDirection = false;
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

    public int getCurrentSkullState() {
        return currentState;
    }

    public void setCurrentSkullState(int currentSkullState) {
        this.currentState = currentSkullState;
    }

    public float getTimeBetweenAttacks() {
        return timeBetweenAttacks;
    }

    public void setTimeBetweenAttacks(float timeBetweenAttacks) {
        this.timeBetweenAttacks = timeBetweenAttacks;
    }

    public float getTimeInAttackState() {
        return timeInAttackState;
    }

    public void setTimeInAttackState(float timeInAttackState) {
        this.timeInAttackState = timeInAttackState;
    }

    public void setSpikeTrapSpawnDelay(float spikeTrapSpawnDelay) {
        this.spikeTrapSpawnDelay = spikeTrapSpawnDelay;
    }

    public float getSpikeTrapSpawnDelay() {
        return spikeTrapSpawnDelay;
    }

    public void setTrapSpawned(boolean trapSpawned) {
        this.trapSpawned = trapSpawned;
    }

    public boolean hasTrapSpawned() {
        return trapSpawned;
    }

    @Override
    public boolean isShielded() {
        return this.shielded;
    }

    @Override
    public void setShielded(boolean shielded) {
        this.shielded = shielded;
    }
}
