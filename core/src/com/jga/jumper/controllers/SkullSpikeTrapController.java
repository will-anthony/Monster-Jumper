package com.jga.jumper.controllers;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;
import com.jga.jumper.common.SoundListener;
import com.jga.jumper.config.GameConfig;
import com.jga.jumper.entity.Bear;
import com.jga.jumper.entity.Monster;
import com.jga.jumper.entity.Skull;
import com.jga.jumper.entity.projectiles.SkullSpikeTrap;
import com.jga.jumper.entity.projectiles.SpikeTrap;
import com.jga.jumper.state_machines.GameState;
import com.jga.jumper.state_machines.MonsterState;

public class SkullSpikeTrapController {

    private final Array<SkullSpikeTrap> spikeTraps = new Array<>();
    private final Pool<SkullSpikeTrap> spikeTrapPool = Pools.get(SkullSpikeTrap.class, 10);
    private ControllerRegister controllerRegister;
    private MonsterController monsterController;

    private SoundListener soundListener;

    // == constructors ==
    public SkullSpikeTrapController(ControllerRegister controllerRegister, SoundListener soundListener) {
        this.controllerRegister = controllerRegister;
        this.monsterController = controllerRegister.getMonsterController();
        this.soundListener = soundListener;
    }

    public void update(float delta) {
        SkullSpikeTrap spikeTrap;

        for (int i = 0; i < spikeTraps.size; i++) {
            spikeTrap = spikeTraps.get(i);
            switch (spikeTrap.getCurrentSpikeTrapState()) {
                case 0:
                    // spawning
                    spikeTrapSpawnLogic(spikeTrap, delta);
                    break;
                case 1:
                    // idle
                    spikeTrapIdleLogic(spikeTrap, spikeTrap.getParentSkull());
                    break;
                case 2:
                    // withdrawing
                    spikeTrapWithdrawLogic(spikeTrap);
                    break;
            }
        }
    }

    private void spikeTrapSpawnLogic(SkullSpikeTrap spikeTrap, float delta) {

        float spawnDelayTimer = spikeTrap.getSpawnDelayTimer();

        // delays the spawn of the trap
        if(spawnDelayTimer > 0) {
            spikeTrap.setSpawnDelayTimer(spawnDelayTimer - delta);

            // if the parent dies before trap is set, trap stops spawning
            if (spikeTrap.getParentSkull().getCurrentSkullState() == GameConfig.ENEMY_DYING_STATE) {
                spikeTrap.setCurrentSpikeTrapState(GameConfig.SPIKE_TRAP_WITHDRAW_STATE);
            }

            return;
        }

        // sets speed rap rises from the ground
        if (spikeTrap.getRadius() < GameConfig.PLANET_HALF_SIZE) {

            spikeTrap.setRadius(spikeTrap.getRadius() + 0.2f);
        }

        if (spikeTrap.getRadius() >= GameConfig.PLANET_HALF_SIZE) {
            spikeTrap.setRadius(GameConfig.PLANET_HALF_SIZE);
            spikeTrap.setCurrentSpikeTrapState(GameConfig.SPIKE_TRAP_IDLE_STATE);
        }

        checkMonsterCollision(spikeTrap, monsterController.getMonsters().get(0));
        spikeTrap.setAngleDegree();
    }

    private void spikeTrapIdleLogic(SkullSpikeTrap spikeTrap, Skull skull) {

        if (skull.getCurrentSkullState() == GameConfig.ENEMY_DEAD_STATE) {
            spikeTrap.setCurrentSpikeTrapState(GameConfig.SPIKE_TRAP_WITHDRAW_STATE);
        }
        checkMonsterCollision(spikeTrap, monsterController.getMonsters().get(0));
        spikeTrap.setAngleDegree();
    }

    private void spikeTrapWithdrawLogic(SkullSpikeTrap spikeTrap) {

        if (spikeTrap.getRadius() > GameConfig.PLANET_HALF_SIZE - GameConfig.MONSTER_SIZE) {
            spikeTrap.setRadius(spikeTrap.getRadius() - 0.2f);
        }
        if (spikeTrap.getRadius() <= GameConfig.PLANET_HALF_SIZE - GameConfig.MONSTER_SIZE) {
            controllerRegister.getTrapWarningSmokeController().spawnTrapWarningSmoke(
                    spikeTrap, 0,0,
                    GameConfig.TRAP_WARNING_SMOKE_WITHDRAW_STATE, GameConfig.PLANET_HALF_SIZE - 0.1f);

            enemyDeathLogic(spikeTrap);
        }

        checkMonsterCollision(spikeTrap, monsterController.getMonsters().get(0));
        spikeTrap.setAngleDegree();
    }

    private void enemyDeathLogic(SkullSpikeTrap spikeTrap) {
        spikeTrapPool.free(spikeTrap);
        spikeTraps.removeValue(spikeTrap, true);
    }

    public void spawnSkullSpikeTrap(Skull skull) {

        boolean isClockWise = skull.isClockWise();
        float spikeTrapSpawnOffset;

        spikeTrapSpawnOffset = (isClockWise) ? 24 : -8;

        float spawnAngle = skull.getAngleDegrees() + (spikeTrapSpawnOffset);

        SkullSpikeTrap spikeTrap = spikeTrapPool.obtain();
        spikeTrap.setStartingPosition(spawnAngle);
        spikeTrap.setClockWise(isClockWise);
        spikeTrap.setParentSkull(skull);
        spikeTraps.add(spikeTrap);
    }

    public void restart() {
        spikeTrapPool.freeAll(spikeTraps);
        spikeTraps.clear();
    }

    public Array<SkullSpikeTrap> getSpikeTraps() {
        return spikeTraps;
    }

    public void checkMonsterCollision(SkullSpikeTrap spikeTrap, Monster monster) {

        // spike trap kills monster
        if (Intersector.overlapConvexPolygons(monster.getPolygonCollider(), spikeTrap.getPolygonCollider()) &&
                monster.getState() != MonsterState.DASHING) {
            soundListener.lose();

            monster.dead();
            controllerRegister.getMasterController().setGameState(GameState.GAME_OVER);
        }
    }
}
