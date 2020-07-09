package com.jga.jumper.entity;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Pool;
import com.jga.jumper.config.GameConfig;

import java.util.Random;

public class Mage extends EnemyBase implements Pool.Poolable, KillCollider {

    private float angleDegreeSpeed;
    private float boundsAngleDegree;

    private int currentMageState;

    private float deathTimer;
    private int hitPoints;

    private float mageWalkTimer;
    private float mageAttackTimer;
    private boolean hasMageThrownFireBall;

    private Random random;

    private float mageDamagedTimer;
    private boolean hasDamageAnimationStarted;

    private Polygon killCollider;

    public Mage() {
        angleDegreeSpeed = GameConfig.MAGE_START_ANGULAR_SPEED;
        super.setSize(GameConfig.MAGE_SIZE, GameConfig.MAGE_SIZE);
        super.setRadius(GameConfig.PLANET_HALF_SIZE - GameConfig.MAGE_SIZE);
        deathTimer = 0.5f;
        hitPoints = 1;
        mageDamagedTimer = 1f;
        killCollider = defineKillCollider();

        random = new Random();
        mageWalkTimer = random.nextInt(3) + 1;
        mageAttackTimer = 1f;
    }

    @Override
    protected Polygon definePolygonCollider() {

        Polygon polygon;

        float[] polygonCoordinates = {0, 0,
                0, GameConfig.MAGE_SIZE - 0.6f,
                GameConfig.MAGE_SIZE - 0.7f, GameConfig.MAGE_SIZE - 0.6f,
                GameConfig.MAGE_SIZE - 0.7f, 0};

        polygon = new Polygon(polygonCoordinates);
        polygon.setOrigin(0, 0);

        return polygon;
    }

    @Override
    public Polygon defineKillCollider() {

        Polygon polygon;

        float[] polygonCoordinates = {0, GameConfig.MAGE_SIZE - 0.6f,
                0, GameConfig.MAGE_SIZE - 0.50f,
                GameConfig.MAGE_SIZE - 0.7f, GameConfig.MAGE_SIZE - 0.50f,
                GameConfig.MAGE_SIZE - 0.7f, GameConfig.MAGE_SIZE - 0.6f};

        polygon = new Polygon(polygonCoordinates);
        polygon.setOrigin(0, 0);

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
        setAngleDegree();

    }

    public double generateRandomWalkStateTime() {
        return random.nextInt(4) + 2;
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
        polygonCollider.setRotation(GameConfig.START_ANGLE - angleDegrees - GameConfig.MAGE_POLYGON_ROTATION_OFFSET);

        killCollider.setPosition(boundsX, boundsY);
        killCollider.setRotation(GameConfig.START_ANGLE - angleDegrees - GameConfig.MAGE_POLYGON_ROTATION_OFFSET);

    }

    public void reset() {
        setPosition(0, 0);
        currentMageState = GameConfig.ENEMY_SPAWNING_STATE;
        radius = GameConfig.PLANET_HALF_SIZE - GameConfig.MAGE_SIZE;

        deathTimer = 0.5f;
        super.clockWise = MathUtils.randomBoolean();
        hitPoints = 1;
        mageDamagedTimer = 1f;
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

    public int getCurrentMageState() {
        return currentMageState;
    }

    public void setCurrentMageState(int currentMageState) {
        this.currentMageState = currentMageState;
    }

    public float getMageDamagedTimer() {
        return mageDamagedTimer;
    }

    public void setMageDamagedTimer(float mageDamagedTimer) {
        this.mageDamagedTimer = mageDamagedTimer;
    }

    public boolean isHasDamageAnimationStarted() {
        return hasDamageAnimationStarted;
    }

    public void setHasDamageAnimationStarted(boolean hasDamageAnimationStarted) {
        this.hasDamageAnimationStarted = hasDamageAnimationStarted;
    }

    public float getMageWalkTimer() {
        return mageWalkTimer;
    }

    public void setMageWalkTimer(float mageWalkTimer) {
        this.mageWalkTimer = mageWalkTimer;
    }

    public float getMageAttackTimer() {
        return mageAttackTimer;
    }

    public void setMageAttackTimer(float mageAttackTimer) {
        this.mageAttackTimer = mageAttackTimer;
    }

    public boolean isHasMageThrownFireBall() {
        return hasMageThrownFireBall;
    }

    public void setHasMageThrownFireBall(boolean hasMageThrownFireBall) {
        this.hasMageThrownFireBall = hasMageThrownFireBall;
    }
}
