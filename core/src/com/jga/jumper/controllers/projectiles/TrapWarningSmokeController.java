package com.jga.jumper.controllers.projectiles;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;
import com.jga.jumper.common.SoundListener;
import com.jga.jumper.config.GameConfig;
import com.jga.jumper.controllers.ControllerRegister;
import com.jga.jumper.controllers.MonsterController;
import com.jga.jumper.entity.abstract_classes_and_interfaces.EntityBase;
import com.jga.jumper.entity.Monster;
import com.jga.jumper.entity.smoke_effects.TrapWarningSmoke;

public class TrapWarningSmokeController<T extends EntityBase> {
    
    private final Array<TrapWarningSmoke> smokes = new Array<>();
    private final Pool<TrapWarningSmoke> smokePool = Pools.get(TrapWarningSmoke.class, 10);
    private ControllerRegister controllerRegister;
    private MonsterController monsterController;

    private SoundListener soundListener;

    // == constructors ==
    public TrapWarningSmokeController(ControllerRegister controllerRegister, SoundListener soundListener) {
        this.controllerRegister = controllerRegister;
        this.monsterController = controllerRegister.getMonsterController();
        this.soundListener = soundListener;
    }

    public void update(float delta) {
        TrapWarningSmoke smoke;

        for (int i = 0; i < smokes.size; i++) {
            smoke = smokes.get(i);
            switch (smoke.getCurrentState()) {
                case 0:
                    // spawning
                    spawnLogic(smoke, delta);
                    break;

                case 1:
                    // withdrawing
                    withdrawLogic(smoke, delta);
                    break;

                case 2:
                    // death
                    deathLogic(smoke);
            }
        }
    }

    private void spawnLogic(TrapWarningSmoke smoke, float delta) {

        float smokeSpawnTimer = smoke.getSpawnTimer();

        smoke.setSpawnTimer(smokeSpawnTimer + delta);

        if(smokeSpawnTimer >= GameConfig.TRAP_WARNING_SMOKE_SPAWN_TIME) {
            smoke.setCurrentState(GameConfig.TRAP_WARNING_SMOKE_WITHDRAW_STATE);
        }

        smoke.setAngleDegree();
    }

    private void withdrawLogic (TrapWarningSmoke smoke, float delta) {

        float smokeWithdrawTimer = smoke.getWithdrawTimer();

        smoke.setWithdrawTimer(smokeWithdrawTimer - delta);

        if(smokeWithdrawTimer <= 0) {
            smoke.setCurrentState(GameConfig.TRAP_WARNING_SMOKE_DEAD_STATE);
        }

        smoke.setAngleDegree();
    }

    private void deathLogic(TrapWarningSmoke trapWarningSmoke) {
        smokePool.free(trapWarningSmoke);
        smokes.removeValue(trapWarningSmoke, true);
    }

    public void spawnTrapWarningSmoke(T entity, int clockwiseOffset, int antiClockwiseOffset, int smokeState, float radius) {

        boolean isClockWise = entity.isClockWise();
        float trapWarningSmokeSpawnOffset = (isClockWise) ? clockwiseOffset : antiClockwiseOffset;

        float spawnAngle = entity.getAngleDegrees() + (trapWarningSmokeSpawnOffset);

        TrapWarningSmoke smoke = smokePool.obtain();
        smoke.setStartingPosition(spawnAngle);
        smoke.setRadius(radius);
        smoke.setClockWise(isClockWise);
        smoke.setCurrentState(smokeState);
        smokes.add(smoke);
    }

    public void restart() {
        smokePool.freeAll(smokes);
        smokes.clear();
    }

    public Array<TrapWarningSmoke> getTrapWarningSmokes() {
        return smokes;
    }

    public void checkMonsterCollision(TrapWarningSmoke trapWarningSmoke, Monster monster) {

//        // spike trap kills monster
//        if (Intersector.overlapConvexPolygons(monster.getPolygonCollider(), trapWarningSmoke.getPolygonCollider()) &&
//                monster.getState() != MonsterState.DASHING) {
//            soundListener.lose();
//
//            monster.dead();
//            controllerRegister.getMasterController().setGameState(GameState.GAME_OVER);
//        }
    }
}

