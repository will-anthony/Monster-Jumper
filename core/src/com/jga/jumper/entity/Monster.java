package com.jga.jumper.entity;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Pool;
import com.jga.jumper.config.GameConfig;

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
        angleDegree = GameConfig.START_ANGLE;
        setSize(GameConfig.MONSTER_SIZE, GameConfig.MONSTER_SIZE);
    }

    // == public methods ==
    public void update(float delta) {

        if(state.currentState() == MonsterState.JUMPING) {
            jumpingSpeed += acceleration * delta;

            // when you've reached max speed switch state to falling
            if(jumpingSpeed >= GameConfig.MONSTER_MAX_SPEED) {
                fall();
            }
        } else if(state.currentState() == MonsterState.FALLING) {
            jumpingSpeed -= acceleration * delta;

            // when speed = 0 switch state to walking
            if(jumpingSpeed <= 0) {
                jumpingSpeed = 0;
                walk();
            }
        } else if(state.currentState() == MonsterState.IDLE) {
            angleDegreeSpeed = 0;

        }else if (state.currentState() == MonsterState.DASHING) {
            dashSpeed = 150;
            dashTimer += delta;

            if(dashTimer >= dashDuration) {
                dashSpeed = 0;
                dashInterval = 2f;
                dashTimer = 0;
                fall();
            }
        }
        if(dashInterval > 0) {
            dashInterval -= delta;
        } else if (dashInterval < 0) {
            dashInterval = 0;
        }

        angleDegreeSpeed = GameConfig.MONSTER_START_ANGULAR_SPEED;

        angleDegree += (angleDegreeSpeed + dashSpeed) * delta;
        angleDegree = angleDegree % 360;
        dashSpeed = 0;

        float radius = GameConfig.PLANET_HALF_SIZE + jumpingSpeed;
        float originX = GameConfig.WORLD_CENTER_X;
        float originY = GameConfig.WORLD_CENTER_Y;

        float newX = originX + MathUtils.cosDeg(-angleDegree) * radius;
        float newY = originY + MathUtils.sinDeg(-angleDegree) * radius;;

        setPosition(newX, newY);
    }

    public float getAngleDegrees() {
        return angleDegree;
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

    public float getDashInterval() {
        return dashInterval;
    }

    public void reset() {
        angleDegree = GameConfig.START_ANGLE;
        jumpingSpeed = 0;
        idle();

    }

    public MonsterState getState() {
        return state;
    }

    @Override
    public void setSize(float width, float height) {
        super.setSize(width, height);
    }

    // == private methods ==
    private void fall() {
        state = MonsterState.FALLING;
    }

    private void idle() {
        state = MonsterState.IDLE;
    }
}
