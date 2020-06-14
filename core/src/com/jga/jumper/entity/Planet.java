package com.jga.jumper.entity;

import com.badlogic.gdx.math.Circle;
import com.jga.jumper.config.GameConfig;

public class Planet {

    private float x;
    private float y;

    private float width = 1f;
    private float height = 1f;

    private Circle bounds;

    // == constructors ==
    public Planet() {
        bounds = new Circle(x, y, GameConfig.PLANET_HALF_SIZE);
        setSize(GameConfig.PLANET_SIZE, GameConfig.PLANET_SIZE);
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

    public void setX(float x) {
        this.x = x;
        updateBounds();
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
        updateBounds();
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
        updateBounds();
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
        updateBounds();
    }

    public Circle getBounds() {
        return bounds;
    }

    public void updateBounds() {
        float halfWidth = getWidth() / 2f;
        float halfHeight = getHeight() / 2f;
        bounds.setPosition(x + halfWidth, y + halfHeight);
    }
}
