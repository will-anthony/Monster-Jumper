package com.jga.jumper.levels;

import com.jga.jumper.controllers.ControllerRegister;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LevelController {

    private ControllerRegister controllerRegister;

    private int gameLevel;
    private int gameLevelDisplay;
    private List<Integer> firstLevelGroup;
    private List<Integer> secondLevelGroup;
    private List<Integer> thirdLevelGroup;

    private Level_1 level_1;
    private Level_2 level_2;
    private Level_3 level_3;
    private Level_4 level_4;
    private Level_5 level_5;

    private Level_6 level_6;
    private Level_7 level_7;
    private Level_8 level_8;
    private Level_9 level_9;
    private Level_10 level_10;

    private Level_11 level_11;
    private Level_12 level_12;
    private Level_13 level_13;
    private Level_14 level_14;
    private Level_15 level_15;

    public LevelController(ControllerRegister controllerRegister) {

        this.controllerRegister = controllerRegister;
        this.level_1 = new Level_1(controllerRegister);
        this.level_2 = new Level_2(controllerRegister);
        this.level_3 = new Level_3(controllerRegister);
        this.level_4 = new Level_4(controllerRegister);
        this.level_5 = new Level_5(controllerRegister);

        this.level_6 = new Level_6(controllerRegister);
        this.level_7 = new Level_7(controllerRegister);
        this.level_8 = new Level_8(controllerRegister);
        this.level_9 = new Level_9(controllerRegister);
        this.level_10 = new Level_10(controllerRegister);

        this.level_11 = new Level_11(controllerRegister);
        this.level_12 = new Level_12(controllerRegister);
        this.level_13 = new Level_13(controllerRegister);
        this.level_14 = new Level_14(controllerRegister);
        this.level_15 = new Level_15(controllerRegister);

        this.gameLevel = 1;
        this.gameLevelDisplay = 1;

        this.firstLevelGroup = new ArrayList<>();
        this.secondLevelGroup = new ArrayList<>();
        this.thirdLevelGroup = new ArrayList<>();
    }

    public void update(float delta) {

        switch (gameLevel) {
            case 1:
                level_1.update(delta);
                if (level_1.hasLevelFinished()) {
                    gameLevel = setGameLevel(firstLevelGroup);
                }
                break;
            case 2:
                level_2.update(delta);
                if (level_2.hasLevelFinished()) {
                    gameLevel = setGameLevel(chooseLevelGroup(firstLevelGroup, secondLevelGroup));
                }
                break;
            case 3:
                level_3.update(delta);
                if (level_3.hasLevelFinished()) {
                    gameLevel = setGameLevel(chooseLevelGroup(firstLevelGroup, secondLevelGroup));
                }
                break;
            case 4:
                level_4.update(delta);
                if (level_4.hasLevelFinished()) {
                    gameLevel = setGameLevel(chooseLevelGroup(firstLevelGroup, secondLevelGroup));
                }
                break;
            case 5:
                level_5.update(delta);
                if (level_5.hasLevelFinished()) {
                    gameLevel = setGameLevel(chooseLevelGroup(firstLevelGroup, secondLevelGroup));
                }
                break;
            case 6:
                level_6.update(delta);
                if (level_6.hasLevelFinished()) {
                    gameLevel = setGameLevel(chooseLevelGroup(secondLevelGroup, thirdLevelGroup));
                }
                break;
            case 7:
                level_7.update(delta);
                if (level_7.hasLevelFinished()) {
                    gameLevel = setGameLevel(chooseLevelGroup(secondLevelGroup, thirdLevelGroup));
                }
                break;
            case 8:
                level_8.update(delta);
                if (level_8.hasLevelFinished()) {
                    gameLevel = setGameLevel(chooseLevelGroup(secondLevelGroup, thirdLevelGroup));
                }
                break;
            case 9:
                level_9.update(delta);
                if (level_9.hasLevelFinished()) {
                    gameLevel = setGameLevel(chooseLevelGroup(secondLevelGroup, thirdLevelGroup));
                }
                break;
            case 10:
                level_10.update(delta);
                if (level_10.hasLevelFinished()) {
                    gameLevel = setGameLevel(chooseLevelGroup(secondLevelGroup, thirdLevelGroup));
                }
                break;
            case 11:
                level_11.update(delta);
                if (level_11.hasLevelFinished()) {
                    gameLevel = setGameLevel(chooseLevelGroup(firstLevelGroup, secondLevelGroup));
                }
                break;
            case 12:
                level_12.update(delta);
                if (level_12.hasLevelFinished()) {
                    gameLevel = setGameLevel(chooseLevelGroup(firstLevelGroup, secondLevelGroup));
                }
                break;
            case 13:
                level_13.update(delta);
                if (level_13.hasLevelFinished()) {
                    gameLevel = setGameLevel(chooseLevelGroup(firstLevelGroup, secondLevelGroup));
                }
                break;
            case 14:
                level_14.update(delta);
                if (level_14.hasLevelFinished()) {
                    gameLevel = setGameLevel(chooseLevelGroup(firstLevelGroup, secondLevelGroup));
                }
                break;
            case 15:
                level_15.update(delta);
                if (level_15.hasLevelFinished()) {
                    gameLevel = setGameLevel(chooseLevelGroup(firstLevelGroup, secondLevelGroup));
                }
                break;
        }
    }

    private int setGameLevel(List<Integer> levelGroup) {

        Integer nextLevel = 1;
        if (!levelGroup.isEmpty()) {
            Collections.shuffle(levelGroup);
            nextLevel = levelGroup.get(0);
            levelGroup.remove(nextLevel);
        }
        this.gameLevelDisplay ++;
        return nextLevel;
    }

    private List<Integer> chooseLevelGroup(List<Integer> levelGroupA, List<Integer> levelGroupB) {
        if (levelGroupA.isEmpty()) {
            return levelGroupB;
        } else {
            return levelGroupA;
        }
    }

    public void restart() {
        resetLevels();
        addLevelsToGroups();
        this.gameLevel = 1;
        this.gameLevelDisplay = 1;

    }

        private void resetLevels() {

        this.level_1.reset();
        this.level_2.reset();
        this.level_3.reset();
        this.level_4.reset();
        this.level_5.reset();

        this.level_6.reset();
        this.level_7.reset();
        this.level_8.reset();
        this.level_9.reset();
        this.level_10.reset();

        this.level_11.reset();
        this.level_12.reset();
        this.level_13.reset();
        this.level_14.reset();
        this.level_15.reset();
    }

    private void addLevelsToGroups() {

        this.firstLevelGroup.add(2);
        this.firstLevelGroup.add(3);
        this.firstLevelGroup.add(4);
        this.firstLevelGroup.add(5);

        this.secondLevelGroup.add(6);
        this.secondLevelGroup.add(7);
        this.secondLevelGroup.add(8);
        this.secondLevelGroup.add(9);
        this.secondLevelGroup.add(10);

        this.thirdLevelGroup.add(11);
        this.thirdLevelGroup.add(12);
        this.thirdLevelGroup.add(13);
        this.thirdLevelGroup.add(14);
        this.thirdLevelGroup.add(15);
    }

    public int getGameLevel() {
        return gameLevel;
    }

    public void setGameLevel(int gameLevel) {
        this.gameLevel = gameLevel;
    }

    public int getGameLevelDisplay() {
        return gameLevelDisplay;
    }

    public void setGameLevelDisplay(int gameLevelDisplay) {
        this.gameLevelDisplay = gameLevelDisplay;
    }
}
