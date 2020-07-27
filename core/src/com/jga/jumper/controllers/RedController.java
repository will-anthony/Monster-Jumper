package com.jga.jumper.controllers;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;
import com.jga.jumper.common.SoundListener;
import com.jga.jumper.config.GameConfig;
import com.jga.jumper.entity.abstract_classes_and_interfaces.EnemyBase;
import com.jga.jumper.entity.Monster;
import com.jga.jumper.entity.Red;
import com.jga.jumper.object_distance_checker.DistanceChecker;
import com.jga.jumper.state_machines.GameState;
import com.jga.jumper.state_machines.MonsterState;

public class RedController implements EnemyController<Red> {

    // == attributes ==
    private final Array<Red> reds = new Array<>();
    private final Pool<Red> redPool = Pools.get(Red.class, 10);
    private ControllerRegister controllerRegister;
    private MonsterController monsterController;

    private SoundListener soundListener;

    // == constructors ==
    public RedController(ControllerRegister controllerRegister, SoundListener soundListener) {
        this.controllerRegister = controllerRegister;
        this.monsterController = controllerRegister.getMonsterController();
        this.soundListener = soundListener;
    }

    public void update(float delta) {
        Red red;

        for (int i = 0; i < reds.size; i++) {
            red = reds.get(i);
            switch (red.getCurrentRedState()) {
                case 0:
                    // spawning
                    enemySpawnLogic(red);
                    break;
                case 1:
                    // idle
                    break;
                case 2:
                    // walking
                    enemyWalkLogic(red, delta);
                    break;
                case 3:
                    // attacking
                    enemyAttackLogic(red, delta);
                    break;
                case 4:
                    // dying
                    enemyDyingLogic(red, delta);
                    break;
                case 5:
                    // dead
                    enemyDeathLogic(red);
                    break;
            }
        }
    }

    @Override
    public void enemySpawnLogic(Red enemy) {

        if (enemy.getRadius() < GameConfig.PLANET_HALF_SIZE) {
            enemy.setRadius(enemy.getRadius() + 0.01f);
        }
        if (enemy.getRadius() >= GameConfig.PLANET_HALF_SIZE) {
            enemy.setRadius(GameConfig.PLANET_HALF_SIZE);
            enemy.setCurrentRedState(GameConfig.ENEMY_WALKING_STATE);
        }
    }

    @Override
    public void enemyWalkLogic(Red enemy, float delta) {

        float redWalkTimer = enemy.getRedWalkTimer();

        enemy.setRedAttackTimer(1f);
        enemy.setHasRedThrownFireBall(false);

        enemy.move(delta);

        checkMonsterCollision(enemy, monsterController.getMonsters().get(0));
        checkEnemyCollision(enemy, reds);

        enemy.setRedWalkTimer(redWalkTimer -= delta);

        if (redWalkTimer <= 0) {
            enemy.setCurrentRedState(GameConfig.ENEMY_ATTACKING_STATE);
        }
    }

    @Override
    public void enemyAttackLogic(Red enemy, float delta) {

        float redAttackTimer = enemy.getRedAttackTimer();

        enemy.setRedWalkTimer((float) enemy.generateRandomWalkStateTime());

        checkMonsterCollision(enemy, monsterController.getMonsters().get(0));
        checkEnemyCollision(enemy, reds);

        enemy.setRedAttackTimer(redAttackTimer -= delta);
        if (!enemy.isHasRedThrownFireBall()) {
            enemy.setHasRedThrownFireBall(true);
            controllerRegister.getFireBallController().spawnProjectile(enemy);
        }

        if (redAttackTimer <= 0) {
            enemy.setClockWise(MathUtils.randomBoolean());
            enemy.setCurrentRedState(2);
        }
    }

    @Override
    public void enemyDyingLogic(Red enemy, float delta) {
        float deathTimer = enemy.getDeathTimer();
        System.out.println(deathTimer);
        if (deathTimer > 0) {
            enemy.setDeathTimer(deathTimer -= delta);
        }

        if (deathTimer <= 0) {
            controllerRegister.getCoinController().spawnCoins(enemy, 3);
            enemy.setCurrentRedState(GameConfig.ENEMY_DEAD_STATE);
        }
    }

    @Override
    public void enemyDeathLogic(Red enemy) {
        redPool.free(enemy);
        reds.removeValue(enemy, true);
    }

    @Override
    public boolean isEnemyNearby(float angle) {
        DistanceChecker<Red> redDistanceChecker = new DistanceChecker<>(reds);
        return redDistanceChecker.isEntityNearBy(angle);
    }

    public void tryToAddReds(int numberOfEnemies) {

        for (int i = 0; i < numberOfEnemies; i++) {

            float randomAngle = MathUtils.random(0, 360);

            if (canMageSpawn(randomAngle)) {
                Red red = redPool.obtain();
                red.setStartingPosition(randomAngle);
                reds.add(red);
            } else {
                numberOfEnemies++;
            }
        }
    }

    private boolean canMageSpawn(float randomAngle) {
        boolean canSpawn = !isEnemyNearby(randomAngle)
                && !monsterController.isMonsterNearBy(randomAngle);
        return canSpawn;
    }

    public void restart() {
        redPool.freeAll(reds);
        reds.clear();
    }

    public Array<Red> getReds() {
        return reds;
    }

    public void checkMonsterCollision(Red red, Monster monster) {

        if (monster.getState() == MonsterState.DASHING && Intersector.overlapConvexPolygons(monster.getPolygonCollider(), red.getPolygonCollider())) {
            red.setCurrentRedState(GameConfig.ENEMY_DYING_STATE);

        } else if (Intersector.overlapConvexPolygons(monster.getPolygonCollider(), red.getPolygonCollider()) &&
                monster.getState() != MonsterState.DASHING) {
            soundListener.lose();


            monster.dead();
            controllerRegister.getMasterController().setGameState(GameState.GAME_OVER);
        }
    }

    private void changeDirection(Red red) {

        if (red.isClockWise()) {
            red.setClockWise(false);
        } else {
            red.setClockWise(true);
        }
    }


    public void checkEnemyCollision(EnemyBase enemyBase1, Array<Red> enemyBases) {

        for (Red red : enemyBases) {

            if (Intersector.overlapConvexPolygons(enemyBase1.getPolygonCollider(), red.getPolygonCollider())) {

                // flip
                if (enemyBase1.isClockWise()) {
                    enemyBase1.setClockWise(false);
                } else {
                    enemyBase1.setClockWise(true);
                }

                if (red.isClockWise()) {
                    red.setClockWise(false);
                } else {
                    red.setClockWise(true);
                }
            }
        }
    }
}
