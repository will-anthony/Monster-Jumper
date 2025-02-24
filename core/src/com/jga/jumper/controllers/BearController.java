package com.jga.jumper.controllers;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;
import com.jga.jumper.common.SoundListener;
import com.jga.jumper.config.GameConfig;
import com.jga.jumper.entity.Bear;
import com.jga.jumper.entity.abstract_classes_and_interfaces.EnemyBase;
import com.jga.jumper.entity.Monster;
import com.jga.jumper.object_distance_checker.DistanceChecker;
import com.jga.jumper.state_machines.GameState;
import com.jga.jumper.state_machines.MonsterState;

public class BearController implements EnemyController<Bear> {

    private final Array<Bear> bears = new Array<>();
    private final Pool<Bear> bearPool = Pools.get(Bear.class, 10);
    private ControllerRegister controllerRegister;
    private MonsterController monsterController;

    private SoundListener soundListener;

    // == constructors ==
    public BearController(ControllerRegister controllerRegister, SoundListener soundListener) {
        this.controllerRegister = controllerRegister;
        this.monsterController = controllerRegister.getMonsterController();
        this.soundListener = soundListener;
    }

    public void update(float delta) {
        Bear bear;


        for (int i = 0; i < bears.size; i++) {
            bear = bears.get(i);
            switch (bear.getCurrentBearState()) {
                case 0:
                    // spawning
                    enemySpawnLogic(bear);
                    break;
                case 1:
                    // idle
                    break;
                case 2:
                    // walking
                    enemyWalkLogic(bear, delta);
                    break;
                case 3:
                    // attacking
                    enemyAttackLogic(bear, delta);
                    break;
                case 4:
                    // dying
                    enemyDyingLogic(bear, delta);
                    break;
                case 5:
                    // dead
                    enemyDeathLogic(bear);
                    break;
                case 6:
                    // damaged
                    bearDamagedLogic(bear, delta);
                    break;
                case 7:
                    // summon attack
                    bearSummonLogic(bear, delta);
                    break;
            }
        }
    }


    @Override
    public void enemySpawnLogic(Bear bear) {
        if (bear.getRadius() < GameConfig.PLANET_HALF_SIZE) {
            bear.setRadius(bear.getRadius() + 0.01f);
        }
        if (bear.getRadius() >= GameConfig.PLANET_HALF_SIZE) {
            bear.setRadius(GameConfig.PLANET_HALF_SIZE + 0.2f);
            bear.setCurrentBearState(GameConfig.ENEMY_WALKING_STATE);
        }
    }

    @Override
    public void enemyWalkLogic(Bear bear, float delta) {

        bear.setSummonLogicTimer(2f);
        bear.setHasSpawnedSpikes(false);

        bear.move(delta);
        checkMonsterCollision(bear, monsterController.getMonsters().get(0));
        checkEnemyCollision(bear, bears);
    }

    @Override
    public void enemyAttackLogic(Bear bear, float delta) {
        bear.charge(delta);
        checkMonsterCollision(bear, monsterController.getMonsters().get(0));
        checkEnemyCollision(bear, bears);
    }

    @Override
    public void enemyDyingLogic(Bear bear, float delta) {

        float deathTimer = bear.getDeathTimer();
        if (deathTimer > 0) {
            bear.setDeathTimer(deathTimer -= delta);

            if (deathTimer <= 0) {
                controllerRegister.getCoinController().spawnCoins(bear, 12);
                bear.setCurrentBearState(GameConfig.ENEMY_DEAD_STATE);
            }
        }
    }
        @Override
        public void enemyDeathLogic (Bear bear){
            controllerRegister.getTrapWarningSmokeController().spawnTrapWarningSmoke(
                    bear, 0,0,
                    GameConfig.TRAP_WARNING_SMOKE_WITHDRAW_STATE, GameConfig.PLANET_HALF_SIZE - 0.1f);
            bearPool.free(bear);
            bears.removeValue(bear, true);
        }

        public void bearDamagedLogic (Bear bear,float delta){
            bear.setBearDamagedTimer(bear.getBearDamagedTimer() - delta);
            if (bear.getBearDamagedTimer() <= 0 && bear.getHitPoints() != 0) {
                bear.setCurrentBearState(GameConfig.ENEMY_WALKING_STATE);
            }
            if (bear.getBearDamagedTimer() <= 0 && bear.getHitPoints() == 0) {
                bear.setCurrentBearState(GameConfig.ENEMY_ATTACKING_STATE);
            }
        }

