package com.jga.jumper.levels;

import com.jga.jumper.controllers.ControllerRegister;
import com.jga.jumper.controllers.SlugBossController;
import com.jga.jumper.controllers.SlugController;
import com.jga.jumper.entity.SlugBoss;

public class Level_2 implements Level {

    private final ControllerRegister controllerRegister;
    private final SlugController slugController;

    private float levelTimer;
    private boolean levelBrake;

    private boolean hasFirstWaveSpawned;
    private boolean hasSecondWaveSpawned;
    private boolean hasThirdWaveSpawned;
    private boolean hasFourthWaveSpawned;
    private boolean hasFifthWaveSpawned;
    private boolean hasSixthWaveSpawned;

    private static final float FINAL_WAVE_TIME = 10f;

    public Level_2(ControllerRegister controllerRegister) {
        this.controllerRegister = controllerRegister;
        this.slugController = controllerRegister.getSlugController();

        this.levelTimer = 0f;
        this.levelBrake = false;
        this.hasFirstWaveSpawned = false;
        this.hasSecondWaveSpawned = false;
        this.hasThirdWaveSpawned = false;
        this.hasFourthWaveSpawned = false;
        this.hasFifthWaveSpawned = false;
        this.hasSixthWaveSpawned = false;
    }

    @Override
    public void update(float delta) {
        if (levelTimer >= 0 && !hasFirstWaveSpawned) {
            System.out.println("Level 2");
            this.slugController.tryToAddSlugs(2);
            this.hasFirstWaveSpawned = true;
        }

        if (levelTimer >= 6 && !hasSecondWaveSpawned) {
            this.slugController.tryToAddSlugs(3);
            this.hasSecondWaveSpawned = true;
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
    }

    @Override
    public boolean hasLevelFinished() {
        if(levelTimer >= FINAL_WAVE_TIME && levelBrake == false) {
            System.out.println("Level completed");
            levelTimer = 0;
            levelBrake = true;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void reset() {
        levelTimer = 0f;
        levelBrake = false;
        hasFirstWaveSpawned = false;
        hasSecondWaveSpawned = false;
        hasThirdWaveSpawned = false;
        hasFourthWaveSpawned = false;
        hasFifthWaveSpawned = false;
        hasSixthWaveSpawned = false;
    }
}
