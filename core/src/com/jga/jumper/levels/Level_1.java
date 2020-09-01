package com.jga.jumper.levels;

import com.jga.jumper.controllers.ControllerRegister;
import com.jga.jumper.controllers.MageController;
import com.jga.jumper.controllers.SkullController;
import com.jga.jumper.controllers.SlugController;

public class Level_1 implements Level {

    private final ControllerRegister controllerRegister;
    private final SlugController slugController;
    private final MageController mageController;
    private final SkullController skullController;

    private float levelTimer;
    private boolean levelBrake;

    private boolean hasFirstWaveSpawned;
    private boolean hasSecondWaveSpawned;
    private boolean hasThirdWaveSpawned;
    private boolean hasFourthWaveSpawned;

    private static final float FINAL_WAVE_TIME = 14f;

    public Level_1(ControllerRegister controllerRegister) {
        this.controllerRegister = controllerRegister;
        this.slugController = controllerRegister.getSlugController();
        this.mageController = controllerRegister.getMageController();
        this.skullController = controllerRegister.getSkullController();

        this.levelTimer = 0f;
        this.levelBrake = false;

        this.hasFirstWaveSpawned = false;
        this.hasSecondWaveSpawned = false;
        this.hasThirdWaveSpawned = false;
        this.hasFourthWaveSpawned = false;
    }

    @Override
    public void update(float delta) {
        if (levelTimer >= 0 && !hasFirstWaveSpawned) {
            System.out.println("Level 1");
            slugController.tryToAddSlugs(1);
            hasFirstWaveSpawned = true;
        }

        if (levelTimer >= 6 && !hasSecondWaveSpawned) {
            slugController.tryToAddSlugs(2);
            hasSecondWaveSpawned = true;
        }
//
//        if (levelTimer >= 10 && !hasThirdWaveSpawned) {
//            hasThirdWaveSpawned = true;
//        }
//
//        if (levelTimer >= FINAL_WAVE_TIME && !hasFourthWaveSpawned) {
//            slugController.tryToAddSlugs(1);
//            hasFourthWaveSpawned = true;
//        }

        levelTimer += delta;
    }

    @Override
    public boolean hasLevelFinished() {
        if(levelTimer >= FINAL_WAVE_TIME && levelBrake == false) {
            System.out.println("Level completed");
            levelBrake = true;
            levelTimer = 0;
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
    }
}
