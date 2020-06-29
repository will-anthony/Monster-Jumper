package com.jga.jumper.common;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.jga.jumper.controllers.MonsterController;
import com.jga.jumper.entity.EnemyBase;
import com.jga.jumper.object_distance_checker.DistanceChecker;

//public class EnemySpawner<T extends EnemyBase> {
//
//    private MonsterController monsterController;
//    private Array<EnemyBase> enemyArray;
//
//    public EnemySpawner(MonsterController monsterController) {
//        this.monsterController = monsterController;
//        this.enemyArray = new Array<>();
//    }
//
//    public void tryToAddEnemies(Pool<T> enemyPool, int numberOfEnemies) {
//
//        for (int i = 0; i < numberOfEnemies; i++) {
//
//            float randomAngle = MathUtils.random(0, 360);
//            EnemyBase enemy = enemyPool.obtain();
//
//            if (canEnemySpawn(randomAngle, enemy)) {
//
//                enemy.setStartingPosition(randomAngle);
//                enemyArray.add(enemy);
//            }
//        }
//    }
//
//    private boolean canEnemySpawn(float randomAngle, EnemyBase enemy) {
//
//        boolean canSpawn = !isEnemyNearby(randomAngle)
//                && !monsterController.isMonsterNearBy(randomAngle);
//        return canSpawn;
//    }
//
//    public boolean isEnemyNearby(float angle) {
//        DistanceChecker<EnemyBase> enemyDistanceChecker = new DistanceChecker<>(enemyArray);
//        return enemyDistanceChecker.isEntityNearBy(angle);
//    }
//
//    public Array<EnemyBase> getEnemyArray() {
//        return enemyArray;
//    }
//}
