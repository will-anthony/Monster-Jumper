package com.jga.jumper.controllers;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;
import com.jga.jumper.common.SoundListener;
import com.jga.jumper.config.GameConfig;
import com.jga.jumper.entity.Monster;
import com.jga.jumper.entity.Obstacle;
import com.jga.jumper.entity.Slug;
import com.jga.jumper.object_distance_checker.DistanceChecker;

public class SlugController {
    // == attributes ==
    private final Array<Slug> slugs = new Array<>();
    private final Pool<Slug> slugPool = Pools.get(Slug.class, 10);
    private float slugSpawnTimer;
    private ControllerRegister controllerRegister;

    private SoundListener soundListener;

    // == constructors ==
    public SlugController(ControllerRegister controllerRegister, SoundListener soundListener) {
        this.controllerRegister = controllerRegister;
        this.soundListener = soundListener;
    }

    // == public methods
    public void update(float delta) {

        for (Slug slug : slugs) {
            slug.update(delta);
        }

        spawnSlugs(delta);
    }

    public Array<Slug> getSlugs() {
        return slugs;
    }

    public void spawnSlugs(float delta) {
        slugSpawnTimer += delta;

        if (slugSpawnTimer < GameConfig.OBSTACLE_SPAWN_TIME) {
            return;
        }
        slugSpawnTimer = 0;

        if (slugs.size == 0) {
            addSlugs();
        }
    }

    public void addSlugs() {

        MonsterController monsterController = controllerRegister.getMonsterController();

        int count = MathUtils.random(2, GameConfig.MAX_OBSTACLES);
        Monster monster = monsterController.getMonsters().get(0);

        for (int i = 0; i < count; i++) {
            float randomAngle = monster.getAngleDegrees()
                    - i * GameConfig.MIN_ANG_DIST - MathUtils.random(60, 80);

            boolean canSpawn = !isSlugNearby(randomAngle)
                    && !controllerRegister.getCoinController().isCoinNearby((randomAngle))
                    && !monsterController.isMonsterNearBy(randomAngle);
            if (canSpawn) {
                Slug slug = slugPool.obtain();
                slug.setAngleDegree(randomAngle);
                slugs.add(slug);
            }
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
}
