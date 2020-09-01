package com.jga.jumper.waves;

import com.jga.jumper.entity.abstract_classes_and_interfaces.EnemyBase;

import java.util.List;

public interface Wave<T extends EnemyBase> {
    void spawnEnemies();
    void reduceTimer(float delta);
    float getTimer();
}
