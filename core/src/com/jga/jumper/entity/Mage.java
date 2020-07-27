package com.jga.jumper.entity;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.utils.Pool;
import com.jga.jumper.config.GameConfig;
import com.jga.jumper.entity.abstract_classes_and_interfaces.AwarenessCollider;
import com.jga.jumper.entity.abstract_classes_and_interfaces.KillCollider;
import com.jga.jumper.entity.abstract_classes_and_interfaces.SmallEnemyBase;

import java.util.Random;

public class Mage extends SmallEnemyBase implements Pool.Poolable, KillCollider, AwarenessCollider {

    private float angleDegreeSpeed;
    private float boundsAngleDegree;

    private int currentMageState;

    private float deathTimer;
    private int hitPoints;

    private float mageWalkTimer;
    private float mageAttackTimer;
    private boolean hasCastShield;

    private Random random;

    private float mageDamagedTimer;
    private boolean hasDamageAnimationStarted;

    private Polygon killCollider;
    private Polygon awarenessCollider;

    private boolean castingShield;

    private boolean shielded;
    private SmallEnemyBase enemyBeingShielded;

    public Mage() {
        angleDegreeSpeed = GameConfig.MAGE_START_ANGULAR_SPEED;
        super.setSize(GameConfig.MAGE_SIZE, GameConfig.MAGE_SIZE);
        super.setRadius(GameConfig.PLANET_HALF_SIZE - GameConfig.MAGE_SIZE);
        deathTimer = 0.5f;
        hitPoints = 1;
        mageDamagedTimer = 1f;
        killCollider = defineKillCollider();
        awarenessCollider = defineAwarenessCollider();

        random = new Random();
        mageWalkTimer = random.nextInt(3) + 1;
        mageAttackTimer = 2f;

        this.shielded = false;
        this.castingShield = false;
    }

    @Override
    public Polygon definePolygonCollider() {

        Polygon polygon;

        float[] polygonCoordinates = {0, 0,
                0, GameConfig.MAGE_SIZE - 0.7f,
                GameConfig.MAGE_SIZE - 0.7f, GameConfig.MAGE_SIZE - 0.7f,
                GameConfig.MAGE_SIZE - 0.7f, 0};

        polygon = new Polygon(polygonCoordinates);
        polygon.setOrigin(0, 0);

        return polygon;
    }

    @Override
    public Polygon defineKillCollider() {

        Polygon polygon;

        float[] polygonCoordinates = {0, GameConfig.MAGE_SIZE - 0.7f,
                0, GameConfig.MAGE_SIZE - 0.50f,
                GameConfig.MAGE_SIZE - 0.7f, GameConfig.MAGE_SIZE - 0.50f,
                GameConfig.MAGE_SIZE - 0.7f, GameConfig.MAGE_SIZE - 0.7f};

        polygon = new Polygon(polygonCoordinates);
        polygon.setOrigin(0, 0);

        return polygon;
    }

    @Override
    public Polygon defineAwarenessCollider() {
        float[] polygonCoordinatesClockwise = {1.35f, -2,
                1.35f, 3,
                8.35f, 3,
                8.35f, -2};

        float[] polygonCoordinatesAntiClockwise = {0, -2,
                0, 3,
                -6, 3,
                -6, -2};


        Polygon polygon = new Polygon(clockWise ? polygonCoordinatesClockwise : polygonCoordinatesAntiClockwise);
        polygon.setOrigin(0, 0);

//        float rotationModifier = clockWise ? 40f : -40f;

        polygon.setRotation(GameConfig.START_ANGLE -100);

        return polygon;

    }

    @Override
    public boolean isShielded() {
        return this.shielded;
    }

    @Override
    public void setShielded(boolean shielded) {
        this.shielded = shielded;
    }

    @Override
    public Polygon getAwarenessCollider() {
        return this.awarenessCollider;
    }

    @Override
    public Polygon getKillCollider() {
        return this.killCollider;
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
    public void assignShieldProperties() {

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

        awarenessCollider.setPosition(boundsX, boundsY);


        float rotationOffset = clockWise ? -60 : 40;
        awarenessCollider.setRotation(GameConfig.START_ANGLE - angleDegrees + rotationOffset);

    }

    public void reset() {
        super.setPosition(0, 0);
        super.radius = GameConfig.PLANET_HALF_SIZE - GameConfig.MAGE_SIZE;
        super.clockWise = MathUtils.randomBoolean();

        this.currentMageState = GameConfig.ENEMY_SPAWNING_STATE;
        this.deathTimer = 0.5f;
        this.awarenessCollider = defineAwarenessCollider();
        this.hitPoints = 1;
        this.mageDamagedTimer = 1f;
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

    public boolean isHasCastShield() {
        return hasCastShield;
    }

    public void setHasCastShield(boolean hasCastShield) {
        this.hasCastShield = hasCastShield;
    }

    public SmallEnemyBase getEnemyBeingShielded() {
        return enemyBeingShielded;
    }

    public void setEnemyBeingShielded(SmallEnemyBase enemyBeingShielded) {
        this.enemyBeingShielded = enemyBeingShielded;
    }

    public boolean isCastingShield() {
        return castingShield;
    }

    public void setCastingShield(boolean castingShield) {
        this.castingShield = castingShield;
    }
}
