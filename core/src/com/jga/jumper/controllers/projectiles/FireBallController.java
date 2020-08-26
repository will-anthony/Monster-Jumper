package com.jga.jumper.controllers.projectiles;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;
import com.jga.jumper.common.SoundListener;
import com.jga.jumper.config.GameConfig;
import com.jga.jumper.controllers.ControllerRegister;
import com.jga.jumper.controllers.MonsterController;
import com.jga.jumper.entity.Monster;
import com.jga.jumper.entity.Red;
import com.jga.jumper.entity.projectiles.FireBall;
import com.jga.jumper.state_machines.GameState;
import com.jga.jumper.state_machines.MonsterState;

public class FireBallController {

    // == attributes ==
    private final Array<FireBall> fireBalls = new Array<>();
    private final Pool<FireBall> fireBallPool = Pools.get(FireBall.class, 10);
    private ControllerRegister controllerRegister;
    private MonsterController monsterController;

    private SoundListener soundListener;

    // == constructors ==
    public FireBallController(ControllerRegister controllerRegister, SoundListener soundListener) {
        this.controllerRegister = controllerRegister;
        this.monsterController = controllerRegister.getMonsterController();
        this.soundListener = soundListener;
    }

    public void update(float delta) {
        FireBall fireBall;

        for (int i = 0; i < fireBalls.size; i++) {
            fireBall = fireBalls.get(i);
            switch (fireBall.getCurrentFireBallState()) {
                case 0:
                    // spawning
                    projectileSpawnLogic(fireBall, delta);
                    if(fireBall.getRedParent().getCurrentState() == GameConfig.ENEMY_DYING_STATE) {
                        fireBall.setCurrentFireBallState(GameConfig.PROJECTILE_DYING_STATE);
                    }
                    break;
                case 1:
                    // moving
                    projectileMoveLogic(fireBall, delta);
                    if(fireBall.getRedParent().getCurrentState() == GameConfig.ENEMY_DYING_STATE) {
                        fireBall.setCurrentFireBallState(GameConfig.PROJECTILE_DYING_STATE);
                    }
                    break;
                case 2:
                    // dying
                    projectileDyingLogic(fireBall, delta);
                    break;
                case 3:
                    // death
                    projectileDeathLogic(fireBall);
                    break;
            }
        }
    }

    public void projectileSpawnLogic(FireBall fireBall, float delta) {
        float fireBallTimer = fireBall.getSpawnTimer();
        fireBall.setSpawnTimer(fireBallTimer -= delta);

        if (fireBallTimer <= 0) {
            fireBall.setCurrentFireBallState(GameConfig.PROJECTILE_MOVING_STATE);
        }
    }

    public void projectileMoveLogic(FireBall fireBall, float delta) {

        float fireBallLifeTimer = fireBall.getLifeTimer();

        fireBall.move(delta);
        fireBall.setLifeTimer(fireBallLifeTimer -= delta);
        checkMonsterCollision(fireBall, monsterController.getMonsters().get(0));
        //checkEnemyCollision(enemy, slugs);

        if (fireBallLifeTimer <= 0) {
            fireBall.setCurrentFireBallState(GameConfig.PROJECTILE_DYING_STATE);
        }
    }

    public void projectileDyingLogic(FireBall fireball, float delta) {
        float deathTimer = fireball.getDeathTimer();

        fireball.move(delta);
        System.out.println(deathTimer);
        if (deathTimer > 0) {
            fireball.setDeathTimer(deathTimer -= delta);
        }

        if (deathTimer <= 0) {
            fireball.setCurrentFireBallState(GameConfig.PROJECTILE_DEATH_STATE);
        }
    }

    public void projectileDeathLogic(FireBall fireBall) {
        fireBallPool.free(fireBall);
        fireBalls.removeValue(fireBall, true);
    }

    public void spawnProjectile(Red enemy) {

        // set fire ball direction
        boolean isClockWise = enemy.isClockWise();
        float fireBallSpawnOffset;

        fireBallSpawnOffset = (isClockWise) ? 20 : -3;

        float spawnAngle = enemy.getAngleDegrees() + (fireBallSpawnOffset);

        FireBall fireBall = fireBallPool.obtain();
        fireBall.setStartingPosition(spawnAngle);
        fireBall.setClockWise(isClockWise);
        fireBall.setRedParent(enemy);
        fireBalls.add(fireBall);
    }


    public void restart() {
        fireBallPool.freeAll(fireBalls);
        fireBalls.clear();
    }

    public Array<FireBall> getFireBalls() {
        return fireBalls;
    }

    public void checkMonsterCollision(FireBall fireBall, Monster monster) {

//        // projectile kills monster
        if (Intersector.overlapConvexPolygons(monster.getPolygonCollider(), fireBall.getPolygonCollider()) &&
                monster.getState() != MonsterState.DASHING) {
            soundListener.lose();

            monster.dead();
            controllerRegister.getMasterController().setGameState(GameState.GAME_OVER);
        }
    }
}