        private void bearSummonLogic(Bear bear, float delta){
            float bearSummonLogicTimer = bear.getSummonLogicTimer();

            checkMonsterCollision(bear, monsterController.getMonsters().get(0));

            bear.setSummonLogicTimer(bearSummonLogicTimer - delta);
            if (!bear.isHasSpawnedSpikes()) {
                controllerRegister.getTrapWarningSmokeController().spawnTrapWarningSmoke(
                        bear, 24, -8,
                        GameConfig.TRAP_WARNING_SMOKE_SPAWN_STATE, GameConfig.PLANET_HALF_SIZE - 0.1f);
                bear.setHasSpawnedSpikes(true);
                controllerRegister.getSpikeTrapController().spawnSpikeTrap(bear);
            }

            if (bearSummonLogicTimer <= 0) {
                bear.setCurrentBearState(GameConfig.ENEMY_WALKING_STATE);
            }
        }

        @Override
        public boolean isEnemyNearby ( float angle){
            DistanceChecker<Bear> bearDistanceChecker = new DistanceChecker<>(bears);
            return bearDistanceChecker.isEntityNearBy(angle);
        }

        public void tryToAddBears ( int numberOfBears){

            for (int i = 0; i < numberOfBears; i++) {

                float randomAngle = MathUtils.random(0, 360);

                if (canBearSpawn(randomAngle)) {
                    Bear bear = bearPool.obtain();
                    bear.setStartingPosition(randomAngle);
                    bears.add(bear);
                } else {
                    numberOfBears++;
                }
            }
        }

        private boolean canBearSpawn ( float randomAngle){
            boolean canSpawn = !isEnemyNearby(randomAngle)
                    && !monsterController.isMonsterNearBy(randomAngle);
            return canSpawn;
        }

        public void restart () {
            bearPool.freeAll(bears);
            bears.clear();
        }

        public Array<Bear> getBears () {
            return bears;
        }

        @Override
        public void checkEnemyCollision (EnemyBase thisEnemy, Array < Bear > otherEnemies){

        }

        public void checkMonsterCollision (Bear bear, Monster monster){

            // monster kills bear with jump attack
            if (Intersector.overlapConvexPolygons(monster.getPolygonCollider(), bear.getKillCollider()) && monster.getState() == MonsterState.FALLING) {
                if (bear.getHitPoints() > 0) {
                    bear.setHitPoints(bear.getHitPoints() - 1);
                    bear.setCurrentBearState(GameConfig.ENEMY_DAMAGED_STATE);
                } else {
                    bear.setCurrentBearState(GameConfig.ENEMY_DYING_STATE);
                }
                monster.setAcceleration(GameConfig.MONSTER_MAX_DOUBLE_JUMP);
                controllerRegister.getTrapWarningSmokeController().spawnTrapWarningSmoke(
                        monster, 0, 0, GameConfig.TRAP_WARNING_SMOKE_WITHDRAW_STATE,
                        GameConfig.PLANET_HALF_SIZE + GameConfig.BEAR_SIZE - 1.1f
                );
                monster.jump();

            } else if (monster.getState() == MonsterState.DASHING && Intersector.overlapConvexPolygons(monster.getPolygonCollider(), bear.getPolygonCollider())) {
                if (bear.getHitPoints() > 0) {
                    bear.setHitPoints(bear.getHitPoints() - 1);
                    bear.setCurrentBearState(GameConfig.ENEMY_DAMAGED_STATE);
                } else {
                    bear.setCurrentBearState(GameConfig.ENEMY_DYING_STATE);
                }

            } else if (Intersector.overlapConvexPolygons(monster.getPolygonCollider(), bear.getAwarenessCollider()) &&
                    bear.getHitPoints() > 0) {
                bear.setCurrentBearState(GameConfig.BEAR_CHARGE_ATTACK_STATE);

                // bear kills monster
            } else if (Intersector.overlapConvexPolygons(monster.getPolygonCollider(), bear.getPolygonCollider()) &&
                    monster.getState() != MonsterState.DASHING) {
                soundListener.lose();

                monster.dead();
                controllerRegister.getMasterController().setGameState(GameState.GAME_OVER);
            }
        }
    }
