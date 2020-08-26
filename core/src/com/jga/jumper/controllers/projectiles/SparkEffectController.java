package com.jga.jumper.controllers.projectiles;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;
import com.jga.jumper.common.SoundListener;
import com.jga.jumper.config.GameConfig;
import com.jga.jumper.controllers.ControllerRegister;
import com.jga.jumper.controllers.MonsterController;
import com.jga.jumper.entity.Monster;
import com.jga.jumper.entity.abstract_classes_and_interfaces.EntityBase;
import com.jga.jumper.entity.smoke_effects.SparkEffect;
import com.jga.jumper.entity.smoke_effects.TrapWarningSmoke;

public class SparkEffectController <T extends EntityBase> {

    private final Array<SparkEffect> sparks = new Array<>();
    private final Pool<SparkEffect> sparkPool = Pools.get(SparkEffect.class, 10);
    private ControllerRegister controllerRegister;
    private MonsterController monsterController;

    private SoundListener soundListener;

    // == constructors ==
    public SparkEffectController(ControllerRegister controllerRegister, SoundListener soundListener) {
        this.controllerRegister = controllerRegister;
        this.monsterController = controllerRegister.getMonsterController();
        this.soundListener = soundListener;
    }

    public void update(float delta) {
        SparkEffect spark;

        for (int i = 0; i < sparks.size; i++) {
            spark = sparks.get(i);
            switch (spark.getCurrentState()) {
                case 0:
                    // spawning
                    spawnLogic(spark, delta);
                    break;

                case 1:
                    // withdrawing
                    withdrawLogic(spark, delta);
                    break;

                case 2:
                    // death
                    deathLogic(spark);
            }
        }
    }

    private void spawnLogic(SparkEffect spark, float delta) {

        float sparkSpawnTimer = spark.getSpawnTimer();

        spark.setSpawnTimer(sparkSpawnTimer + delta);

        if(sparkSpawnTimer >= GameConfig.MAGE_ATTACK_TIMER) {

        }

        spark.setAngleDegree();
    }

    private void withdrawLogic (SparkEffect spark, float delta) {

        float sparkWithdrawTimer = spark.getWithdrawTimer();

        spark.setWithdrawTimer(sparkWithdrawTimer - delta);

        if(sparkWithdrawTimer <= 0) {
            spark.setCurrentState(GameConfig.TRAP_WARNING_SMOKE_DEAD_STATE);
        }

        spark.setAngleDegree();
    }

    private void deathLogic(SparkEffect spark) {
        sparkPool.free(spark);
        sparks.removeValue(spark, true);
    }

    public void spawnSparks(T entity, int clockwiseOffset, int antiClockwiseOffset, float radius) {

        boolean isClockWise = entity.isClockWise();
        float trapWarningSmokeSpawnOffset = (isClockWise) ? clockwiseOffset : antiClockwiseOffset;

        float spawnAngle = entity.getAngleDegrees() + (trapWarningSmokeSpawnOffset);

        SparkEffect spark = sparkPool.obtain();
        spark.setStartingPosition(spawnAngle);
        spark.setRadius(radius);
        spark.setClockWise(isClockWise);
        sparks.add(spark);
    }

    public void restart() {
        sparkPool.freeAll(sparks);
        sparks.clear();
    }

    public Array<SparkEffect> getSparks() {
        return sparks;
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
