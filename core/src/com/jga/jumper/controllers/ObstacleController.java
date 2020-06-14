package com.jga.jumper.controllers;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;
import com.jga.jumper.common.GameManager;
import com.jga.jumper.common.GameState;
import com.jga.jumper.common.SoundListener;
import com.jga.jumper.config.GameConfig;
import com.jga.jumper.entity.EntityBase;
import com.jga.jumper.entity.Monster;
import com.jga.jumper.entity.MonsterState;
import com.jga.jumper.entity.Obstacle;
import com.jga.jumper.object_distance_checker.DistanceChecker;
import com.jga.jumper.screen.game.MasterController;

public class ObstacleController {

    // == attributes ==
    private final Array<Obstacle> obstacles = new Array<>();
    private final Pool<Obstacle> obstaclePool = Pools.get(Obstacle.class, 10);
    private float obstacleTimer;
    private ControllerRegister controllerRegister;

    private SoundListener soundListener;

    // == constructors ==
    public ObstacleController(ControllerRegister controllerRegister, SoundListener soundListener) {
        this.controllerRegister = controllerRegister;
        this.soundListener = soundListener;
    }

    // == public methods
    public void update(float delta) {

        for (Obstacle obstacle : obstacles) {
            obstacle.update(delta);
        }

        spawnObstacles(delta);
    }

    public Array<Obstacle> getObstacles() {
        return obstacles;
    }

    public void spawnObstacles(float delta) {
        obstacleTimer += delta;

        if (obstacleTimer < GameConfig.OBSTACLE_SPAWN_TIME) {
            return;
        }
        obstacleTimer = 0;

        if (obstacles.size == 0) {
            addObstacles();
        }
    }

    public void addObstacles() {

        MonsterController monsterController = controllerRegister.getMonsterController();

        int count = MathUtils.random(2, GameConfig.MAX_OBSTACLES);
        Monster monster = monsterController.getMonsters().get(0);

        for (int i = 0; i < count; i++) {
            float randomAngle = monster.getAngleDegrees()
                    - i * GameConfig.MIN_ANG_DIST - MathUtils.random(60, 80);

            boolean canSpawn = !isObstacleNearby(randomAngle)
                    && !controllerRegister.getCoinController().isCoinNearby((randomAngle))
                    && !monsterController.isMonsterNearBy(randomAngle);
            if (canSpawn) {
                Obstacle obstacle = obstaclePool.obtain();
                obstacle.setAngleDegree(randomAngle);
                obstacles.add(obstacle);
            }
        }
    }


    public boolean isObstacleNearby(float angle) {
        DistanceChecker<Obstacle> obstacleDistanceChecker = new DistanceChecker<>(obstacles);
        return obstacleDistanceChecker.isEntityNearBy(angle);
    }

    public void checkCollision(EntityBase otherEntity) {

        for (int i = 0; i < obstacles.size; i++) {
            Obstacle obstacle = obstacles.get(i);
            Array<Monster> monsters = controllerRegister.getMonsterController().getMonsters();
            Monster monster = monsters.get(0);
            if (Intersector.overlaps(otherEntity.getBounds(), obstacle.getSensor())) {
                GameManager.INSTANCE.addScore(GameConfig.OBSTACLE_SCORE);
                controllerRegister.getFloatingScoreController().addFloatingScore(GameConfig.OBSTACLE_SCORE);
                obstaclePool.free(obstacle);
                obstacles.removeIndex(i);

            } else if (Intersector.overlaps(otherEntity.getBounds(), obstacle.getBounds()) &&
                    monster.getState() != MonsterState.DASHING) {
                soundListener.lose();

                monster.dead();
                controllerRegister.getMasterController().setGameState(GameState.GAME_OVER);
            }
        }
    }

    public void restart() {
        obstaclePool.freeAll(obstacles);
        obstacles.clear();
    }
}
