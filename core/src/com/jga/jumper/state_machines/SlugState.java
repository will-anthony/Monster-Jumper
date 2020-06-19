package com.jga.jumper.state_machines;

import com.badlogic.gdx.utils.Array;
import com.jga.jumper.state_machines.interfaces.MinimumEntityStates;
import com.jga.jumper.state_machines.interfaces.SpawningEntityStates;

public class SlugState {

    // == attributes ==

    private final int spawning = 0;
    private final int idle = 1;
    private final int walking = 2;
    private final int dying = 3;
    private final int dead = 4;

    private int currentSlugState;

    // == constructor ==
    public SlugState() {

    }

    public int getCurrentSlugState() {
        if (isSpawning()) return spawning;
        else if (isIdle()) return idle;
        else if (isWalking()) return walking;
        else if (isDying()) return dying;
        else return dead;
    }

    public void setCurrentSlugState(int newState) {
        if (newState >= 0 && newState <= 4) {
            this.currentSlugState = newState;
        }
    }

    private boolean isSpawning() {
        return this.currentSlugState == spawning;
    }

    private boolean isIdle() {
        return this.currentSlugState == idle;
    }

    private boolean isWalking() {
        return this.currentSlugState == dying;
    }

    private boolean isDying() {
        return this.currentSlugState == walking;
    }
}
