package com.jga.jumper.entity;

import com.badlogic.gdx.math.Circle;

public abstract class EntityBase {

    // == attributes ==
    protected float x;
    protected float y;

    protected float width = 1;
    protected float height = 1;

    protected float angleDegree;

    protected Circle bounds;

    // == constructors ==
    public EntityBase() {
        bounds = new Circle(x,y, 0.5f);
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

    public void setX(float x) {
        this.x = x;
        updateBounds();
    }

    public void setY(float y) {
        this.y = y;
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

    public float getAngleDegree() {
        return angleDegree;
    }

    // == private methods ==
    public void updateBounds() {
        bounds.setPosition(x, y);
    }
}
