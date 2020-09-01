package com.jga.jumper.waves;

import com.jga.jumper.controllers.ControllerRegister;

import java.util.Collections;
import java.util.LinkedList;

public class WaveController {

    private final ControllerRegister controllerRegister;
    private LinkedList<Wave> waves;
    private int gameLevelDisplay;

    private boolean secondWaveGroupAdded;
    private boolean thirdWaveGroupAdded;
    private boolean fourthWaveGroupAdded;
    private boolean fifthWaveGroupAdded;
    private boolean sixthWaveGroupAdded;


    public WaveController(ControllerRegister controllerRegister) {
        this.controllerRegister = controllerRegister;
        this.waves = new LinkedList<>();
        addFirstWaveGrop();
        shuffleWaves();
        this.gameLevelDisplay = 1;

        this.secondWaveGroupAdded = false;
        this.thirdWaveGroupAdded = false;
        this.fourthWaveGroupAdded = false;
        this.fifthWaveGroupAdded = false;
        this.sixthWaveGroupAdded = false;
    }

    private void addFirstWaveGrop() {
        waves.add(new SlugWave(controllerRegister, 1, 5));
        waves.add(new SlugWave(controllerRegister, 2, 7));
        waves.add(new SlugWave(controllerRegister, 1, 5));
        waves.add(new SlugWave(controllerRegister, 2, 7));
        waves.add(new SlugWave(controllerRegister, 2, 7));
        waves.add(new SlugWave(controllerRegister, 1, 5));
        waves.add(new SlugWave(controllerRegister, 2, 7));
        waves.add(new SlugWave(controllerRegister, 1, 5));
    }

    private void addSecondWaveGroup() {
        this.secondWaveGroupAdded = !secondWaveGroupAdded;
        waves.add(new SlugWave(controllerRegister, 2, 7));
        waves.add(new SlugWave(controllerRegister, 2, 7));
        waves.add(new SlugWave(controllerRegister, 2, 7));
        waves.add(new MageWave(controllerRegister,2,4));
        waves.add(new MageWave(controllerRegister,2,4));
        waves.add(new MageWave(controllerRegister,1,4));
        waves.add(new MageWave(controllerRegister,1,4));
        shuffleWaves();
        System.out.println("Level " + gameLevelDisplay + ". " + waves.size() + " wave objects in waves");

        System.out.println("second wave added");
    }

    private void addThirdWaveGroup() {
        this.thirdWaveGroupAdded = !thirdWaveGroupAdded;
        waves.add(new SkullWave(controllerRegister, 2, 10));
        waves.add(new SkullWave(controllerRegister, 2, 10));
        waves.add(new SkullWave(controllerRegister, 2, 10));
        waves.add(new SkullWave(controllerRegister, 2, 10));
        waves.add(new MageWave(controllerRegister,2,4));
        waves.add(new MageWave(controllerRegister,2,4));
        waves.add(new SlugWave(controllerRegister, 3, 10));
        waves.add(new SlugWave(controllerRegister, 3, 10));
        waves.add(new SlugWave(controllerRegister, 3, 10));
        System.out.println(waves.size());

        shuffleWaves();
        System.out.println("third wave added");
    }

    private void addForthWaveGroup() {
        this.fourthWaveGroupAdded = !fourthWaveGroupAdded;
        waves.add(new RedWave(controllerRegister, 2, 8));
        waves.add(new RedWave(controllerRegister, 2, 8));
        waves.add(new RedWave(controllerRegister, 1, 3));
        waves.add(new RedWave(controllerRegister, 1, 3));
        waves.add(new SlugWave(controllerRegister, 3, 10));
        waves.add(new SlugWave(controllerRegister, 3, 10));
        waves.add(new SlugWave(controllerRegister, 3, 10));
        waves.add(new MageWave(controllerRegister,1,4));
        waves.add(new MageWave(controllerRegister,1,4));
        shuffleWaves();
        System.out.println(waves.size());

        System.out.println("fourth wave added");
    }

    private void addFifthWaveGroup() {
        this.fifthWaveGroupAdded = !fifthWaveGroupAdded;
        waves.add(new BearWave(controllerRegister, 1, 6));
        waves.add(new BearWave(controllerRegister, 1, 6));
        waves.add(new BearWave(controllerRegister, 1, 6));
        waves.add(new BearWave(controllerRegister, 2, 12));
        waves.add(new SlugBossWave(controllerRegister, 1, 8));
        waves.add(new SlugBossWave(controllerRegister, 1, 8));
        waves.add(new SlugBossWave(controllerRegister, 1, 8));
        waves.add(new SlugBossWave(controllerRegister,2,14));
        shuffleWaves();
        System.out.println(waves.size());
        System.out.println("fifth wave added");
    }


    private void shuffleWaves() {
        Collections.shuffle(waves);
    }

    public void update(float delta) {
        Wave currentWave = waves.get(0);
        currentWave.spawnEnemies();
        currentWave.reduceTimer(delta);
        if (currentWave.getTimer() <= 0) {
            waves.removeFirst();
            gameLevelDisplay += 1;

        }
        if(gameLevelDisplay >= 5 && secondWaveGroupAdded == false) {
            addSecondWaveGroup();
        }
        if(gameLevelDisplay >= 10 && thirdWaveGroupAdded == false) {
            addThirdWaveGroup();
        }
        if(gameLevelDisplay >= 15 && fourthWaveGroupAdded == false) {
            addForthWaveGroup();
        }
        if(gameLevelDisplay >= 20 && fifthWaveGroupAdded == false) {
            addFifthWaveGroup();
        }

    }

    public void restart() {
        this.waves.clear();
        addFirstWaveGrop();
        shuffleWaves();
        this.gameLevelDisplay = 1;
        this.secondWaveGroupAdded = false;
        this.thirdWaveGroupAdded = false;
        this.fourthWaveGroupAdded = false;
        this.fifthWaveGroupAdded = false;
    }

    public int getGameLevelDisplay() {
        return gameLevelDisplay;
    }
}
