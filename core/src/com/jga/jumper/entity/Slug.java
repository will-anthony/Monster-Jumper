package com.jga.jumper.entity;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Pool;
import com.jga.jumper.config.GameConfig;
import com.jga.jumper.entity.abstract_classes_and_interfaces.KillCollider;
import com.jga.jumper.entity.abstract_classes_and_interfaces.Shieldable;
import com.jga.jumper.entity.abstract_classes_and_interfaces.SmallEnemyBase;

public class Slug extends SmallEnemyBase implements Pool.Poolable, KillCollider, Shieldable {

    private float sensorAngleDegree;
    private float deathTimer;
    private Rectangle distanceSensor;
    private boolean shielded;

    private Polygon killCollider;

    public Slug() {
        super.angleDegreesSpeed = GameConfig.SLUG_START_ANGULAR_SPEED;
        super.setSize(GameConfig.SLUG_SIZE, GameConfig.SLUG_SIZE);
        super.setRadius(GameConfig.PLANET_HALF_SIZE - GameConfig.MONSTER_SIZE);
        super.changedDirection = false;
        super.moving = false;
        this.deathTimer = 0.5f;
        this.killCollider = defineKillCollider();
        this.shielded = false;

        int directionMultiplier = clockWise ? 1 : -1;
        distanceSensor = new Rectangle(x, y, directionMultiplier * 3f, 3f);

        assignShieldProperties();
    }

    @Override
    public Polygon definePolygonCollider() {

        float[] polygonCoordinates = {0, 0,
                0, GameConfig.SLUG_SIZE / 2 - 0.1f,
                GameConfig.SLUG_SIZE - 0.4f, GameConfig.SLUG_SIZE /2 - 0.1f,
                GameConfig.SLUG_SIZE - 0.4f, 0};

        Polygon polygon = new Polygon(polygonCoordinates);
        polygon.setOrigin(0, 0);

        return polygon;
    }

    @Override
    public Polygon defineKillCollider() {

        float[] polygonCoordinates = {0,GameConfig.SLUG_SIZE / 2 - 0.1f,
                0, GameConfig.SLUG_SIZE / 2 + 0.1f,
                GameConfig.SLUG_SIZE - 0.4f, GameConfig.SLUG_SIZE / 2 + 0.1f,
                GameConfig.SLUG_SIZE - 0.4f, GameConfig.SLUG_SIZE / 2 - 0.1f };

        Polygon polygon = new Polygon(polygonCoordinates);
        polygon.setOrigin(0, 0);

        return polygon;
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

        sensorAngleDegree = angleDegrees + 3f;

        super.setAngleDegree();

        float originX = GameConfig.WORLD_CENTER_X;
        float originY = GameConfig.WORLD_CENTER_Y;

        float newX = originX + MathUtils.cosDeg(-sensorAngleDegree) * (radius);
        float newY = originY + MathUtils.sinDeg(-sensorAngleDegree) * (radius);

        polygonCollider.setPosition(newX, newY);
        polygonCollider.setRotation(GameConfig.START_ANGLE - angleDegrees - 10f);

        killCollider.setPosition(newX, newY);
        killCollider.setRotation(GameConfig.START_ANGLE - angleDegrees - 10f);

        updateDistanceSensor(super.x, super.y);
    }

    @Override
    public void reset() {
        setPosition(0, 0);
        currentState = GameConfig.ENEMY_SPAWNING_STATE;
        radius = GameConfig.PLANET_HALF_SIZE - GameConfig.MONSTER_SIZE;
        deathTimer = 0.5f;
        this.shielded = false;
        super.clockWise = MathUtils.randomBoolean();
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
        super.shieldOrbitRadius = GameConfig.PLANET_HALF_SIZE - 0.62f;
        super.shieldAngleDegrees = this.angleDegrees;
        super.shieldClockWiseOffset = -7f;
        super.shieldAntiClockWiseOffset = -7f;
    }

    public Rectangle getDistanceSensor() {
        return distanceSensor;
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

    private void updateDistanceSensor(float x, float y) {
        distanceSensor.setPosition(x, y);
    }

    @Override
    public Polygon getKillCollider() {
        return killCollider;
    }

    public void setAngleDegreesSpeed(float angleDegreesSpeed) {
        this.angleDegreesSpeed = angleDegreesSpeed;
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
