package com.jga.jumper.state_machines;

public enum SlugState {
    SPAWNING,
    IDLE,
    WALKING,
    DYING,
    DEAD;

    private boolean isSpawning() { return this.SPAWNING == SPAWNING; }

    private boolean isIdle() { return this.IDLE == IDLE; }

    private boolean isWalking() { return this.WALKING == WALKING; }

    private boolean isDying() { return this.DYING == DYING; }

    private boolean isDead() { return this.DEAD == DEAD; }

    public SlugState getCurrentSlugState() {
        if (isSpawning()) return SPAWNING;
        else if (isIdle()) return IDLE;
        else if (isWalking()) return WALKING;
        else if (isDying()) return DYING;
        else if (isDead())return DEAD;
        else return null;
    }
}
