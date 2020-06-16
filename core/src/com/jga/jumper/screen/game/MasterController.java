package com.jga.jumper.screen.game;

import com.jga.jumper.common.GameManager;
import com.jga.jumper.controllers.SlugController;
import com.jga.jumper.state_machines.GameState;
import com.jga.jumper.config.GameConfig;
import com.jga.jumper.controllers.CoinController;
import com.jga.jumper.controllers.ControllerRegister;
import com.jga.jumper.controllers.FloatingScoreController;
import com.jga.jumper.controllers.MonsterController;
import com.jga.jumper.controllers.ObstacleController;
import com.jga.jumper.entity.Monster;

public class MasterController {

    // == attributes ==
    private float startWaitTimer = GameConfig.START_WAIT_TIME;
    private float animationTime;
    private boolean gameStarted;

    private GameState gameState = GameState.MENU;

    private ControllerRegister controllerRegister;
    private CoinController coinController;
    private ObstacleController obstacleController;
    private FloatingScoreController floatingScoreController;
    private MonsterController monsterController;
    private SlugController slugController;

    // == constructors ==
    public MasterController(ControllerRegister controllerRegister) {
        this.controllerRegister = controllerRegister;
        init();
    }

    private void init() {
        this.coinController = controllerRegister.getCoinController();
        this.obstacleController = controllerRegister.getObstacleController();
        this.monsterController = controllerRegister.getMonsterController();
        this.floatingScoreController = controllerRegister.getFloatingScoreController();
        this.slugController = controllerRegister.getSlugController();
    }

    // == public methods ==
    public void update(float delta) {
        animationTime += delta;
        controllerRegister.getBackgroundController().update(delta);

        if (gameState.isReady() && startWaitTimer > 0) {
            startWaitTimer -= delta;

            if (startWaitTimer <= 0) {
                gameState = GameState.PLAYING;
                gameStarted = true;
                monsterController.getMonsters().get(0).walk();
            }
        }

        if (!gameState.isPlaying()) {
            return;
        }

        GameManager.INSTANCE.updateDisplayScore(delta);
        monsterController.update(delta);
        obstacleController.update(delta);
        coinController.update(delta);
        floatingScoreController.update(delta);
        slugController.update(delta);

        checkCollision();
    }

    public void restart() {
        // clear all objects from screen
        coinController.restart();
        obstacleController.restart();
        monsterController.restart();
        floatingScoreController.restart();

        GameManager.INSTANCE.updateHighScore();
        GameManager.INSTANCE.reset();
        startWaitTimer = GameConfig.START_WAIT_TIME;
        animationTime = 0f;
        gameStarted = false;
        gameState = GameState.READY;
    }

    // == getters and setters ==
    public boolean isGameStarted() {
        return gameStarted;
    }

    public float getAnimationTime() {
        return animationTime;
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

    // == private methods ==
    private void checkCollision() {
        Monster monster = monsterController.getMonsters().get(0);

        // player <-> obstacle
        obstacleController.checkCollision(monster);

        // player <-> coins
        coinController.checkCollision(monster);


    }

}

