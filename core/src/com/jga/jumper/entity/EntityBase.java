package com.jga.jumper.entity;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;

public abstract class EntityBase {

    // == attributes ==
    protected float x;
    protected float y;

    protected float width = 1;
    protected float height = 1;

    protected float angleDegrees;
    private float animationTime;

    protected boolean hasIdleAnimationStarted;
    protected boolean hasWalkAnimationStarted;
    protected boolean hasDeadAnimationStarted;

    protected Circle bounds;

    // == constructors ==
    public EntityBase() {
        bounds = new Circle(x,y, 0.5f);
        animationTime = 0;
    }

    // == public methods ==
    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
        updateBounds();
    }

    public void setSize(float width, float height) {
        this.width = width;
        this.height = height;
        updateBounds();
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

    public Circle getBounds() {
        return bounds;
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

    public boolean hasIdleAnimationStarted() {
        return hasIdleAnimationStarted;
    }

    public void setHasIdleAnimationStarted(boolean hasIdleAnimationStarted) {
        this.hasIdleAnimationStarted = hasIdleAnimationStarted;
    }

    public boolean hasWalkAnimationStarted() {
        return hasWalkAnimationStarted;
    }

    public void setHasWalkAnimationStarted(boolean hasWalkAnimationStarted) {
        this.hasWalkAnimationStarted = hasWalkAnimationStarted;
    }

    public boolean hasDeadAnimationStarted() {
        return hasDeadAnimationStarted;
    }

    public void setHasDeadAnimationStarted(boolean hasDeadAnimationStarted) {
        this.hasDeadAnimationStarted = hasDeadAnimationStarted;
    }

    public abstract void update(float delta);

    // == private methods ==
    public void updateBounds() {
        bounds.setPosition(x, y);
    }
}
