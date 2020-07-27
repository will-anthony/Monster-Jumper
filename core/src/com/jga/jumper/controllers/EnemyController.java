package com.jga.jumper.controllers;

import com.badlogic.gdx.utils.Array;
import com.jga.jumper.entity.abstract_classes_and_interfaces.EnemyBase;
import com.jga.jumper.entity.Monster;

public interface EnemyController<T extends EnemyBase> {

    void update(float delta);
    void enemySpawnLogic(T enemy);
    void enemyWalkLogic(T enemy, float delta);
    void enemyAttackLogic(T enemy, float delta);
    void enemyDyingLogic(T enemy, float delta);
    void enemyDeathLogic(T enemy);
    boolean isEnemyNearby(float angle);
    void checkMonsterCollision(T enemy, Monster monster);
    void checkEnemyCollision(EnemyBase thisEnemy, Array<T> otherEnemies);
}
