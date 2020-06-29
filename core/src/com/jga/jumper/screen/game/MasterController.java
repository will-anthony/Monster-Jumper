package com.jga.jumper.screen.game;

import com.jga.jumper.common.GameManager;
import com.jga.jumper.config.GameConfig;
import com.jga.jumper.controllers.ControllerRegister;
import com.jga.jumper.controllers.FloatingScoreController;
import com.jga.jumper.controllers.MonsterController;
import com.jga.jumper.controllers.SlugController;
import com.jga.jumper.levels.Level1;
import com.jga.jumper.levels.Level2;
import com.jga.jumper.state_machines.GameState;

public class MasterController {

    // == attributes ==
    private float startWaitTimer = GameConfig.START_WAIT_TIME;
    //private boolean gameStarted;
    private int gameLevel;

    private GameState gameState = GameState.MENU;

    private ControllerRegister controllerRegister;
    private FloatingScoreController floatingScoreController;
    private MonsterController monsterController;
    private SlugController slugController;
    private Level1 level1;
    private Level2 level2;

    // == constructors ==
    public MasterController(ControllerRegister controllerRegister) {
        this.controllerRegister = controllerRegister;
        this.monsterController = controllerRegister.getMonsterController();
        this.floatingScoreController = controllerRegister.getFloatingScoreController();
        this.slugController = controllerRegister.getSlugController();
        this.level1 = new Level1(controllerRegister);
        this.level2 = new Level2(controllerRegister);
        this.gameLevel = 1;
    }

    // == public methods ==
    public void update(float delta) {

        startGame(delta);

        if (gameState.isPlaying()) {

            GameManager.INSTANCE.updateDisplayScore(delta);
            monsterController.update(delta);
            floatingScoreController.update(delta);
            slugController.update(delta);

            gameLevelLogic(delta);
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
            startWaitTimer -= delta;

            if (startWaitTimer <= 0) {
                gameState = GameState.PLAYING;
                //gameStarted = true;
                monsterController.getMonsters().get(0).walk();
            }
        }
    }

    private void gameLevelLogic(float delta) {
        switch (gameLevel) {
            case 1:
                level1.update(delta);
                if(level1.hasLevelFinished()) {
                    gameLevel ++;
                }
                break;
            case 2:
                level2.update(delta);
                if(level2.hasLevelFinished()) {
                    gameLevel ++;
                }
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
        }
    }

    public void restart() {
        // clear all objects from screen
        restartControllers();
        monsterController.restart();
        floatingScoreController.restart();

        GameManager.INSTANCE.updateHighScore();
        GameManager.INSTANCE.reset();
        startWaitTimer = GameConfig.START_WAIT_TIME;
        //gameStarted = false;
        gameState = GameState.READY;

        level1.reset();
        level2.reset();
    }

    private void restartControllers() {
        monsterController.restart();
        floatingScoreController.restart();
        slugController.restart();
    }

    public float getStartWaitTimer() {
        return startWaitTimer;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }
}
