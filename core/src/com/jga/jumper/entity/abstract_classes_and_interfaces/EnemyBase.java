package com.jga.jumper.entity.abstract_classes_and_interfaces;

import com.jga.jumper.entity.abstract_classes_and_interfaces.EntityBase;

public abstract class EnemyBase extends EntityBase {

    // == attributes

    protected boolean hasIdleAnimationStarted;
    protected boolean hasWalkAnimationStarted;
    protected boolean hasAttackAnimationStarted;
    protected boolean hasDeadAnimationStarted;

    // == constructors ==
    public EnemyBase() {

    }

    public abstract void move(float delta);


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
