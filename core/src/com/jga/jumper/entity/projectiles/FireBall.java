package com.jga.jumper.entity.projectiles;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.utils.Pool;
import com.jga.jumper.config.GameConfig;
import com.jga.jumper.entity.Red;

public class FireBall extends ProjectileBase implements Pool.Poolable {

    private float angleDegreeSpeed;
    private int currentFireBallState;
    private float spawnTimer;
    private float deathTimer;
    private float lifeTimer;

    private Red redParent;

    public FireBall() {
        angleDegreeSpeed = GameConfig.FIRE_BALL_START_ANGULAR_SPEED;
        setSize(GameConfig.FIRE_BALL_SIZE, GameConfig.FIRE_BALL_SIZE);
        spawnTimer = 1f;
        lifeTimer = 3f;
        deathTimer = 1f;
        super.setRadius(GameConfig.PLANET_HALF_SIZE + 0.25f);

    }

    @Override
    public Polygon definePolygonCollider() {

        float[] polygonCoordinates = {0, 0.1f,
                0, GameConfig.FIRE_BALL_SIZE,
                GameConfig.FIRE_BALL_SIZE - 0.1f, GameConfig.FIRE_BALL_SIZE,
                GameConfig.FIRE_BALL_SIZE - 0.1f, 0.1f};

        Polygon polygon = new Polygon(polygonCoordinates);
        polygon.setOrigin(0, 0);

        return polygon;

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

        super.setAngleDegree();

        float originX = GameConfig.WORLD_CENTER_X;
        float originY = GameConfig.WORLD_CENTER_Y;

        float newX = originX + MathUtils.cosDeg(-angleDegrees) * (radius);
        float newY = originY + MathUtils.sinDeg(-angleDegrees) * (radius);

        polygonCollider.setPosition(newX, newY);
        polygonCollider.setRotation(GameConfig.START_ANGLE - angleDegrees - 10f);

    }

    public void reset() {
        setPosition(0, 0);
        currentFireBallState = GameConfig.PROJECTILE_SPAWNING_STATE;
        radius = GameConfig.PLANET_HALF_SIZE + 0.3f;
        spawnTimer = 1f;
        lifeTimer = 3f;
        deathTimer = 1f;
    }

    public float getSpawnTimer() {
        return spawnTimer;
    }

    public void setSpawnTimer(float spawnTimer) {
        this.spawnTimer = spawnTimer;
    }

    public void setRadius(float radius) {
        this.radius = radius;
        setAngleDegree();
    }

    public int getCurrentFireBallState() {
        return currentFireBallState;
    }

    public void setCurrentFireBallState(int currentFireBallState) {
        this.currentFireBallState = currentFireBallState;
    }

    public float getDeathTimer() {
        return deathTimer;
    }

    public void setDeathTimer(float deathTimer) {
        this.deathTimer = deathTimer;
    }

    public float getLifeTimer() {
        return lifeTimer;
    }

    public void setLifeTimer(float lifeTimer) {
        this.lifeTimer = lifeTimer;
    }

    public Red getRedParent() {
        return redParent;
    }

    public void setRedParent(Red redParent) {
        this.redParent = redParent;
    }
}
