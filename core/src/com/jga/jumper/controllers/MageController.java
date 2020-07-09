package com.jga.jumper.controllers;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;
import com.jga.jumper.common.SoundListener;
import com.jga.jumper.config.GameConfig;
import com.jga.jumper.entity.EnemyBase;
import com.jga.jumper.entity.Mage;
import com.jga.jumper.entity.Monster;
import com.jga.jumper.object_distance_checker.DistanceChecker;
import com.jga.jumper.state_machines.GameState;
import com.jga.jumper.state_machines.MonsterState;

import java.util.Random;

public class MageController implements EnemyController<Mage> {

    // == attributes ==
    private final Array<Mage> mages = new Array<>();
    private final Pool<Mage> magePool = Pools.get(Mage.class, 10);
    private ControllerRegister controllerRegister;
    private MonsterController monsterController;

    private SoundListener soundListener;

    // == constructors ==
    public MageController(ControllerRegister controllerRegister, SoundListener soundListener) {
        this.controllerRegister = controllerRegister;
        this.monsterController = controllerRegister.getMonsterController();
        this.soundListener = soundListener;
    }

    public void update(float delta) {
        Mage mage;

        for (int i = 0; i < mages.size; i++) {
            mage = mages.get(i);
            switch (mage.getCurrentMageState()) {
                case 0:
                    // spawning
                    enemySpawnLogic(mage);
                    break;
                case 1:
                    // idle
                    break;
                case 2:
                    // walking
                    enemyWalkLogic(mage, delta);
                    break;
                case 3:
                    // attacking
                    enemyAttackLogic(mage, delta);
                    break;
                case 4:
                    // dying
                    enemyDyingLogic(mage, delta);
                    break;
                case 5:
                    // dead
                    enemyDeathLogic(mage);
                    break;
            }
        }
    }

    @Override
    public void enemySpawnLogic(Mage enemy) {

        // Mage rises from the planet. Once at final height, switch to walking state
        if (enemy.getRadius() < GameConfig.PLANET_HALF_SIZE) {
            enemy.setRadius(enemy.getRadius() + 0.01f);
        }
        if (enemy.getRadius() >= GameConfig.PLANET_HALF_SIZE) {
            enemy.setRadius(GameConfig.PLANET_HALF_SIZE);
            enemy.setCurrentMageState(GameConfig.ENEMY_WALKING_STATE);
        }
    }

    @Override
    public void enemyWalkLogic(Mage enemy, float delta) {

        float mageWalkTimer = enemy.getMageWalkTimer();

        enemy.setMageAttackTimer(1f);
        enemy.setHasMageThrownFireBall(false);

        enemy.move(delta);

        checkMonsterCollision(enemy, monsterController.getMonsters().get(0));
        checkEnemyCollision(enemy, mages);

        enemy.setMageWalkTimer(mageWalkTimer -= delta);

        if (mageWalkTimer <= 0) {
            enemy.setCurrentMageState(GameConfig.ENEMY_ATTACKING_STATE);
        }
    }

    @Override
    public void enemyAttackLogic(Mage enemy, float delta) {

        float mageAttackTimer = enemy.getMageAttackTimer();

        enemy.setMageWalkTimer((float) enemy.generateRandomWalkStateTime());

        checkMonsterCollision(enemy, monsterController.getMonsters().get(0));
        checkEnemyCollision(enemy, mages);

        enemy.setMageAttackTimer(mageAttackTimer -= delta);
        if (!enemy.isHasMageThrownFireBall()) {
            enemy.setHasMageThrownFireBall(true);
            controllerRegister.getFireBallController().spawnProjectile(enemy);
        }

        if (mageAttackTimer <= 0) {
            changeDirection(enemy);
            enemy.setCurrentMageState(2);
        }
    }

    @Override
    public void enemyDyingLogic(Mage enemy, float delta) {
        float deathTimer = enemy.getDeathTimer();
        System.out.println(deathTimer);
        if (deathTimer > 0) {
            enemy.setDeathTimer(deathTimer -= delta);
        }

        if (deathTimer <= 0) {
            enemy.setCurrentMageState(GameConfig.ENEMY_DEAD_STATE);
        }
    }

    @Override
    public void enemyDeathLogic(Mage enemy) {
        magePool.free(enemy);
        mages.removeValue(enemy, true);
    }

    @Override
    public boolean isEnemyNearby(float angle) {
        DistanceChecker<Mage> mageDistanceChecker = new DistanceChecker<>(mages);
        return mageDistanceChecker.isEntityNearBy(angle);
    }

    public void tryToAddMages(int numberOfEnemies) {

        for (int i = 0; i < numberOfEnemies; i++) {

            float randomAngle = MathUtils.random(0, 360);

            if (canMageSpawn(randomAngle)) {
                Mage mage = magePool.obtain();
                mage.setStartingPosition(randomAngle);
                mages.add(mage);
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
        magePool.freeAll(mages);
        mages.clear();
    }

    public Array<Mage> getMages() {
        return mages;
    }

    public void checkMonsterCollision(Mage mage, Monster monster) {

//        // monster kills mage with jump attack
        if (Intersector.overlapConvexPolygons(monster.getPolygonCollider(), mage.getKillCollider()) && monster.getState() == MonsterState.FALLING) {
            mage.setCurrentMageState(GameConfig.ENEMY_DYING_STATE);
            monster.setAcceleration(GameConfig.MONSTER_BOUNCE_ACCELERATION);
            monster.jump();

        } else if (monster.getState() == MonsterState.DASHING && Intersector.overlapConvexPolygons(monster.getPolygonCollider(), mage.getPolygonCollider())) {
            mage.setCurrentMageState(GameConfig.ENEMY_DYING_STATE);

//            // mage kills monster
        } else if (Intersector.overlapConvexPolygons(monster.getPolygonCollider(), mage.getPolygonCollider()) &&
                monster.getState() != MonsterState.DASHING) {
            soundListener.lose();


            monster.dead();
            controllerRegister.getMasterController().setGameState(GameState.GAME_OVER);
        }
    }

    private void changeDirection(Mage mage) {

        if (mage.isClockWise()) {
            mage.setClockWise(false);
        } else {
            mage.setClockWise(true);
        }
    }


    public void checkEnemyCollision(EnemyBase enemyBase1, Array<Mage> enemyBases) {

        for (Mage mage : enemyBases) {

            if (Intersector.overlapConvexPolygons(enemyBase1.getPolygonCollider(), mage.getPolygonCollider())) {

                // flip
                if (enemyBase1.isClockWise()) {
                    enemyBase1.setClockWise(false);
                } else {
                    enemyBase1.setClockWise(true);
                }

                if (mage.isClockWise()) {
                    mage.setClockWise(false);
                } else {
                    mage.setClockWise(true);
                }
            }
        }
    }
}
