package com.jga.jumper.waves;

import com.jga.jumper.controllers.ControllerRegister;
import com.jga.jumper.controllers.SkullController;

public class SkullWave implements Wave {

    private ControllerRegister controllerRegister;
    private SkullController skullController;
    private int numberOfEnemies;
    private float timer;
    private boolean waveNotSpawned;

    public SkullWave(ControllerRegister controllerRegister, int numberOfEnemies, float timer) {
        this.controllerRegister = controllerRegister;
        this.skullController = controllerRegister.getSkullController();
        this.numberOfEnemies = numberOfEnemies;
        this.timer = timer;
        this.waveNotSpawned = true;
    }

    @Override
    public void spawnEnemies() {
        if (waveNotSpawned) {
            waveNotSpawned = !waveNotSpawned;
            skullController.tryToAddSkulls(numberOfEnemies);
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
