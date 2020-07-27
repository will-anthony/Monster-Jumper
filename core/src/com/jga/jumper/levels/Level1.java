package com.jga.jumper.levels;

import com.jga.jumper.controllers.BearController;
import com.jga.jumper.controllers.ControllerRegister;
import com.jga.jumper.controllers.MageController;
import com.jga.jumper.controllers.RedController;
import com.jga.jumper.controllers.SkullController;
import com.jga.jumper.controllers.SlugBossController;
import com.jga.jumper.controllers.SlugController;

public class Level1 implements Level {

    private final ControllerRegister controllerRegister;
    private final SlugController slugController;
    private final SlugBossController slugBossController;
    private final BearController bearController;
    private final SkullController skullController;
    private final MageController mageController;
    private final RedController redController;

    private float levelTimer;

    private boolean hasFirstWaveSpawned;
    private boolean hasSecondWaveSpawned;
    private boolean hasThirdWaveSpawned;
    private boolean hasFourthWaveSpawned;
    private boolean hasFifthWaveSpawned;
    private boolean hasSixthWaveSpawned;

    private static final float FINAL_WAVE_TIME = 20f;

    public Level1(ControllerRegister controllerRegister) {
        this.controllerRegister = controllerRegister;
        slugController = controllerRegister.getSlugController();
        bearController = controllerRegister.getBearController();
        mageController = controllerRegister.getMageController();
        skullController = controllerRegister.getSkullController();
        slugBossController = controllerRegister.getSlugBossController();
        redController = controllerRegister.getRedController();

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
            mageController.tryToAddMages(1);
            slugController.tryToAddSlugs(2);
            skullController.tryToAddSkulls(1);
            hasFirstWaveSpawned = true;
        }

        if (levelTimer >= 6 && !hasSecondWaveSpawned) {
//            slugController.tryToAddSlugs(2);

            hasSecondWaveSpawned = true;
        }

        if (levelTimer >= 10 && !hasThirdWaveSpawned) {
//            slugBossController.tryToAddSlugBosses(1);
            hasThirdWaveSpawned = true;
        }

        if (levelTimer >= 12 && !hasFourthWaveSpawned) {
//            mageController.tryToAddMages(1);;
            hasFourthWaveSpawned = true;
        }

        if (levelTimer >= 16 && !hasFifthWaveSpawned) {
//            mageController.tryToAddMages(1);;
            hasFifthWaveSpawned = true;
        }

        if (levelTimer >= FINAL_WAVE_TIME && !hasSixthWaveSpawned) {
//            mageController.tryToAddMages(1);;
            hasSixthWaveSpawned = true;
        }

        levelTimer += delta;
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
