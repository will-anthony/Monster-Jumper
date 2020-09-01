package com.jga.jumper.waves;

import com.jga.jumper.controllers.ControllerRegister;
import com.jga.jumper.controllers.BearController;
import com.jga.jumper.controllers.SlugController;

public class BearWave implements Wave {
    private ControllerRegister controllerRegister;
    private BearController bearController;
    private int numberOfEnemies;
    private float timer;
    private boolean waveNotSpawned;

    public BearWave(ControllerRegister controllerRegister, int numberOfEnemies, float timer) {
        this.controllerRegister = controllerRegister;
        this.bearController = controllerRegister.getBearController();
        this.numberOfEnemies = numberOfEnemies;
        this.timer = timer;
        this.waveNotSpawned = true;
    }

    @Override
    public void spawnEnemies() {
        if (waveNotSpawned) {
            waveNotSpawned = !waveNotSpawned;
            bearController.tryToAddBears(numberOfEnemies);
        }
    }

    @Override
    public void reduceTimer(float delta) {
        this.timer -= delta;
    }

    @Override
    public float getTimer() {
        return timer;
    }
}
