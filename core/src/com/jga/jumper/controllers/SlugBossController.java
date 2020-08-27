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
import com.jga.jumper.entity.SlugBoss;
import com.jga.jumper.object_distance_checker.DistanceChecker;
import com.jga.jumper.state_machines.GameState;
import com.jga.jumper.state_machines.MonsterState;

public class SlugBossController implements EnemyController<SlugBoss> {

    private final Array<SlugBoss> slugBosses = new Array<>();
    private final Pool<SlugBoss> slugBossPool = Pools.get(SlugBoss.class, 10);
    private ControllerRegister controllerRegister;
    private MonsterController monsterController;

    private SoundListener soundListener;

    // == constructors ==
    public SlugBossController(ControllerRegister controllerRegister, SoundListener soundListener) {
        this.controllerRegister = controllerRegister;
        this.monsterController = controllerRegister.getMonsterController();
        this.soundListener = soundListener;
    }

    public void update(float delta) {
        SlugBoss slugBoss;

        for (int i = 0; i < slugBosses.size; i++) {
            slugBoss = slugBosses.get(i);
            switch (slugBoss.getCurrentSlugBossState()) {

                case GameConfig.SLUG_BOSS_SPAWNING_STATE:
                    enemySpawnLogic(slugBoss);
                    break;

                case GameConfig.SLUG_BOSS_IDLE_STATE:
                    break;

                case GameConfig.SLUG_BOSS_WALKING_STATE:
                    enemyWalkLogic(slugBoss, delta);
                    break;

                case GameConfig.SLUG_BOSS_ATTACKING_HIGH_STATE:
                    enemyAttackLogic(slugBoss, delta);
                    break;

                case GameConfig.SLUG_BOSS_ATTACKING_LOW_STATE:
                    enemyAttackLogic(slugBoss, delta);
                    break;

                case GameConfig.SLUG_BOSS_SUMMON_STATE:
                    enemySummonLogic(slugBoss, delta);
                    break;

                case GameConfig.SLUG_BOSS_DAMAGED_STATE:
                    enemyDamagedLogic(slugBoss, delta);
                    break;

                case GameConfig.SLUG_BOSS_DYING_STATE:
                    enemyDyingLogic(slugBoss, delta);
                    break;

                case GameConfig.SLUG_BOSS_DEAD_STATE:
                    enemyDeathLogic(slugBoss);
                    break;

            }
        }
    }

    @Override
    public void enemySpawnLogic(SlugBoss enemy) {
        // slug emerges from planet
        if (enemy.getRadius() < GameConfig.PLANET_HALF_SIZE + GameConfig.SLUG_BOSS_ANIMATION_ADDITIONAL_RADIUS) {
            enemy.setRadius(enemy.getRadius() + 0.01f);
        }
        if (enemy.getRadius() >= GameConfig.PLANET_HALF_SIZE + GameConfig.SLUG_BOSS_ANIMATION_ADDITIONAL_RADIUS) {
            enemy.setRadius(GameConfig.PLANET_HALF_SIZE + GameConfig.SLUG_BOSS_ANIMATION_ADDITIONAL_RADIUS);
            enemy.setCurrentSlugBossState(GameConfig.ENEMY_WALKING_STATE);
        }
    }

    @Override
    public void enemyWalkLogic(SlugBoss enemy, float delta) {
        enemy.setSlugBossDamagedTimer(1f);
        enemy.setTurnOnAwarenessCollider(true);

        enemy.setTimeBetweenSummons(enemy.getTimeBetweenSummons() - delta);
        if(enemy.getTimeBetweenSummons() <= 0 ) {
            enemy.setCurrentSlugBossState(GameConfig.SLUG_BOSS_SUMMON_STATE);
        }
        enemy.move(delta);
        checkMonsterCollision(enemy, monsterController.getMonsters().get(0));
    }

    @Override
    public void enemyAttackLogic(SlugBoss enemy, float delta) {
        enemy.move(delta);
        checkMonsterCollision(enemy, monsterController.getMonsters().get(0));
        checkEnemyCollision(enemy, slugBosses);
        if(!monsterController.isMonsterNearBy(enemy.getAngleDegrees())) {
            enemy.setCurrentSlugBossState(GameConfig.SLUG_BOSS_WALKING_STATE);
        }
    }

    public void enemySummonLogic(SlugBoss slugBoss, float delta) {
        checkMonsterCollision(slugBoss, monsterController.getMonsters().get(0));
        slugBoss.setTimeBetweenSummons(MathUtils.random(3f, 6f));
        if(slugBoss.isSummoningChildSlug()) {
            controllerRegister.getSlugController().spawnSingleSlugAtLocation(slugBoss);
            slugBoss.setSummoningChildSlug(false);
            slugBoss.setCurrentSlugBossState(GameConfig.SLUG_BOSS_WALKING_STATE);
        }
    }

