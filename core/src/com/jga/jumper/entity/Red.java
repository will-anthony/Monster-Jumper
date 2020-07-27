package com.jga.jumper.entity;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.utils.Pool;
import com.jga.jumper.config.GameConfig;
import com.jga.jumper.entity.abstract_classes_and_interfaces.SmallEnemyBase;

import java.util.Random;

public class Red extends SmallEnemyBase implements Pool.Poolable {

    private float boundsAngleDegree;
    private float angleDegreeSpeed;

    private int currentRedState;

    private float deathTimer;
    private int hitPoints;

    private float redWalkTimer;
    private float redAttackTimer;
    private boolean hasRedThrownFireBall;

    private Random random;

    private float redDamagedTimer;
    private boolean hasDamageAnimationStarted;

    private boolean shielded;

    public Red() {
        angleDegreeSpeed = GameConfig.RED_START_ANGULAR_SPEED;
        super.setSize(GameConfig.RED_SIZE, GameConfig.RED_SIZE);
        super.setRadius(GameConfig.PLANET_HALF_SIZE - GameConfig.RED_SIZE);
        super.clockWise = MathUtils.randomBoolean();
        this.deathTimer = 0.5f;
        this.hitPoints = 1;
        this.redDamagedTimer = 1f;

        this.random = new Random();
        this.redWalkTimer = MathUtils.random(1,5);
        this.redAttackTimer = 1f;
        this.shielded = false;
    }

    @Override
    public Polygon definePolygonCollider() {

        Polygon polygon;

        float[] polygonCoordinates = {0, 0,
                0, GameConfig.RED_SIZE - 0.5f,
                GameConfig.RED_SIZE - 0.7f, GameConfig.RED_SIZE - 0.5f,
                GameConfig.RED_SIZE - 0.7f, 0};

        polygon = new Polygon(polygonCoordinates);
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

    @Override
    public void assignShieldProperties() {

    }

    @Override
    public boolean isShielded() {
        return this.shielded;
    }

    @Override
    public void setShielded(boolean shielded) {
        this.shielded = shielded;
    }

    public double generateRandomWalkStateTime() {
        return MathUtils.random(2,5);
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

        float boundsX = originX + MathUtils.cosDeg(-boundsAngleDegree) * (radius);
        float boundsY = originY + MathUtils.sinDeg(-boundsAngleDegree) * (radius);

        polygonCollider.setPosition(boundsX, boundsY);
        polygonCollider.setRotation(GameConfig.START_ANGLE - angleDegrees - GameConfig.RED_POLYGON_ROTATION_OFFSET);

    }

    public void reset() {
        setPosition(0, 0);
        currentRedState = GameConfig.ENEMY_SPAWNING_STATE;
        radius = GameConfig.PLANET_HALF_SIZE - GameConfig.MAGE_SIZE;

        deathTimer = 0.5f;
        super.clockWise = MathUtils.randomBoolean();
        hitPoints = 1;
        redDamagedTimer = 1f;
        this.shielded = false;
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

    public int getCurrentRedState() {
        return currentRedState;
    }

    public void setCurrentRedState(int currentRedState) {
        this.currentRedState = currentRedState;
    }

    public float getRedDamagedTimer() {
        return redDamagedTimer;
    }

    public void setRedDamagedTimer(float redDamagedTimer) {
        this.redDamagedTimer = redDamagedTimer;
    }

    public boolean isHasDamageAnimationStarted() {
        return hasDamageAnimationStarted;
    }

    public void setHasDamageAnimationStarted(boolean hasDamageAnimationStarted) {
        this.hasDamageAnimationStarted = hasDamageAnimationStarted;
    }

    public float getRedWalkTimer() {
        return redWalkTimer;
    }

    public void setRedWalkTimer(float redWalkTimer) {
        this.redWalkTimer = redWalkTimer;
    }

    public float getRedAttackTimer() {
        return redAttackTimer;
    }

    public void setRedAttackTimer(float redAttackTimer) {
        this.redAttackTimer = redAttackTimer;
    }

    public boolean isHasRedThrownFireBall() {
        return hasRedThrownFireBall;
    }

    public void setHasRedThrownFireBall(boolean hasRedThrownFireBall) {
        this.hasRedThrownFireBall = hasRedThrownFireBall;
    }


}
