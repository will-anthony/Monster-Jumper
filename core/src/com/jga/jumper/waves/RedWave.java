package com.jga.jumper.waves;

import com.jga.jumper.controllers.ControllerRegister;
import com.jga.jumper.controllers.RedController;

public class RedWave implements Wave {
    private ControllerRegister controllerRegister;
    private RedController redController;
    private int numberOfEnemies;
    private float timer;
    private boolean waveNotSpawned;

    public RedWave(ControllerRegister controllerRegister, int numberOfEnemies, float timer) {
        this.controllerRegister = controllerRegister;
        this.redController = controllerRegister.getRedController();
        this.numberOfEnemies = numberOfEnemies;
        this.timer = timer;
        this.waveNotSpawned = true;
    }

    @Override
    public void spawnEnemies() {
        if (waveNotSpawned) {
            waveNotSpawned = !waveNotSpawned;
            redController.tryToAddReds(numberOfEnemies);
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
