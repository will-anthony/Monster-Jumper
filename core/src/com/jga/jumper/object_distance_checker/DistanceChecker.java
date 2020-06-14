package com.jga.jumper.object_distance_checker;

import com.badlogic.gdx.utils.Array;
import com.jga.jumper.config.GameConfig;
import com.jga.jumper.entity.EntityBase;

public class DistanceChecker<T extends EntityBase> {

    // == attributes ==
    private Array<T> entities;

    public DistanceChecker(Array<T> entities) {
        this.entities = entities;
    }

    public boolean isEntityNearBy(float angle) {

        for (EntityBase entity : entities) {
            float angleDegree = entity.getAngleDegree();

            float diff = Math.abs(Math.abs(angleDegree) - Math.abs(angle));

            if (diff < GameConfig.MIN_ANG_DIST) {
                return true;
            }
        }
        return false;
    }
}
