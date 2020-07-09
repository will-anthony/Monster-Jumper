package com.jga.jumper.entity;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.jga.jumper.config.GameConfig;

public abstract class EnemyBase extends EntityBase {

    // == attributes

    protected boolean hasIdleAnimationStarted;
    protected boolean hasWalkAnimationStarted;
    protected boolean hasAttackAnimationStarted;
    protected boolean hasDeadAnimationStarted;

    // == constructors ==
    public EnemyBase() {

    }

    public boolean hasIdleAnimationStarted() {
        return hasIdleAnimationStarted;
    }

    public void setHasIdleAnimationStarted(boolean hasIdleAnimationStarted) {
        this.hasIdleAnimationStarted = hasIdleAnimationStarted;
    }

    public boolean hasWalkAnimationStarted() {
        return hasWalkAnimationStarted;
    }

    public void setHasWalkAnimationStarted(boolean hasWalkAnimationStarted) {
        this.hasWalkAnimationStarted = hasWalkAnimationStarted;
    }

    public boolean hasAttackAnimationStarted() {
        return hasAttackAnimationStarted;
    }

    public void setHasAttackAnimationStarted(boolean hasAttackAnimationStarted) {
        this.hasAttackAnimationStarted = hasAttackAnimationStarted;
    }

    public boolean hasDeadAnimationStarted() {
        return hasDeadAnimationStarted;
    }

    public void setHasDeadAnimationStarted(boolean hasDeadAnimationStarted) {
        this.hasDeadAnimationStarted = hasDeadAnimationStarted;
    }
}
