package com.jga.jumper.entity;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.jga.jumper.config.GameConfig;
import com.jga.jumper.entity.abstract_classes_and_interfaces.KillCollider;
import com.jga.jumper.entity.abstract_classes_and_interfaces.SmallEnemyBase;

public class Shield implements KillCollider {

    protected float x;
    protected float y;

    protected float width;
    protected float height;

    protected float angleDegrees;
    private float clockWiseOffset;
    private float antiClockWiseOffset;
    protected float angleDegreesSpeed;
    protected float animationTime;

    protected boolean clockWise;
    protected float orbitalRadius;
    private boolean increasingAlpha;

    protected Polygon polygonCollider;
    private Polygon killCollider;

    private int currentState;
    private float shieldAlphaPercentage;

    private SmallEnemyBase shieldParent;

    public Shield(SmallEnemyBase shieldParent) {

        this.shieldParent = shieldParent;

        this.setSize(shieldParent.getShieldSize(), shieldParent.getShieldSize());
        this.polygonCollider = definePolygonCollider(shieldParent.getShieldPolygonColliderCoordinates());
        this.killCollider = defineKillCollider();
        this.clockWiseOffset = shieldParent.getShieldClockWiseOffset();
        this.antiClockWiseOffset = shieldParent.getShieldAntiClockWiseOffset();
        this.angleDegrees = shieldParent.getAngleDegrees();
        this.increasingAlpha = false;

        this.angleDegreesSpeed = shieldParent.getAngleDegreesSpeed();
        this.clockWise = shieldParent.isClockWise();
        this.orbitalRadius = shieldParent.getShieldOrbitRadius();

        this.animationTime = 0;
        this.shieldAlphaPercentage = 0;

        setInitialDirectionOffset();
        setAngleDegree();
    }

    private void setInitialDirectionOffset() {
        angleDegrees += shieldParent.isClockWise() ? clockWiseOffset : antiClockWiseOffset;
    }

    public Polygon definePolygonCollider(float[] polygonCoordinates) {

        Polygon polygon = new Polygon(polygonCoordinates);
        polygon.setOrigin(0, 0);

        polygon.setRotation(GameConfig.START_ANGLE - angleDegrees - 20);

        return polygon;
    }

    public Polygon defineKillCollider() {

        float[] polygonCoordinates = shieldParent.getShieldKillColliderCoordinates();

        Polygon polygon = new Polygon(polygonCoordinates);
        polygon.setOrigin(0, 0);

        polygon.setRotation(GameConfig.START_ANGLE - angleDegrees - 20f);

        return polygon;
    }


    public void move(float delta) {

        int directionalMultiplier = 1;

        if (shieldParent.isClockWise()) {
            directionalMultiplier = -1;
        }

        angleDegrees -= (angleDegreesSpeed * directionalMultiplier) * delta;
        setAngleDegree();
    }

    public void checkForDirectionChange() {

        if (shieldParent.isChangedDirection()) {
            angleDegrees += shieldParent.isClockWise() ?
                    (-antiClockWiseOffset + clockWiseOffset + 2):
                    (-clockWiseOffset + antiClockWiseOffset - 2);
        }
    }


    public void setAngleDegree() {

        float originX = GameConfig.WORLD_CENTER_X;
        float originY = GameConfig.WORLD_CENTER_Y;

        float newX = originX + MathUtils.cosDeg(-angleDegrees) * (orbitalRadius);
        float newY = originY + MathUtils.sinDeg(-angleDegrees) * (orbitalRadius);

        setPosition(newX, newY);

        polygonCollider.setPosition(newX, newY);
        polygonCollider.setRotation(GameConfig.START_ANGLE - angleDegrees - 20f);

        killCollider.setPosition(newX, newY);
        killCollider.setRotation(GameConfig.START_ANGLE - angleDegrees - 20f);

    }

    public int getCurrentState() {
        return currentState;
    }

    public void setCurrentState(int currentState) {
        this.currentState = currentState;
    }

    @Override
    public Polygon getKillCollider() {
        return this.killCollider;
    }

    public float getShieldAlphaPercentage() {
        return shieldAlphaPercentage;
    }

    public void setShieldAlphaPercentage(float shieldAlphaPercentage) {
        this.shieldAlphaPercentage = shieldAlphaPercentage;
    }

    public SmallEnemyBase getShieldParent() {
        return shieldParent;
    }

    public void setStartingPosition(float value) {
        angleDegrees = value % 360;
        setAngleDegree();
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void setSize(float width, float height) {
        this.width = width;
        this.height = height;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public Polygon getPolygonCollider() {
        return polygonCollider;
    }

    public float getAngleDegrees() {
        return angleDegrees;
    }

    public float getRotation(float offset) {
        return angleDegrees + offset;
    }

    public float getAnimationTime() {
        return animationTime;
    }

    public void setAnimationTime(float animationTime) {
        this.animationTime = animationTime;
    }

    public boolean isClockWise() {
        return clockWise;
    }

    public void setPolygonCollider(Polygon polygonCollider) {
        this.polygonCollider = polygonCollider;
    }

    public boolean isAlphaIncreasing() {
        return increasingAlpha;
    }

    public void setAlphaIncreasing(boolean increasingAlpha) {
        this.increasingAlpha = increasingAlpha;
    }
}


