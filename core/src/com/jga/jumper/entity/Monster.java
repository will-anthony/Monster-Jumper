package com.jga.jumper.entity;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Pool;
import com.jga.jumper.config.GameConfig;
import com.jga.jumper.entity.abstract_classes_and_interfaces.EntityBase;
import com.jga.jumper.state_machines.MonsterState;

public class Monster extends EntityBase implements Pool.Poolable {

    private float monsterVelocityX = GameConfig.MONSTER_START_ANGULAR_SPEED;
    private float monsterVelocityY;
    private float gravity = GameConfig.MONSTER_GRAVITY;
    private float acceleration = 0;

    private MonsterState state = MonsterState.IDLE;

    private float dashSpeed = 0;
    private float dashDuration = GameConfig.MONSTER_DASH_DURATION;
    private float dashTimer = 0;
    private float dashInterval = 0;

    private boolean hasIdleAnimationStarted;
    private boolean hasDeathAnimationStarted;
    private boolean hasWalkAnimationStarted;
    private boolean hasDashAnimationStarted;
    private boolean hasJumpAnimationStarted;
    private boolean hasFallAnimationStarted;

    // == constructors ==
    public Monster() {
        angleDegrees = GameConfig.START_ANGLE;
        setSize(GameConfig.MONSTER_SIZE, GameConfig.MONSTER_SIZE);
    }

    @Override
    public Polygon definePolygonCollider() {

        float[] polygonCoordinates = {0.5f, 0,
                0.5f, GameConfig.MONSTER_SIZE - 0.5f,
                GameConfig.MONSTER_SIZE - 0.5f, GameConfig.MONSTER_SIZE - 0.5f,
                GameConfig.MONSTER_SIZE - 0.5f, 0};

        Polygon polygon = new Polygon(polygonCoordinates);
        polygon.setOrigin(0, 0);

        return polygon;

    }

    public void update(float delta) {
        switch (state) {
            case JUMPING:
                monsterVelocityY += acceleration * delta;
                acceleration -= gravity;
                if(acceleration <= 0) {
                    fall();
                }
                break;

            case FALLING:
                monsterVelocityY += acceleration * delta;
                acceleration -= gravity;
                if (monsterVelocityY <= 0) {
                    monsterVelocityY = 0;
                    walk();
                }
                break;

            case IDLE:
                monsterVelocityX = 0;
                break;

            case DASHING:
                dashSpeed = 160;
                dashTimer += delta;
                if (dashTimer >= dashDuration) {
                    dashSpeed = 0;
                    dashInterval = 1f;
                    dashTimer = 0;
                    acceleration = 0;
                    fall();
                }
                break;

        }

        reduceDashInterval(delta);
        move(delta);
    }

    private void reduceDashInterval(float delta) {
        if (dashInterval > 0) {
            dashInterval -= delta;
        } else if (dashInterval < 0) {
            dashInterval = 0;
        }
    }

    private void move(float delta) {

        angleDegrees += (monsterVelocityX + dashSpeed) * delta;
        angleDegrees = angleDegrees % 360;
        dashSpeed = 0;

        float radius = GameConfig.PLANET_HALF_SIZE + monsterVelocityY;
        float originX = GameConfig.WORLD_CENTER_X;
        float originY = GameConfig.WORLD_CENTER_Y;

        float newX = originX + MathUtils.cosDeg(-angleDegrees) * radius;
        float newY = originY + MathUtils.sinDeg(-angleDegrees) * radius;

        setPosition(newX, newY);
        polygonCollider.setPosition(newX, newY);
        polygonCollider.setRotation(GameConfig.START_ANGLE - angleDegrees);
    }

    public void reset() {
        angleDegrees = GameConfig.START_ANGLE;
        monsterVelocityY = 0;
        dashInterval = 0;
        idle();
    }

    public float getDashInterval() {
        return dashInterval;
    }

    public MonsterState getState() {
        return state;
    }

    private void fall() {
        state = MonsterState.FALLING;
    }

    private void idle() {
        state = MonsterState.IDLE;
    }

    public void jump() {
        state = MonsterState.JUMPING;

    }

    public void dash() {
        state = MonsterState.DASHING;
    }

    public void dead() {
        state = MonsterState.DEAD;

    }

    public void walk() {
        acceleration = GameConfig.MONSTER_START_ACCELERATION;
        state = MonsterState.WALKING;
    }

    public boolean isHasDashAnimationStarted() {
        return hasDashAnimationStarted;
    }

    public void setHasDashAnimationStarted(boolean hasDashAnimationStarted) {
        this.hasDashAnimationStarted = hasDashAnimationStarted;
    }

    public boolean isHasJumpAnimationStarted() {
        return hasJumpAnimationStarted;
    }

    public void setHasJumpAnimationStarted(boolean hasJumpAnimationStarted) {
        this.hasJumpAnimationStarted = hasJumpAnimationStarted;
    }

    public boolean isHasFallAnimationStarted() {
        return hasFallAnimationStarted;
    }

    public void setHasFallAnimationStarted(boolean hasFallAnimationStarted) {
        this.hasFallAnimationStarted = hasFallAnimationStarted;
    }

    public boolean isHasIdleAnimationStarted() {
        return hasIdleAnimationStarted;
    }

    public void setHasIdleAnimationStarted(boolean hasIdleAnimationStarted) {
        this.hasIdleAnimationStarted = hasIdleAnimationStarted;
    }

    public boolean isHasDeathAnimationStarted() {
        return hasDeathAnimationStarted;
    }

    public void setHasDeathAnimationStarted(boolean hasDeathAnimationStarted) {
        this.hasDeathAnimationStarted = hasDeathAnimationStarted;
    }

    public boolean isHasWalkAnimationStarted() {
        return hasWalkAnimationStarted;
    }

    public void setHasWalkAnimationStarted(boolean hasWalkAnimationStarted) {
        this.hasWalkAnimationStarted = hasWalkAnimationStarted;
    }

    public void setAcceleration(float acceleration) {
        this.acceleration = acceleration;
    }

    public float getAcceleration() {
        return acceleration;
    }
}
