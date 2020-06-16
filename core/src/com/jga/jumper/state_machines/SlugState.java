package com.jga.jumper.state_machines;

import com.jga.jumper.state_machines.interfaces.MinimumEntityStates;

public enum SlugState implements MinimumEntityStates {
    WALKING,
    IDLE,
    DEAD;


    @Override
    public boolean isWalking() {
        return this == WALKING;
    }

    @Override
    public boolean isIdle() {
        return this == IDLE;
    }

    @Override
    public boolean isDead() {
        return this == DEAD;
    }

    public SlugState currentState() {
        if (isWalking()) return WALKING;
        else if (isIdle()) return IDLE;
        else if (isDead()) return DEAD;
        else return null;
    }
}
