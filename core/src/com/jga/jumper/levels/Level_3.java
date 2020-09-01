package com.jga.jumper.levels;

import com.jga.jumper.controllers.ControllerRegister;
import com.jga.jumper.controllers.MageController;
import com.jga.jumper.controllers.SkullController;
import com.jga.jumper.controllers.SlugController;

public class Level_3 implements Level {

    private final ControllerRegister controllerRegister;
    private final SkullController skullController;

    private float levelTimer;
    private boolean levelBrake;

    private boolean hasFirstWaveSpawned;
    private boolean hasSecondWaveSpawned;
    private boolean hasThirdWaveSpawned;
    private boolean hasFourthWaveSpawned;
    private boolean hasFifthWaveSpawned;
    private boolean hasSixthWaveSpawned;

    private static final float FINAL_WAVE_TIME = 12f;

    public Level_3(ControllerRegister controllerRegister) {
        this.controllerRegister = controllerRegister;
        this.skullController = controllerRegister.getSkullController();
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
            System.out.println("Level 3");
            skullController.tryToAddSkulls(1);
            hasFirstWaveSpawned = true;
        }

        if (levelTimer >= 6 && !hasSecondWaveSpawned) {
            skullController.tryToAddSkulls(1);
            hasSecondWaveSpawned = true;
        }
//
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

        this.levelTimer += delta;
    }

    @Override
    public boolean hasLevelFinished() {
        if (levelTimer >= FINAL_WAVE_TIME && levelBrake == false) {
            levelTimer = 0;
            levelBrake = true;
            System.out.println("Level completed");
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void reset() {
        this.levelTimer = 0f;
        this.levelBrake = false;
        this.hasFirstWaveSpawned = false;
        this.hasSecondWaveSpawned = false;
        this.hasThirdWaveSpawned = false;
        this.hasFourthWaveSpawned = false;
        this.hasFifthWaveSpawned = false;
        this.hasSixthWaveSpawned = false;
    }
}
