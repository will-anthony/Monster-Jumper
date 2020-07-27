package com.jga.jumper.entity.abstract_classes_and_interfaces;

public abstract class SmallEnemyBase extends EnemyBase implements Shieldable {

    // properties passed to shield if slug becomes shielded
    protected float[] shieldPolygonColliderCoordinates;
    protected float[] shieldKillColliderCoordinates;
    protected float shieldSize;
    protected float shieldOrbitRadius;
    protected float shieldAngleDegrees;
    protected float shieldClockWiseOffset;
    protected float shieldAntiClockWiseOffset;
    protected boolean moving;
    protected boolean changedDirection;

    protected int currentState;

    public float[] getShieldPolygonColliderCoordinates() {
        return shieldPolygonColliderCoordinates;
    }

    public float[] getShieldKillColliderCoordinates() {
        return shieldKillColliderCoordinates;
    }

    public float getShieldSize() {
        return shieldSize;
    }

    public void setShieldSize(float shieldSize) {
        this.shieldSize = shieldSize;
    }

    public float getShieldOrbitRadius() {
        return shieldOrbitRadius;
    }

    public void setShieldOrbitRadius(float shieldOrbitRadius) {
        this.shieldOrbitRadius = shieldOrbitRadius;
    }

    public float getShieldAngleDegrees() {
        return shieldAngleDegrees;
    }

    public void setShieldAngleDegrees(float shieldAngleDegrees) {
        this.shieldAngleDegrees = shieldAngleDegrees;
    }

    public int getCurrentState() {
        return currentState;
    }

    public void setCurrentState(int currentState) {
        this.currentState = currentState;
    }

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public float getShieldClockWiseOffset() {
        return shieldClockWiseOffset;
    }

    public void setShieldClockWiseOffset(float shieldClockWiseOffset) {
        this.shieldClockWiseOffset = shieldClockWiseOffset;
    }

    public float getShieldAntiClockWiseOffset() {
        return shieldAntiClockWiseOffset;
    }

    public void setShieldAntiClockWiseOffset(float shieldAntiClockWiseOffset) {
        this.shieldAntiClockWiseOffset = shieldAntiClockWiseOffset;
    }

    public boolean isChangedDirection() {
        return changedDirection;
    }

    public void setChangedDirection(boolean changedDirection) {
        this.changedDirection = changedDirection;
    }
}