    public void enemyDamagedLogic(SlugBoss slugBoss, float delta) {
        slugBoss.setSlugBossDamagedTimer(slugBoss.getSlugBossDamagedTimer() - delta);
        if (slugBoss.getSlugBossDamagedTimer() <= 0 && slugBoss.getHitPoints() != 0) {
            slugBoss.setCurrentSlugBossState(GameConfig.SLUG_BOSS_WALKING_STATE);
        }
        if (slugBoss.getSlugBossDamagedTimer() <= 0 && slugBoss.getHitPoints() == 0) {
            slugBoss.setCurrentSlugBossState(GameConfig.SLUG_BOSS_WALKING_STATE);
        }
    }

    @Override
    public void enemyDyingLogic(SlugBoss enemy, float delta) {
        float deathTimer = enemy.getDeathTimer();
        System.out.println(deathTimer);
        if (deathTimer > 0) {
            enemy.setDeathTimer(deathTimer -= delta);
        }

        if (deathTimer <= 0) {
            controllerRegister.getCoinController().spawnCoins(enemy, 2);
            enemy.setCurrentSlugBossState(GameConfig.SLUG_BOSS_DEAD_STATE);
        }
    }

    @Override
    public void enemyDeathLogic(SlugBoss enemy) {
        controllerRegister.getTrapWarningSmokeController().spawnTrapWarningSmoke(
                enemy, 0,0,
                GameConfig.TRAP_WARNING_SMOKE_WITHDRAW_STATE, GameConfig.PLANET_HALF_SIZE - 0.1f);
        slugBossPool.free(enemy);
        slugBosses.removeValue(enemy, true);
    }

    @Override
    public boolean isEnemyNearby(float angle) {
        DistanceChecker<SlugBoss> slugDistanceChecker = new DistanceChecker<>(slugBosses);
        return slugDistanceChecker.isEntityNearBy(angle);
    }

    public void tryToAddSlugBosses(int numberOfEnemies) {

        for (int i = 0; i < numberOfEnemies; i++) {

            float randomAngle = MathUtils.random(0, 360);

            if (canSlugSpawn(randomAngle)) {
                SlugBoss slugBoss = slugBossPool.obtain();
                slugBoss.setStartingPosition(randomAngle);
                slugBosses.add(slugBoss);
            } else {
                numberOfEnemies++;
            }
        }
    }

    private boolean canSlugSpawn(float randomAngle) {
        boolean canSpawn = !isEnemyNearby(randomAngle)
                && !monsterController.isMonsterNearBy(randomAngle);
        return canSpawn;
    }

    public void restart() {
        slugBossPool.freeAll(slugBosses);
        slugBosses.clear();
    }

    public Array<SlugBoss> getSlugBosses() {
        return slugBosses;
    }

    public void checkMonsterCollision(SlugBoss slugBoss, Monster monster) {

        // monster kills slug with jump attack
        if (Intersector.overlapConvexPolygons(monster.getPolygonCollider(), slugBoss.getKillCollider()) && monster.getState() == MonsterState.FALLING) {
            if (slugBoss.getHitPoints() > 0) {
                slugBoss.setHitPoints(slugBoss.getHitPoints() - 1);
                slugBoss.setCurrentSlugBossState(GameConfig.ENEMY_DAMAGED_STATE);

            } else {
                slugBoss.setCurrentSlugBossState(GameConfig.SLUG_BOSS_DYING_STATE);
            }
            monster.setAcceleration(GameConfig.MONSTER_BOUNCE_ACCELERATION);
            controllerRegister.getTrapWarningSmokeController().spawnTrapWarningSmoke(
                    monster,0,0, GameConfig.TRAP_WARNING_SMOKE_WITHDRAW_STATE,
                    GameConfig.PLANET_HALF_SIZE + 1.35f
            );
            monster.jump();

        } else if (monster.getState() == MonsterState.DASHING && Intersector.overlapConvexPolygons(monster.getPolygonCollider(), slugBoss.getPolygonCollider())) {

            if (slugBoss.getHitPoints() > 0) {
                slugBoss.setHitPoints(slugBoss.getHitPoints() - 1);
                slugBoss.setCurrentSlugBossState(GameConfig.ENEMY_DAMAGED_STATE);

            } else {
                slugBoss.setCurrentSlugBossState(GameConfig.SLUG_BOSS_DYING_STATE);
            }

        } else if (Intersector.overlapConvexPolygons(monster.getPolygonCollider(), slugBoss.getAwarenessCollider()) &&
        slugBoss.isTurnOnAwarenessCollider() && slugBoss.getCurrentSlugBossState() == GameConfig.SLUG_BOSS_WALKING_STATE) {

            slugBoss.setRandomAttack(MathUtils.random(3,4));
            slugBoss.setCurrentSlugBossState(slugBoss.getRandomAttack());

            slugBoss.setTurnOnAwarenessCollider(false);

            // slug kills monster
        } else if (Intersector.overlapConvexPolygons(monster.getPolygonCollider(), slugBoss.getPolygonCollider()) &&
                monster.getState() != MonsterState.DASHING) {
            soundListener.lose();

            monster.dead();
            controllerRegister.getMasterController().setGameState(GameState.GAME_OVER);
        }
    }

    @Override
    public void checkEnemyCollision(EnemyBase thisEnemy, Array<SlugBoss> otherEnemies) {

    }
}
