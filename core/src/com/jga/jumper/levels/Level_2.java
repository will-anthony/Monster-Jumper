package com.jga.jumper.levels;

import com.jga.jumper.controllers.ControllerRegister;
import com.jga.jumper.controllers.SlugBossController;
import com.jga.jumper.controllers.SlugController;
import com.jga.jumper.entity.SlugBoss;

public class Level_2 implements Level {

    private final ControllerRegister controllerRegister;
    private final SlugController slugController;

    private float levelTimer;

    private boolean hasFirstWaveSpawned;
    private boolean hasSecondWaveSpawned;
    private boolean hasThirdWaveSpawned;
    private boolean hasFourthWaveSpawned;
    private boolean hasFifthWaveSpawned;
    private boolean hasSixthWaveSpawned;

    private static final float FINAL_WAVE_TIME = 10f;

    public Level_2(ControllerRegister controllerRegister) {
        this.controllerRegister = controllerRegister;
        slugController = controllerRegister.getSlugController();

        levelTimer = 0f;
        hasFirstWaveSpawned = false;
        hasSecondWaveSpawned = false;
        hasThirdWaveSpawned = false;
        hasFourthWaveSpawned = false;
        hasFifthWaveSpawned = false;
        hasSixthWaveSpawned = false;
    }

    @Override
    public void update(float delta) {
        if (levelTimer >= 0 && !hasFirstWaveSpawned) {
            slugController.tryToAddSlugs(2);
            hasFirstWaveSpawned = true;
        }

        if (levelTimer >= 6 && !hasSecondWaveSpawned) {
            slugController.tryToAddSlugs(3);
            hasSecondWaveSpawned = true;
        }
//
//        if (levelTimer >= 10 && !hasThirdWaveSpawned) {
//            slugController.tryToAddSlugs(1);
//            hasThirdWaveSpawned = true;
//        }
//
//        if (levelTimer >= 12 && !hasFourthWaveSpawned) {
//            hasFourthWaveSpawned = true;
//        }
//
//        if (levelTimer >= 16 && !hasFifthWaveSpawned) {
//            hasFifthWaveSpawned = true;
//        }
//
//        if (levelTimer >= FINAL_WAVE_TIME && !hasSixthWaveSpawned) {
//            slugController.tryToAddSlugs(2);
//            hasSixthWaveSpawned = true;
//        }

        levelTimer += delta;
        System.out.println(levelTimer);
    }

    @Override
    public boolean hasLevelFinished() {
        if(levelTimer >= FINAL_WAVE_TIME && slugController.getSlugs().size == 0) {
            System.out.println("Level completed");
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void reset() {
        levelTimer = 0f;
        hasFirstWaveSpawned = false;
        hasSecondWaveSpawned = false;
        hasThirdWaveSpawned = false;
        hasFourthWaveSpawned = false;
        hasFifthWaveSpawned = false;
        hasSixthWaveSpawned = false;
    }
}
