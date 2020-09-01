package com.jga.jumper.waves;

import com.jga.jumper.controllers.ControllerRegister;
import com.jga.jumper.controllers.SlugController;
import com.jga.jumper.entity.Slug;
import com.jga.jumper.entity.abstract_classes_and_interfaces.EnemyBase;

import java.util.ArrayList;
import java.util.List;

public class SlugWave implements Wave{

    private ControllerRegister controllerRegister;
    private SlugController slugController;
    private int numberOfEnemies;
    private float timer;
    private boolean waveNotSpawned;

    public SlugWave(ControllerRegister controllerRegister, int numberOfEnemies, float timer) {
        this.controllerRegister = controllerRegister;
        this.slugController = controllerRegister.getSlugController();
        this.numberOfEnemies = numberOfEnemies;
        this.timer = timer;
        this.waveNotSpawned = true;
    }

    @Override
    public void spawnEnemies() {
        if (waveNotSpawned) {
            waveNotSpawned = !waveNotSpawned;
            slugController.tryToAddSlugs(numberOfEnemies);
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
