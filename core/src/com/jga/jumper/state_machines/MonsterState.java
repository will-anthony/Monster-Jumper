package com.jga.jumper.state_machines;

import com.jga.jumper.state_machines.interfaces.MinimumEntityStates;
import com.jga.jumper.state_machines.interfaces.DashingEntityStates;
import com.jga.jumper.state_machines.interfaces.JumpingEntityStates;

public enum MonsterState implements MinimumEntityStates, JumpingEntityStates, DashingEntityStates {
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
        else if (isDashing()) return DASHING;
        else if (isWalking()) return WALKING;
        else if (isIdle()) return IDLE;
        else if (isDead()) return DEAD;
        else return null;
    }
}
