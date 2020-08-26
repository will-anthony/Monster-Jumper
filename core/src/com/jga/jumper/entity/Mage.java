package com.jga.jumper.entity;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.utils.Pool;
import com.jga.jumper.config.GameConfig;
import com.jga.jumper.entity.abstract_classes_and_interfaces.AwarenessCollider;
import com.jga.jumper.entity.abstract_classes_and_interfaces.KillCollider;
import com.jga.jumper.entity.abstract_classes_and_interfaces.SmallEnemyBase;

public class Mage extends SmallEnemyBase implements Pool.Poolable, KillCollider, AwarenessCollider {

    private int currentMageState;

    private float mageAttackTimer;
    private float deathTimer;

    private boolean hasCastShield;
    private boolean castingShield;
    private boolean shielded;
    private SmallEnemyBase enemyBeingShielded;

    private boolean hasDamageAnimationStarted;

    private Polygon killCollider;
    private Polygon awarenessCollider;

    public Mage() {

        super.angleDegreesSpeed = GameConfig.MAGE_START_ANGULAR_SPEED;
        super.setSize(GameConfig.MAGE_SIZE, GameConfig.MAGE_SIZE);
        super.setRadius(GameConfig.PLANET_HALF_SIZE - GameConfig.MAGE_SIZE);
        super.changedDirection = false;
        super.moving = false;

        this.deathTimer = 0.5f;
        this.killCollider = defineKillCollider();
        this.awarenessCollider = defineAwarenessCollider();
        this.mageAttackTimer = GameConfig.MAGE_ATTACK_TIMER;
        this.shielded = false;
        this.castingShield = false;

        assignShieldProperties();
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

        polygon.setRotation(GameConfig.START_ANGLE - 100);

        return polygon;
    }

    @Override
    public boolean isShielded() {
        return this.shielded;
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
    public void assignShieldProperties() {
        super.shieldPolygonColliderCoordinates = new float[]{0.42f, 0.8f,
                0.3f, 1.3f,
                1.65f, 1.3f,
                1.53f, 0.8f};

        super.shieldKillColliderCoordinates = new float[]{0.3f, 1.3f,
                0.38f, 1.5f,
                0.55f, 1.8f,
                0.7f, 1.9f,
                1.15f, 1.9f,
                1.3f, 1.8f,
                1.57f, 1.5f,
                1.65f, 1.3f
        };

        super.shieldSize = 2.2f;
        super.shieldOrbitRadius = GameConfig.PLANET_HALF_SIZE - 0.5f;
        super.shieldAngleDegrees = this.angleDegrees;
        super.shieldClockWiseOffset = -3.2f;
        super.shieldAntiClockWiseOffset = -4f;
    }

    @Override
    public void setAngleDegree() {

        float boundsAngleDegree = angleDegrees + 7f;

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
        super.moving = false;

        this.currentMageState = GameConfig.ENEMY_SPAWNING_STATE;
        this.deathTimer = 0.5f;
        this.awarenessCollider = defineAwarenessCollider();
        this.shielded = false;
        this.mageAttackTimer = GameConfig.MAGE_ATTACK_TIMER;
        this.castingShield = false;
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

    public int getCurrentMageState() {
        return currentMageState;
    }

    public void setCurrentMageState(int currentMageState) {
        this.currentMageState = currentMageState;
    }

    public boolean isHasDamageAnimationStarted() {
        return hasDamageAnimationStarted;
    }

    public void setHasDamageAnimationStarted(boolean hasDamageAnimationStarted) {
        this.hasDamageAnimationStarted = hasDamageAnimationStarted;
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

}
