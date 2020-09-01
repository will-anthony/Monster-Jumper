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
import com.jga.jumper.entity.Skull;
import com.jga.jumper.object_distance_checker.DistanceChecker;
import com.jga.jumper.state_machines.GameState;
import com.jga.jumper.state_machines.MonsterState;

public class SkullController implements EnemyController<Skull> {

    // == attributes ==
    private final Array<Skull> skulls = new Array<>();
    private final Pool<Skull> skullPool = Pools.get(Skull.class, 10);
    private ControllerRegister controllerRegister;
    private MonsterController monsterController;

    private SoundListener soundListener;

    // == constructors ==
    public SkullController(ControllerRegister controllerRegister, SoundListener soundListener) {
        this.controllerRegister = controllerRegister;
        this.monsterController = controllerRegister.getMonsterController();
        this.soundListener = soundListener;
    }

    public void update(float delta) {
        Skull skull;

        for (int i = 0; i < skulls.size; i++) {
            skull = skulls.get(i);
            switch (skull.getCurrentSkullState()) {
                case 0:
                    // spawning
                    enemySpawnLogic(skull);
                    break;
                case 1:
                    // idle
                    break;
                case 2:
                    // walking
                    enemyWalkLogic(skull, delta);
                    break;
                case 3:
                    // attacking
                    enemyAttackLogic(skull, delta);
                    break;
                case 4:
                    // dying
                    enemyDyingLogic(skull, delta);
                    break;
                case 5:
                    // dead
                    enemyDeathLogic(skull);
                    break;
            }
        }
    }

    @Override
    public void enemySpawnLogic(Skull enemy) {

        enemy.setMoving(false);

        // slug emerges from planet
        if (enemy.getRadius() < GameConfig.PLANET_HALF_SIZE + 0.1f) {
            enemy.setRadius(enemy.getRadius() + 0.02f);
        }
        if (enemy.getRadius() >= GameConfig.PLANET_HALF_SIZE + 0.1f) {
            enemy.setRadius(GameConfig.PLANET_HALF_SIZE + 0.1f);
            enemy.setCurrentSkullState(GameConfig.ENEMY_WALKING_STATE);
        }
    }

    @Override
    public void enemyWalkLogic(Skull enemy, float delta) {

        enemy.setChangedDirection(false);
        enemy.setMoving(true);

        float timeBetweenAttacks = enemy.getTimeBetweenAttacks();
        enemy.setTimeBetweenAttacks(timeBetweenAttacks - delta);
        enemy.move(delta);

        checkMonsterCollision(enemy, monsterController.getMonsters().get(0));
        if(enemy.getTimeBetweenAttacks() <= 0) {
            enemy.setCurrentSkullState(GameConfig.ENEMY_ATTACKING_STATE);
        }
    }

    @Override
    public void enemyAttackLogic(Skull enemy, float delta) {

        enemy.setMoving(false);

        float timeInAttackState = enemy.getTimeInAttackState();
        float spawnSpikeTrapDelay = enemy.getSpikeTrapSpawnDelay();

        enemy.setTimeInAttackState(timeInAttackState - delta);
        enemy.setSpikeTrapSpawnDelay(spawnSpikeTrapDelay - delta);

        enemy.setTimeBetweenAttacks(MathUtils.random(2f, 6f));

        if (spawnSpikeTrapDelay <= 0 && !enemy.hasTrapSpawned()) {
            controllerRegister.getSkullSpikeTrapController().spawnSkullSpikeTrap(enemy);
            controllerRegister.getTrapWarningSmokeController().spawnTrapWarningSmoke(
                    enemy, 24, -8,
                    GameConfig.TRAP_WARNING_SMOKE_SPAWN_STATE, GameConfig.PLANET_HALF_SIZE - 0.1f);
            enemy.setTrapSpawned(true);
        }

        checkMonsterCollision(enemy, monsterController.getMonsters().get(0));

        if (timeInAttackState <= 0) {

            enemy.setTimeInAttackState(2f);

            boolean flagForChangedDirection = enemy.isClockWise();
            enemy.setClockWise(MathUtils.randomBoolean());
            if(flagForChangedDirection != enemy.isClockWise()) {
                enemy.setChangedDirection(true);
            }
            enemy.setTrapSpawned(false);
            enemy.setCurrentSkullState(GameConfig.ENEMY_WALKING_STATE);

        }
    }

    @Override
    public void enemyDyingLogic(Skull enemy, float delta) {
        float deathTimer = enemy.getDeathTimer();
        if (deathTimer > 0) {
            enemy.setDeathTimer(deathTimer -= delta);
        }

        if (deathTimer <= 0) {
            controllerRegister.getCoinController().spawnCoins(enemy, 5);
            enemy.setCurrentSkullState(GameConfig.ENEMY_DEAD_STATE);
        }
    }

    @Override
    public void enemyDeathLogic(Skull enemy) {
        controllerRegister.getTrapWarningSmokeController().spawnTrapWarningSmoke(
                enemy, 0,0,
                GameConfig.TRAP_WARNING_SMOKE_WITHDRAW_STATE, GameConfig.PLANET_HALF_SIZE - 0.1f);
        skullPool.free(enemy);
        skulls.removeValue(enemy, true);
    }

    @Override
    public boolean isEnemyNearby(float angle) {
        DistanceChecker<Skull> slugDistanceChecker = new DistanceChecker<>(skulls);
        return slugDistanceChecker.isEntityNearBy(angle);
    }

    public void tryToAddSkulls(int numberOfEnemies) {

        for (int i = 0; i < numberOfEnemies; i++) {

            float randomAngle = MathUtils.random(0, 360);

            if (canSkullSpawn(randomAngle)) {
                Skull skull = skullPool.obtain();
                skull.setStartingPosition(randomAngle);
                skulls.add(skull);
            } else {
                numberOfEnemies++;
            }
        }
    }

    private boolean canSkullSpawn(float randomAngle) {
        boolean canSpawn = !isEnemyNearby(randomAngle)
                && !monsterController.isMonsterNearBy(randomAngle);
        return canSpawn;
    }

    public void restart() {
        skullPool.freeAll(skulls);
        skulls.clear();
    }

    public Array<Skull> getSkulls() {
        return skulls;
    }

    public void checkMonsterCollision(Skull skull, Monster monster) {

         if (monster.getState() == MonsterState.DASHING && Intersector.overlapConvexPolygons(monster.getPolygonCollider(), skull.getPolygonCollider())) {
            skull.setCurrentSkullState(GameConfig.ENEMY_DYING_STATE);

            // slug kills monster
        } else if (Intersector.overlapConvexPolygons(monster.getPolygonCollider(), skull.getPolygonCollider()) &&
                monster.getState() != MonsterState.DASHING) {
            soundListener.lose();

            monster.dead();
            controllerRegister.getMasterController().setGameState(GameState.GAME_OVER);
        }
    }

    @Override
    public void checkEnemyCollision(EnemyBase thisEnemy, Array<Skull> otherEnemies) {

    }
}
