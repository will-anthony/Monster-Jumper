package com.jga.jumper.entity.projectiles;

import com.badlogic.gdx.math.Rectangle;
import com.jga.jumper.entity.EntityBase;

public abstract class ProjectileBase extends EntityBase {

    // == attributes ==
    protected boolean hasSpawnAnimationStarted;
    protected boolean hasMoveAnimationStarted;
    protected boolean hasDeathAnimationStarted;


    // == constructors ==
    public ProjectileBase() {

    }

    // == public methods ==
    public boolean hasSpawnAnimationStarted() {
        return hasSpawnAnimationStarted;
    }

    public void setHasSpawnAnimationStarted(boolean hasSpawnAnimationStarted) {
        this.hasSpawnAnimationStarted = hasSpawnAnimationStarted;
    }

    public boolean hasMoveAnimationStarted() {
        return hasMoveAnimationStarted;
    }

    public void setHasMoveAnimationStarted(boolean hasMoveAnimationStarted) {
        this.hasMoveAnimationStarted = hasMoveAnimationStarted;
    }

    public boolean hasDeathAnimationStarted() {
        return hasDeathAnimationStarted;
    }

    public void setHasDeathAnimationStarted(boolean hasDeathAnimationStarted) {
        this.hasDeathAnimationStarted = hasDeathAnimationStarted;
    }

}
