package com.jga.jumper.levels;

import com.jga.jumper.controllers.ControllerRegister;
import com.jga.jumper.controllers.MageController;
import com.jga.jumper.controllers.SlugController;

public class Level_7 implements Level {

    private final ControllerRegister controllerRegister;
    private final SlugController slugController;
    private final MageController mageController;

    private float levelTimer;
    private boolean levelBrake;

    private boolean hasFirstWaveSpawned;
    private boolean hasSecondWaveSpawned;
    private boolean hasThirdWaveSpawned;
    private boolean hasFourthWaveSpawned;
    private boolean hasFifthWaveSpawned;
    private boolean hasSixthWaveSpawned;

    private static final float FINAL_WAVE_TIME = 5f;

    public Level_7(ControllerRegister controllerRegister) {
        this.controllerRegister = controllerRegister;
        slugController = controllerRegister.getSlugController();
        mageController = controllerRegister.getMageController();
        levelTimer = 0f;
        levelBrake = false;
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
            System.out.println("Level 7");
            slugController.tryToAddSlugs(2);
            hasFirstWaveSpawned = true;
        }

        if (levelTimer >= 6 && !hasSecondWaveSpawned) {
            mageController.tryToAddMages(1);
            slugController.tryToAddSlugs(1);
            hasSecondWaveSpawned = true;
        }

//        if (levelTimer >= 10 && !hasThirdWaveSpawned) {
//            slugController.tryToAddSlugs(1);
//            hasThirdWaveSpawned = true;
//        }
//
//        if (levelTimer >= 12 && !hasFourthWaveSpawned) {
//            slugController.tryToAddSlugs(1);
//            hasFourthWaveSpawned = true;
//        }
//
//        if (levelTimer >= 16 && !hasFifthWaveSpawned) {
//            slugController.tryToAddSlugs(1);
//            hasFifthWaveSpawned = true;
//        }
//
//        if (levelTimer >= FINAL_WAVE_TIME && !hasSixthWaveSpawned) {
//            mageController.tryToAddMages(1);
//            slugController.tryToAddSlugs(1);
//            hasSixthWaveSpawned = true;
//        }

        levelTimer += delta;
    }

    @Override
    public boolean hasLevelFinished() {
        if (levelTimer >= FINAL_WAVE_TIME && levelBrake == false) {
            levelBrake = true;
            levelTimer = 0;
            System.out.println("Level completed");
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
