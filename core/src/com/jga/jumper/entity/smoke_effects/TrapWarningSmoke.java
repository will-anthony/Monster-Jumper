package com.jga.jumper.entity.smoke_effects;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.utils.Pool;
import com.jga.jumper.config.GameConfig;
import com.jga.jumper.entity.abstract_classes_and_interfaces.EntityBase;

public class TrapWarningSmoke extends EntityBase implements Pool.Poolable {

    private int currentState;
    private float spawnTimer;
    private float withdrawTimer;

    protected boolean hasSpawnAnimationStarted;
    protected boolean hasWithdrawAnimationStarted;

    public TrapWarningSmoke() {
        setRadius(GameConfig.PLANET_HALF_SIZE - 0.1f);
        setSize(1.5f, 1.5f);
        spawnTimer = 0f;
        withdrawTimer = 0.4f;

    }

    @Override
    public Polygon definePolygonCollider() {
        float[] polygonCoordinates = {0, 0,
                0, 1,
                1, 1,
                1, 0};

        Polygon polygon = new Polygon(polygonCoordinates);
        polygon.setOrigin(0, 0);

        polygon.setRotation(GameConfig.START_ANGLE - angleDegrees);

        return polygon;
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

    public int getCurrentState() {
        return currentState;
    }

    public void setCurrentState(int currentState) {
        this.currentState = currentState;
    }

    @Override
    public void reset() {
//        currentState = GameConfig.TRAP_WARNING_SMOKE_SPAWN_STATE;
        spawnTimer = 0f;
        withdrawTimer = 0.4f;
        animationTime = 0f;

    }

    @Override
    public void setRadius(float radius) {
        super.setRadius(radius);
        setAngleDegree();
    }

    public float getSpawnTimer() {
        return spawnTimer;
    }

    public void setSpawnTimer(float spawnTimer) {
        this.spawnTimer = spawnTimer;
    }

    public float getWithdrawTimer() {
        return withdrawTimer;
    }

    public void setWithdrawTimer(float withdrawTimer) {
        this.withdrawTimer = withdrawTimer;
    }

    public boolean hasSpawnAnimationStarted() {
        return hasSpawnAnimationStarted;
    }

    public void setHasSpawnAnimationStarted(boolean hasSpawnAnimationStarted) {
        this.hasSpawnAnimationStarted = hasSpawnAnimationStarted;
    }

    public boolean hasWithdrawAnimationStarted() {
        return hasWithdrawAnimationStarted;
    }

    public void setHasWithdrawAnimationStarted(boolean hasWithdrawAnimationStarted) {
        this.hasWithdrawAnimationStarted = hasWithdrawAnimationStarted;
    }
}
