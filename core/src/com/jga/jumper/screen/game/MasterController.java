package com.jga.jumper.screen.game;

import com.jga.jumper.common.GameManager;
import com.jga.jumper.config.GameConfig;
import com.jga.jumper.controllers.BearController;
import com.jga.jumper.controllers.CoinController;
import com.jga.jumper.controllers.ControllerRegister;
import com.jga.jumper.controllers.FloatingScoreController;
import com.jga.jumper.controllers.MageController;
import com.jga.jumper.controllers.MonsterController;
import com.jga.jumper.controllers.RedController;
import com.jga.jumper.controllers.ShieldController;
import com.jga.jumper.controllers.SkullController;
import com.jga.jumper.controllers.SkullSpikeTrapController;
import com.jga.jumper.controllers.SlugBossController;
import com.jga.jumper.controllers.SlugController;
import com.jga.jumper.controllers.projectiles.FireBallController;
import com.jga.jumper.controllers.projectiles.SpikeTrapController;
import com.jga.jumper.controllers.projectiles.TrapWarningSmokeController;
import com.jga.jumper.levels.LevelController;
import com.jga.jumper.levels.Level_1;
import com.jga.jumper.levels.Level_10;
import com.jga.jumper.levels.Level_11;
import com.jga.jumper.levels.Level_12;
import com.jga.jumper.levels.Level_13;
import com.jga.jumper.levels.Level_14;
import com.jga.jumper.levels.Level_15;
import com.jga.jumper.levels.Level_2;
import com.jga.jumper.levels.Level_3;
import com.jga.jumper.levels.Level_4;
import com.jga.jumper.levels.Level_5;
import com.jga.jumper.levels.Level_6;
import com.jga.jumper.levels.Level_7;
import com.jga.jumper.levels.Level_8;
import com.jga.jumper.levels.Level_9;
import com.jga.jumper.state_machines.GameState;
import com.jga.jumper.waves.WaveController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MasterController {

    // == attributes ==
    private float startWaitTimer = GameConfig.START_WAIT_TIME;
//    private int gameLevel;
//    private int gameLevelDisplay;
//    private List<Integer> firstLevelGroup;
//    private List<Integer> secondLevelGroup;
//    private List<Integer> thirdLevelGroup;

    private GameState gameState = GameState.MENU;

    private ControllerRegister controllerRegister;
    private FloatingScoreController floatingScoreController;
    private MonsterController monsterController;
    private SlugController slugController;
    private SlugBossController slugBossController;
    private SkullController skullController;
    private SkullSpikeTrapController skullSpikeTrapController;
    private RedController redController;
    private BearController bearController;
    private MageController mageController;
    private FireBallController fireBallController;
    private SpikeTrapController spikeTrapController;
    private ShieldController shieldController;
    private TrapWarningSmokeController trapWarningSmokeController;
    private CoinController coinController;
    private LevelController levelController;
    private WaveController waveController;

//    private Level_1 level_1;
//    private Level_2 level_2;
//    private Level_3 level_3;
//    private Level_4 level_4;
//    private Level_5 level_5;
//
//    private Level_6 level_6;
//    private Level_7 level_7;
//    private Level_8 level_8;
//    private Level_9 level_9;
//    private Level_10 level_10;
//
//    private Level_11 level_11;
//    private Level_12 level_12;
//    private Level_13 level_13;
//    private Level_14 level_14;
//    private Level_15 level_15;

    // == constructors ==
    public MasterController(ControllerRegister controllerRegister) {
        this.controllerRegister = controllerRegister;
        this.monsterController = controllerRegister.getMonsterController();
        this.floatingScoreController = controllerRegister.getFloatingScoreController();
        this.slugController = controllerRegister.getSlugController();
        this.slugBossController = controllerRegister.getSlugBossController();
        this.skullController = controllerRegister.getSkullController();
        this.redController = controllerRegister.getRedController();
        this.bearController = controllerRegister.getBearController();
        this.mageController = controllerRegister.getMageController();
        this.fireBallController = controllerRegister.getFireBallController();
        this.spikeTrapController = controllerRegister.getSpikeTrapController();
        this.skullSpikeTrapController = controllerRegister.getSkullSpikeTrapController();
        this.trapWarningSmokeController = controllerRegister.getTrapWarningSmokeController();
        this.shieldController = controllerRegister.getShieldController();
        this.coinController = controllerRegister.getCoinController();
        this.levelController = controllerRegister.getLevelController();
        this.waveController = controllerRegister.getWaveController();

//        this.level_1 = new Level_1(controllerRegister);
//        this.level_2 = new Level_2(controllerRegister);
//        this.level_3 = new Level_3(controllerRegister);
//        this.level_4 = new Level_4(controllerRegister);
//        this.level_5 = new Level_5(controllerRegister);
//
//        this.level_6 = new Level_6(controllerRegister);
//        this.level_7 = new Level_7(controllerRegister);
//        this.level_8 = new Level_8(controllerRegister);
//        this.level_9 = new Level_9(controllerRegister);
//        this.level_10 = new Level_10(controllerRegister);
//
//        this.level_11 = new Level_11(controllerRegister);
//        this.level_12 = new Level_12(controllerRegister);
//        this.level_13 = new Level_13(controllerRegister);
//        this.level_14 = new Level_14(controllerRegister);
//        this.level_15 = new Level_15(controllerRegister);
//
//        this.gameLevel = 1;
//        this.gameLevelDisplay = 1;
//
//        this.firstLevelGroup = new ArrayList<>();
//        this.secondLevelGroup = new ArrayList<>();
//        this.thirdLevelGroup = new ArrayList<>();
    }

    // == public methods ==
    public void update(float delta) {

        startGame(delta);

        if (gameState.isPlaying()) {

            GameManager.INSTANCE.updateDisplayScore(delta);
            this.monsterController.update(delta);
            this.floatingScoreController.update(delta);
            this.slugController.update(delta);
            this.slugBossController.update(delta);
            this.skullController.update(delta);
            this.skullSpikeTrapController.update(delta);
            this.redController.update(delta);
            this.bearController.update(delta);
            this.mageController.update(delta);
            this.spikeTrapController.update(delta);
            this.trapWarningSmokeController.update(delta);
            this.fireBallController.update(delta);
            this.coinController.update(delta);
            this.shieldController.update(delta);
//            this.levelController.update(delta);
            this.waveController.update(delta);
//            gameLevelLogic(delta);
        }
    }

    private void startGame(float delta) {
        updateScrollingBackground(delta);
        checkCountdownTimer(delta);
    }

    private void updateScrollingBackground(float delta) {
        controllerRegister.getBackgroundController().update(delta);
    }

    private void checkCountdownTimer(float delta) {
        if (gameState.isReady() && startWaitTimer > 0) {
            this.startWaitTimer -= delta;

            if (startWaitTimer <= 0) {
                gameState = GameState.PLAYING;
                monsterController.getMonsters().get(0).walk();
            }
        }
    }

//    private void gameLevelLogic(float delta) {
//        switch (gameLevel) {
//            case 1:
//                level_1.update(delta);
//                if (level_1.hasLevelFinished()) {
//                    gameLevel = setGameLevel(firstLevelGroup);
//                }
//                break;
//            case 2:
//                level_2.update(delta);
//                if (level_2.hasLevelFinished()) {
//                    gameLevel = setGameLevel(chooseLevelGroup(firstLevelGroup, secondLevelGroup));
//                }
//                break;
//            case 3:
//                level_3.update(delta);
//                if (level_3.hasLevelFinished()) {
//                    gameLevel = setGameLevel(chooseLevelGroup(firstLevelGroup, secondLevelGroup));
//                }
//                break;
//            case 4:
//                level_4.update(delta);
//                if (level_4.hasLevelFinished()) {
//                    gameLevel = setGameLevel(chooseLevelGroup(firstLevelGroup, secondLevelGroup));
//                }
//                break;
//            case 5:
//                level_5.update(delta);
//                if (level_5.hasLevelFinished()) {
//                    gameLevel = setGameLevel(chooseLevelGroup(firstLevelGroup, secondLevelGroup));
//                }
//                break;
//            case 6:
//                level_6.update(delta);
//                if (level_6.hasLevelFinished()) {
//                    gameLevel = setGameLevel(chooseLevelGroup(secondLevelGroup, thirdLevelGroup));
//                }
//                break;
//            case 7:
//                level_7.update(delta);
//                if (level_7.hasLevelFinished()) {
//                    gameLevel = setGameLevel(chooseLevelGroup(secondLevelGroup, thirdLevelGroup));
//                }
//                break;
//            case 8:
//                level_8.update(delta);
//                if (level_8.hasLevelFinished()) {
//                    gameLevel = setGameLevel(chooseLevelGroup(secondLevelGroup, thirdLevelGroup));
//                }
//                break;
//            case 9:
//                level_9.update(delta);
//                if (level_9.hasLevelFinished()) {
//                    gameLevel = setGameLevel(chooseLevelGroup(secondLevelGroup, thirdLevelGroup));
//                }
//                break;
//            case 10:
//                level_10.update(delta);
//                if (level_10.hasLevelFinished()) {
//                    gameLevel = setGameLevel(chooseLevelGroup(secondLevelGroup, thirdLevelGroup));
//                }
//                break;
//            case 11:
//                level_11.update(delta);
//                if (level_11.hasLevelFinished()) {
//                    gameLevel = setGameLevel(chooseLevelGroup(firstLevelGroup, secondLevelGroup));
//                }
//                break;
//            case 12:
//                level_12.update(delta);
//                if (level_12.hasLevelFinished()) {
//                    gameLevel = setGameLevel(chooseLevelGroup(firstLevelGroup, secondLevelGroup));
//                }
//                break;
//            case 13:
//                level_13.update(delta);
//                if (level_13.hasLevelFinished()) {
//                    gameLevel = setGameLevel(chooseLevelGroup(firstLevelGroup, secondLevelGroup));
//                }
//                break;
//            case 14:
//                level_14.update(delta);
//                if (level_14.hasLevelFinished()) {
//                    gameLevel = setGameLevel(chooseLevelGroup(firstLevelGroup, secondLevelGroup));
//                }
//                break;
//            case 15:
//                level_15.update(delta);
//                if (level_15.hasLevelFinished()) {
//                    gameLevel = setGameLevel(chooseLevelGroup(firstLevelGroup, secondLevelGroup));
//                }
//                break;
//        }
//    }
//
//    private int setGameLevel(List<Integer> levelGroup) {
//
//        Integer nextLevel = 1;
//        if (!levelGroup.isEmpty()) {
//            Collections.shuffle(levelGroup);
//            nextLevel = levelGroup.get(0);
//            levelGroup.remove(nextLevel);
//        }
//        this.gameLevelDisplay ++;
//        return nextLevel;
//    }
//
//    private List<Integer> chooseLevelGroup(List<Integer> levelGroupA, List<Integer> levelGroupB) {
//        if (levelGroupA.isEmpty()) {
//            return levelGroupB;
//        } else {
//            return levelGroupA;
//        }
//    }

    public void restart() {
        // clear all objects from screen
        restartControllers();

        GameManager.INSTANCE.updateHighScore();
        GameManager.INSTANCE.reset();
        startWaitTimer = GameConfig.START_WAIT_TIME;
        gameState = GameState.READY;

//        this.gameLevel = 1;
//        this.gameLevelDisplay = 1;

//        resetLevels();
//        addLevelsToGroups();
    }

    private void restartControllers() {
        this.monsterController.restart();
        this.floatingScoreController.restart();
        this.slugController.restart();
        this.slugBossController.restart();
        this.skullController.restart();
        this.spikeTrapController.restart();
        this.skullSpikeTrapController.restart();
        this.trapWarningSmokeController.restart();
        this.redController.restart();
        this.shieldController.restart();
        this.bearController.restart();
        this.mageController.restart();
        this.spikeTrapController.restart();
        this.fireBallController.restart();
        this.coinController.restart();
        this.waveController.restart();
//        this.levelController.restart();
    }
//
//    private void resetLevels() {
//        this.level_1.reset();
//        this.level_2.reset();
//        this.level_3.reset();
//        this.level_4.reset();
//        this.level_5.reset();
//
//        this.level_6.reset();
//        this.level_7.reset();
//        this.level_8.reset();
//        this.level_9.reset();
//        this.level_10.reset();
//
//        this.level_11.reset();
//        this.level_12.reset();
//        this.level_13.reset();
//        this.level_14.reset();
//        this.level_15.reset();
//    }
//
//    private void addLevelsToGroups() {
//
//        this.firstLevelGroup.add(2);
//        this.firstLevelGroup.add(3);
//        this.firstLevelGroup.add(4);
//        this.firstLevelGroup.add(5);
//
//        this.secondLevelGroup.add(6);
//        this.secondLevelGroup.add(7);
//        this.secondLevelGroup.add(8);
//        this.secondLevelGroup.add(9);
//        this.secondLevelGroup.add(10);
//
//        this.thirdLevelGroup.add(11);
//        this.thirdLevelGroup.add(12);
//        this.thirdLevelGroup.add(13);
//        this.thirdLevelGroup.add(14);
//        this.thirdLevelGroup.add(15);
//    }

    public float getStartWaitTimer() {
        return startWaitTimer;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

//    public int getGameLevel() {
//        return gameLevel;
//    }
//
//    public int getGameLevelDisplay() {
//        return gameLevelDisplay;
//    }
}
