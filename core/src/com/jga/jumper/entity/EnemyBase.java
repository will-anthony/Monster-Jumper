package com.jga.jumper.entity;

import com.badlogic.gdx.math.Circle;

public abstract class EnemyBase extends EntityBase {

    // == attributes
    protected boolean antiClockWise;

    // == constructors ==
    public EnemyBase() {

    }

    // == public methods ==
    public boolean isAntiClockWise() {
        return antiClockWise;
    }
}
