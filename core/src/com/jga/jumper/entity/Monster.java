package com.jga.jumper.entity;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Pool;
import com.jga.jumper.config.GameConfig;
import com.jga.jumper.state_machines.MonsterState;

public class Monster extends EntityBase implements Pool.Poolable {

    // == attributes ==
    private float angleDegreeSpeed = GameConfig.MONSTER_START_ANGULAR_SPEED;
    private float jumpingSpeed;
    private float dashSpeed = 0;
    private float dashDuration = 0.16f;
    private float dashTimer = 0;
    private float dashInterval = 0;
    private float acceleration = GameConfig.MONSTER_START_ACCELERATION;
    private MonsterState state = MonsterState.IDLE;

    // == constructors ==
    public Monster() {
        angleDegrees = GameConfig.START_ANGLE;
        setSize(GameConfig.MONSTER_SIZE, GameConfig.MONSTER_SIZE);
    }

    public void update(float delta) {
        switch (state) {
            case JUMPING:
                jumpingSpeed += acceleration * delta;
                if (jumpingSpeed >= GameConfig.MONSTER_MAX_SPEED) {
                    fall();
                }
                break;
            case FALLING:
                jumpingSpeed -= acceleration * delta;
                if (jumpingSpeed <= 0) {
                    jumpingSpeed = 0;
                    walk();
                }
                break;
            case IDLE:
                angleDegreeSpeed = 0;
                break;
            case DASHING:
                dashSpeed = 150;
                dashTimer += delta;
                if (dashTimer >= dashDuration) {
                    dashSpeed = 0;
                    dashInterval = 2f;
                    dashTimer = 0;
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

        angleDegrees += (angleDegreeSpeed + dashSpeed) * delta;
        angleDegrees = angleDegrees % 360;
        dashSpeed = 0;

        float radius = GameConfig.PLANET_HALF_SIZE + jumpingSpeed;
        float originX = GameConfig.WORLD_CENTER_X;
        float originY = GameConfig.WORLD_CENTER_Y;

        float newX = originX + MathUtils.cosDeg(-angleDegrees) * radius;
        float newY = originY + MathUtils.sinDeg(-angleDegrees) * radius;

        setPosition(newX, newY);
    }

    public void reset() {
        angleDegrees = GameConfig.START_ANGLE;
        jumpingSpeed = 0;
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
        state = MonsterState.WALKING;
    }
}
