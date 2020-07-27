package com.jga.jumper.controllers.projectiles;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;
import com.jga.jumper.common.SoundListener;
import com.jga.jumper.config.GameConfig;
import com.jga.jumper.controllers.ControllerRegister;
import com.jga.jumper.controllers.MonsterController;
import com.jga.jumper.entity.Bear;
import com.jga.jumper.entity.Monster;
import com.jga.jumper.entity.projectiles.SpikeTrap;
import com.jga.jumper.state_machines.GameState;
import com.jga.jumper.state_machines.MonsterState;

public class SpikeTrapController {

    private final Array<SpikeTrap> spikeTraps = new Array<>();
    private final Pool<SpikeTrap> spikeTrapPool = Pools.get(SpikeTrap.class, 10);
    private ControllerRegister controllerRegister;
    private MonsterController monsterController;

    private SoundListener soundListener;

    // == constructors ==
    public SpikeTrapController(ControllerRegister controllerRegister, SoundListener soundListener) {
        this.controllerRegister = controllerRegister;
        this.monsterController = controllerRegister.getMonsterController();
        this.soundListener = soundListener;
    }

    public void update(float delta) {
        SpikeTrap spikeTrap;

        for (int i = 0; i < spikeTraps.size; i++) {
            spikeTrap = spikeTraps.get(i);
            switch (spikeTrap.getCurrentSpikeTrapState()) {
                case 0:
                    // spawning
                    spikeTrapSpawnLogic(spikeTrap, delta);
                    break;
                case 1:
                    // idle
                    spikeTrapIdleLogic(spikeTrap, delta);
                    break;
                case 2:
                    // withdrawing
                    spikeTrapWithdrawLogic(spikeTrap);
                    break;
            }
        }
    }

    private void spikeTrapSpawnLogic(SpikeTrap spikeTrap, float delta) {
        float spawnDelayTimer = spikeTrap.getSpawnDelayTimer();

        if(spawnDelayTimer > 0) {
            spikeTrap.setSpawnDelayTimer(spawnDelayTimer - delta);
            return;
        }

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

    private void spikeTrapIdleLogic(SpikeTrap spikeTrap, float delta) {

        spikeTrap.setSpawnDelayTimer(1f);
        float spikeTrapTimer = spikeTrap.getSpikeTrapIdleTimer();
        spikeTrap.setSpikeTrapIdleTimer(spikeTrapTimer - delta);

        if (spikeTrapTimer <= 0) {
            spikeTrap.setCurrentSpikeTrapState(GameConfig.SPIKE_TRAP_WITHDRAW_STATE);
        }
        checkMonsterCollision(spikeTrap, monsterController.getMonsters().get(0));
        spikeTrap.setAngleDegree();
    }

    private void spikeTrapWithdrawLogic(SpikeTrap spikeTrap) {
        if (spikeTrap.getRadius() > GameConfig.PLANET_HALF_SIZE - GameConfig.MONSTER_SIZE) {
            spikeTrap.setRadius(spikeTrap.getRadius() - 0.5f);
        }
        if (spikeTrap.getRadius() <= GameConfig.PLANET_HALF_SIZE - GameConfig.MONSTER_SIZE) {
            enemyDeathLogic(spikeTrap);
        }

        checkMonsterCollision(spikeTrap, monsterController.getMonsters().get(0));
        spikeTrap.setAngleDegree();
    }

    private void enemyDeathLogic(SpikeTrap spikeTrap) {
        spikeTrapPool.free(spikeTrap);
        spikeTraps.removeValue(spikeTrap, true);
    }

    public void spawnSpikeTrap(Bear bear) {

        // set fire ball direction
        boolean isClockWise = bear.isClockWise();
        float spikeTrapSpawnOffset;

        spikeTrapSpawnOffset = (isClockWise) ? 24 : -8;

        float spawnAngle = bear.getAngleDegrees() + (spikeTrapSpawnOffset);

        SpikeTrap spikeTrap = spikeTrapPool.obtain();
        spikeTrap.setStartingPosition(spawnAngle);
        spikeTrap.setClockWise(isClockWise);
        spikeTraps.add(spikeTrap);
    }

    public void restart() {
        spikeTrapPool.freeAll(spikeTraps);
        spikeTraps.clear();
    }

    public Array<SpikeTrap> getSpikeTraps() {
        return spikeTraps;
    }

    public void checkMonsterCollision(SpikeTrap spikeTrap, Monster monster) {

        // spike trap kills monster
        if (Intersector.overlapConvexPolygons(monster.getPolygonCollider(), spikeTrap.getPolygonCollider()) &&
                monster.getState() != MonsterState.DASHING) {
            soundListener.lose();

            monster.dead();
            controllerRegister.getMasterController().setGameState(GameState.GAME_OVER);
        }
    }
}

