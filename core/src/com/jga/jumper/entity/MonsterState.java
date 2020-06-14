package com.jga.jumper.entity;

public enum MonsterState {
    WALKING,
    IDLE,
    JUMPING,
    FALLING,
    DASHING,
    DEAD;

    // == public methods ==
    public boolean isWalking() {
        return this == WALKING;
    }

    public boolean isJumping() {
        return this == JUMPING;
    }

    public boolean isFalling() {
        return this == FALLING;
    }

    public boolean isDashing() {
        return this == DASHING;
    }

    public boolean isIdle() {
        return this == IDLE;
    }

    public boolean isDead() {
        return this == DEAD;
    }

    public MonsterState currentState() {
        if (isFalling()) return FALLING;
        else if (isJumping()) return JUMPING;
        else if (isWalking()) return WALKING;
        else if (isDashing()) return DASHING;
        else if (isIdle()) return IDLE;
        else return null;
    }
}
