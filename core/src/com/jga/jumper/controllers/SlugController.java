package com.jga.jumper.controllers;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;
import com.jga.jumper.common.SoundListener;
import com.jga.jumper.config.GameConfig;
import com.jga.jumper.entity.EnemyBase;
import com.jga.jumper.entity.Monster;
import com.jga.jumper.entity.Slug;
import com.jga.jumper.object_distance_checker.DistanceChecker;
import com.jga.jumper.state_machines.GameState;
import com.jga.jumper.state_machines.MonsterState;

public class SlugController implements EnemyController<Slug> {
    // == attributes ==
    private final Array<Slug> slugs = new Array<>();
    private final Pool<Slug> slugPool = Pools.get(Slug.class, 10);
    private float slugSpawnTimer;
    private ControllerRegister controllerRegister;
    private MonsterController monsterController;

    private SoundListener soundListener;

    // == constructors ==
    public SlugController(ControllerRegister controllerRegister, SoundListener soundListener) {
        this.controllerRegister = controllerRegister;
        this.monsterController = controllerRegister.getMonsterController();
        this.soundListener = soundListener;
    }

    public void update(float delta) {
        Slug slug;

        for (int i = 0; i < slugs.size; i++) {
            slug = slugs.get(i);
            switch (slug.getCurrentSlugState()) {
                case 0:
                    // spawning
                    enemySpawnLogic(slug);
                    break;
                case 1:
                    // idle
                    break;
                case 2:
                    // walking
                    enemyWalkLogic(slug, delta);
                    break;
                case 3:
                    // attacking
                    enemyAttackLogic(slug, delta);
                    break;
                case 4:
                    // dying
                    enemyDyingLogic(slug, delta);
                    break;
                case 5:
                    // dead
                    enemyDeathLogic(slug);
                    break;
            }
        }
    }

    @Override
    public void enemySpawnLogic(Slug enemy) {
        // slug emerges from planet
        if (enemy.getRadius() < GameConfig.PLANET_HALF_SIZE) {
            enemy.setRadius(enemy.getRadius() + 0.01f);
        }
        if (enemy.getRadius() >= GameConfig.PLANET_HALF_SIZE) {
            enemy.setRadius(GameConfig.PLANET_HALF_SIZE);
            enemy.setCurrentSlugState(GameConfig.ENEMY_WALKING_STATE);
        }
    }

    @Override
    public void enemyWalkLogic(Slug enemy, float delta) {
        enemy.move(delta);
        checkMonsterCollision(enemy, monsterController.getMonsters().get(0));
        checkEnemyCollision(enemy, slugs);
    }

    @Override
    public void enemyAttackLogic(Slug enemy, float delta) {
        enemy.move(delta);
        checkMonsterCollision(enemy, monsterController.getMonsters().get(0));
        checkEnemyCollision(enemy, slugs);
    }

    @Override
    public void enemyDyingLogic(Slug enemy, float delta) {
        float deathTimer = enemy.getDeathTimer();
        System.out.println(deathTimer);
        if (deathTimer > 0) {
            enemy.setDeathTimer(deathTimer -= delta);
        }

        if (deathTimer <= 0) {
            enemy.setCurrentSlugState(GameConfig.ENEMY_DEAD_STATE);
        }
    }

    @Override
    public void enemyDeathLogic(Slug enemy) {
        slugPool.free(enemy);
        slugs.removeValue(enemy, true);
    }

    @Override
    public boolean isEnemyNearby(float angle) {
        DistanceChecker<Slug> slugDistanceChecker = new DistanceChecker<>(slugs);
        return slugDistanceChecker.isEntityNearBy(angle);
    }

    public void tryToAddSlugs(int numberOfEnemies) {

        for (int i = 0; i < numberOfEnemies; i++) {

            float randomAngle = MathUtils.random(0, 360);

            if (canSlugSpawn(randomAngle)) {
                Slug slug = slugPool.obtain();
                slug.setStartingPosition(randomAngle);
                slugs.add(slug);
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
        slugPool.freeAll(slugs);
        slugs.clear();
    }

    public Array<Slug> getSlugs() {
        return slugs;
    }

    public void checkMonsterCollision(Slug slug, Monster monster) {

        // monster kills slug with jump attack
        if (Intersector.overlaps(monster.getBounds(), slug.getSensor()) && monster.getState() == MonsterState.FALLING) {
            slug.setCurrentSlugState(GameConfig.ENEMY_DYING_STATE);
            monster.jump();

        } else if (monster.getState() == MonsterState.DASHING && Intersector.overlaps(monster.getBounds(), slug.getBounds())) {
            slug.setCurrentSlugState(GameConfig.ENEMY_DYING_STATE);

            // slug kills monster
        } else if (Intersector.overlaps(monster.getBounds(), slug.getBounds()) &&
                monster.getState() != MonsterState.DASHING) {
            soundListener.lose();

            monster.dead();
            controllerRegister.getMasterController().setGameState(GameState.GAME_OVER);
        }
    }

    private void checkEnemyDistanceCollision(Monster monster, Slug slug) {
        if(Intersector.overlaps(monster.getBounds(), slug.getBounds())) {
            slug.setCurrentSlugState(GameConfig.ENEMY_ATTACKING_STATE);
        }
    }


    public void checkEnemyCollision(EnemyBase enemyBase1, Array<Slug> enemyBases) {

        for (Slug slug : enemyBases) {

            if (Intersector.overlaps(enemyBase1.getBounds(), slug.getBounds())) {

                // flip
                if (enemyBase1.isClockWise()) {
                    enemyBase1.setClockWise(false);
                } else {
                    enemyBase1.setClockWise(true);
                }

                if (slug.isClockWise()) {
                    slug.setClockWise(false);
                } else {
                    slug.setClockWise(true);
                }
            }
        }
    }
}


