package com.jga.jumper.entity;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Pool;
import com.jga.jumper.config.GameConfig;

public class Slug extends EnemyBase implements Pool.Poolable, KillCollider {

    private float angleDegreeSpeed;
    private float sensorAngleDegree;
    private int currentSlugState;
    private float deathTimer;
    private Rectangle distanceSensor;

    private Polygon killCollider;


    public Slug() {
        angleDegreeSpeed = GameConfig.SLUG_START_ANGULAR_SPEED;
        setSize(GameConfig.SLUG_SIZE, GameConfig.SLUG_SIZE);
        super.setRadius(GameConfig.PLANET_HALF_SIZE - GameConfig.MONSTER_SIZE);
        deathTimer = 0.5f;
        this.killCollider = defineKillCollider();

        int directionMultiplier = clockWise ? 1 : -1;
        distanceSensor = new Rectangle(x, y, directionMultiplier * 3f, 3f);
    }

    @Override
    protected Polygon definePolygonCollider() {

        float[] polygonCoordinates = {0, 0,
                0, GameConfig.SLUG_SIZE / 2,
                GameConfig.SLUG_SIZE - 0.4f, GameConfig.SLUG_SIZE /2,
                GameConfig.SLUG_SIZE - 0.4f, 0};

        Polygon polygon = new Polygon(polygonCoordinates);
        polygon.setOrigin(0, 0);

        return polygon;

    }


    @Override
    public Polygon defineKillCollider() {

        float[] polygonCoordinates = {0,GameConfig.SLUG_SIZE / 2,
                0, GameConfig.SLUG_SIZE / 2 + 0.1f,
                GameConfig.SLUG_SIZE - 0.4f, GameConfig.SLUG_SIZE / 2 + 0.1f,
                GameConfig.SLUG_SIZE - 0.4f, GameConfig.SLUG_SIZE / 2 };

        Polygon polygon = new Polygon(polygonCoordinates);
        polygon.setOrigin(0, 0);

        return polygon;

    }


    public void update(float delta) {
    }

    public void move(float delta) {

        int directionalMultiplier = 1;

        if (clockWise) {
            directionalMultiplier = -1;
        }
        angleDegrees -= (angleDegreeSpeed * directionalMultiplier) * delta;
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
        currentSlugState = GameConfig.ENEMY_SPAWNING_STATE;
        radius = GameConfig.PLANET_HALF_SIZE - GameConfig.MONSTER_SIZE;
        deathTimer = 0.5f;
        super.clockWise = MathUtils.randomBoolean();
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

    public int getCurrentSlugState() {
        return currentSlugState;
    }

    public void setCurrentSlugState(int currentSlugState) {
        this.currentSlugState = currentSlugState;
    }

    private void updateDistanceSensor(float x, float y) {
        distanceSensor.setPosition(x, y);
    }

    @Override
    public Polygon getKillCollider() {
        return killCollider;
    }
}
