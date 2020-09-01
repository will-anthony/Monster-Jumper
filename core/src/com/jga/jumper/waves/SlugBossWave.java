package com.jga.jumper.waves;

import com.jga.jumper.controllers.ControllerRegister;
import com.jga.jumper.controllers.SlugBossController;
import com.jga.jumper.controllers.SlugController;

public class SlugBossWave implements Wave{

    private ControllerRegister controllerRegister;
    private SlugBossController slugBossController;
    private int numberOfEnemies;
    private float timer;
    private boolean waveNotSpawned;

    public SlugBossWave(ControllerRegister controllerRegister, int numberOfEnemies, float timer) {
        this.controllerRegister = controllerRegister;
        this.slugBossController = controllerRegister.getSlugBossController();
        this.numberOfEnemies = numberOfEnemies;
        this.timer = timer;
        this.waveNotSpawned = true;
    }

    @Override
    public void spawnEnemies() {
        if (waveNotSpawned) {
            waveNotSpawned = !waveNotSpawned;
            slugBossController.tryToAddSlugBosses(numberOfEnemies);
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
