package com.jga.jumper.entity;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.utils.Pool;
import com.jga.jumper.config.GameConfig;
import com.jga.jumper.entity.abstract_classes_and_interfaces.EntityBase;

public class Coin<T extends com.jga.jumper.entity.abstract_classes_and_interfaces.EntityBase> extends EntityBase implements Pool.Poolable {

    private int coinScore;
    private float gravity;
    private float velocityX;
    private float velocityY;
    private float accelerationY;
    private float accelerationX;
    private int coinState;
    private boolean hasCoinSpinningAnimationStarted;
    private boolean hasCoinCollectedAnimationStarted;
    private float coinCollectedTimer;
    private boolean coinCollected;

    // == constructors ==
    public Coin() {
        setSize(GameConfig.COIN_SIZE, GameConfig.COIN_SIZE);
        this.coinScore = GameConfig.COIN_SCORE;
        gravity = GameConfig.MONSTER_GRAVITY;
        coinState = GameConfig.COIN_RISING;
        coinCollectedTimer = 0.36f;
    }

    @Override
    public Polygon definePolygonCollider() {

        float[] polygonCoordinates = {0.2f, 0.2f,
                0.2f, GameConfig.COIN_SIZE - 0.2f,
                GameConfig.COIN_SIZE - 0.2f, GameConfig.COIN_SIZE - 0.2f,
                GameConfig.COIN_SIZE - 0.2f, 0.2f};

        Polygon polygon = new Polygon(polygonCoordinates);
        polygon.setOrigin(0, 0);

        return polygon;

    }

    public void update(float delta) {

        coinMovingOnXAxis(delta);

        switch (coinState) {
            case GameConfig.COIN_RISING:
                coinRising(delta);
                if (accelerationY <= 0) {
                    coinState = GameConfig.COIN_FALLING;
                }
                break;

            case GameConfig.COIN_FALLING:
                coinFalling(delta);
                if (velocityY <= 0) {
                    velocityY = 0;
                    coinState = GameConfig.COIN_IDLE;
                }
                break;

            case GameConfig.COIN_IDLE:
                break;

            case GameConfig.COIN_COLLECTED:
                coinCollectedLogic(delta);
                break;

            case GameConfig.COIN_DEAD:
                break;

        }

        setAngleDegree();

    }

    public void coinCollectedLogic(float delta) {
        coinCollectedTimer -= delta;

        if(coinCollectedTimer <= 0) {
            coinState = GameConfig.COIN_DEAD;
        }
    }


    public void setStartingPosition(T entity) {

        angleDegrees = entity.getAngleDegrees();
        setAngleDegree();

    }

    public void coinRising(float delta) {

        velocityY += accelerationY * delta;
        accelerationY -= gravity;

    }

    public void coinFalling(float delta) {
        velocityY += accelerationY * delta;
        accelerationY -= gravity;
    }

    public void coinMovingOnXAxis(float delta) {

        if (radius + velocityY > GameConfig.PLANET_HALF_SIZE) {
            velocityX += accelerationX * delta;
        }
    }

    public void setAngleDegree() {

        float radius = GameConfig.PLANET_HALF_SIZE + velocityY;
        float originX = GameConfig.WORLD_CENTER_X;
        float originY = GameConfig.WORLD_CENTER_Y;

        float newX = originX + MathUtils.cosDeg(-angleDegrees + velocityX) * (radius - 0.25f);
        float newY = originY + MathUtils.sinDeg(-angleDegrees + velocityX) * (radius - 0.25f);

        polygonCollider.setPosition(newX, newY);
        polygonCollider.setRotation(GameConfig.START_ANGLE - angleDegrees + velocityX);
        setPosition(newX, newY);

    }

    @Override
    public void reset() {
        gravity = GameConfig.MONSTER_GRAVITY;
        velocityX = 0;
        velocityY = 0;
        accelerationY = 0;
        accelerationX = 0;
        coinState = GameConfig.COIN_RISING;
        coinCollectedTimer = 0.36f;
        coinCollected = false;
    }

    public int getCoinScore() {
        return coinScore;
    }

    public void setCoinScore(int coinScore) {
        this.coinScore = coinScore;
    }

    public float getGravity() {
        return gravity;
    }

    public void setGravity(float gravity) {
        this.gravity = gravity;
    }

    public float getVelocityX() {
        return velocityX;
    }

    public void setVelocityX(float velocityX) {
        this.velocityX = velocityX;
    }

    public float getVelocityY() {
        return velocityY;
    }

    public void setVelocityY(float velocityY) {
        this.velocityY = velocityY;
    }

    public float getAccelerationY() {
        return accelerationY;
    }

    public void setAccelerationY(float acceleration) {
        this.accelerationY = acceleration;
    }

    public float getAccelerationX() {
        return accelerationX;
    }

    public void setAccelerationX(float accelerationX) {
        this.accelerationX = accelerationX;
    }

    public int getCoinState() {
        return coinState;
    }

    public void setCoinState(int coinState) {
        this.coinState = coinState;
    }

    public boolean hasCoinSpinningAnimationStarted() {
        return hasCoinSpinningAnimationStarted;
    }

    public void setHasCoinSpinningAnimationStarted(boolean hasCoinSpinningAnimationStarted) {
        this.hasCoinSpinningAnimationStarted = hasCoinSpinningAnimationStarted;
    }

    public boolean hasCoinCollectedAnimationStarted() {
        return hasCoinCollectedAnimationStarted;
    }

    public void setHasCoinCollectedAnimationStarted(boolean hasCoinCollectedAnimationStarted) {
        this.hasCoinCollectedAnimationStarted = hasCoinCollectedAnimationStarted;
    }

    public float getCoinCollectedTimer() {
        return coinCollectedTimer;
    }

    public void setCoinCollectedTimer(float coinCollectedTimer) {
        this.coinCollectedTimer = coinCollectedTimer;
    }

    public boolean isCoinCollected() {
        return coinCollected;
    }

    public void setCoinCollected(boolean coinCollected) {
        this.coinCollected = coinCollected;
    }
}
