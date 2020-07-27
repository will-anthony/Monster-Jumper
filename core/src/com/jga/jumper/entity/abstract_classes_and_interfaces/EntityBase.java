package com.jga.jumper.entity.abstract_classes_and_interfaces;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.jga.jumper.config.GameConfig;

public abstract class EntityBase {

    // == attributes ==
    protected float x;
    protected float y;

    protected float width = 1;
    protected float height = 1;

    protected float angleDegrees;
    protected float angleDegreesSpeed;
    protected float animationTime;

    protected boolean clockWise;
    protected float radius;

    protected Polygon polygonCollider;

    // == constructors ==
    public EntityBase() {
        polygonCollider = definePolygonCollider();
        animationTime = 0;
        radius = GameConfig.PLANET_HALF_SIZE;
    }

    // == public methods ==
    public void setStartingPosition(float value) {
        angleDegrees = value % 360;
        setAngleDegree();
    }

    public void setAngleDegree() {

        float originX = GameConfig.WORLD_CENTER_X;
        float originY = GameConfig.WORLD_CENTER_Y;

        float newX = originX + MathUtils.cosDeg(-angleDegrees) * radius;
        float newY = originY + MathUtils.sinDeg(-angleDegrees) * radius;

        setPosition(newX, newY);
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void setSize(float width, float height) {
        this.width = width;
        this.height = height;
    }

    public abstract Polygon definePolygonCollider();

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

    public void setClockWise(boolean clockWise) {
        this.clockWise = clockWise;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public float getAngleDegreesSpeed() {
        return angleDegreesSpeed;
    }

    public void setAngleDegreesSpeed(float angleDegreesSpeed) {
        this.angleDegreesSpeed = angleDegreesSpeed;
    }

    public void setPolygonCollider(Polygon polygonCollider) {
        this.polygonCollider = polygonCollider;
    }
}
