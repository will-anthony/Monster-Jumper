package com.jga.jumper.controllers;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;
import com.jga.jumper.common.SoundListener;
import com.jga.jumper.config.GameConfig;
import com.jga.jumper.entity.EntityBase;
import com.jga.jumper.entity.Monster;
import com.jga.jumper.entity.Slug;
import com.jga.jumper.object_distance_checker.DistanceChecker;
import com.jga.jumper.state_machines.GameState;
import com.jga.jumper.state_machines.MonsterState;

public class SlugController {
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
            System.out.println(slug);

            switch (slug.getCurrentSlugState()) {
                case 0:
                    // spawning
                    slugSpawnLogic(slug);
                    break;
                case 1:
                    // idle
                    break;
                case 2:
                    // walking
                    slug.move(delta);
                    checkCollision(monsterController.getMonsters().get(0));
                    break;
                case 3:
                    // dying
                    float deathTimer = slug.getDeathTimer();
                    if(deathTimer > 0) {
                        slug.setDeathTimer(deathTimer -= delta);
                    }

                    if(deathTimer <= 0) {
                        slug.setCurrentSlugState(4);
                    }
                    break;
                case 4:
                    // dead
                    slugPool.free(slug);
                    slugs.removeIndex(i);
                    break;
            }
        }
        spawnSlugs(delta);
    }

    public void spawnSlugs(float delta) {
        slugSpawnTimer += delta;

        if (slugSpawnTimer < GameConfig.OBSTACLE_SPAWN_TIME) {
            return;
        }
        slugSpawnTimer = 0;

        if (slugs.size == 0) {
            tryToAddSlugs();
        }
    }

    public void tryToAddSlugs() {

        int count = MathUtils.random(2, GameConfig.MAX_OBSTACLES);

        for (int i = 0; i < count; i++) {

            float randomAngle = MathUtils.random(0, 360);

            if (canSlugSpawn(randomAngle)) {
                Slug slug = slugPool.obtain();
                slug.setStartingPosition(randomAngle);
                slugs.add(slug);
            }
        }
    }

    private boolean canSlugSpawn(float randomAngle) {
        boolean canSpawn = !isSlugNearby(randomAngle)
                && !monsterController.isMonsterNearBy(randomAngle);
        return canSpawn;
    }

    private void slugSpawnLogic(Slug slug) {
        // slug emerges from planet
        if (slug.getRadius() < GameConfig.PLANET_HALF_SIZE) {
            slug.setRadius(slug.getRadius() + 0.01f);
        }
        if (slug.getRadius() >= GameConfig.PLANET_HALF_SIZE) {
            slug.setRadius(GameConfig.PLANET_HALF_SIZE);
            slug.setCurrentSlugState(2);
        }
    }

    public boolean isSlugNearby(float angle) {
        DistanceChecker<Slug> slugDistanceChecker = new DistanceChecker<>(slugs);
        return slugDistanceChecker.isEntityNearBy(angle);
    }

    public void restart() {
        slugPool.freeAll(slugs);
        slugs.clear();
    }

    public Array<Slug> getSlugs() {
        return slugs;
    }

    public void checkCollision(EntityBase otherEntity) {
        for (int i = 0; i < slugs.size; i++) {
            Slug slug = slugs.get(i);
            Array<Monster> monsters = controllerRegister.getMonsterController().getMonsters();
            Monster monster = monsters.get(0);
            if (Intersector.overlaps(otherEntity.getBounds(), slug.getSensor()) ||
                    monster.getState() == MonsterState.DASHING && Intersector.overlaps(otherEntity.getBounds(), slug.getBounds())) {
                slug.setCurrentSlugState(3);
                monster.jump();
            } else if (Intersector.overlaps(otherEntity.getBounds(), slug.getBounds()) &&
                    monster.getState() != MonsterState.DASHING) {
                soundListener.lose();

                monster.dead();
                controllerRegister.getMasterController().setGameState(GameState.GAME_OVER);
            }
        }
    }
}

