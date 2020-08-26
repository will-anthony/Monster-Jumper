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
import com.jga.jumper.levels.Level;
import com.jga.jumper.levels.Level_1;
import com.jga.jumper.levels.Level_2;
import com.jga.jumper.levels.Level_3;
import com.jga.jumper.levels.Level_4;
import com.jga.jumper.levels.Level_5;
import com.jga.jumper.state_machines.GameState;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MasterController {

    // == attributes ==
    private float startWaitTimer = GameConfig.START_WAIT_TIME;
    private int gameLevel;
    private List<Integer> levels;

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

    private Level_1 level_1;
    private Level_2 level_2;
    private Level_3 level_3;
    private Level_4 level_4;
    private Level_5 level_5;

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

        this.level_1 = new Level_1(controllerRegister);
        this.level_2 = new Level_2(controllerRegister);
        this.level_3 = new Level_3(controllerRegister);
        this.level_4 = new Level_4(controllerRegister);
        this.level_5 = new Level_5(controllerRegister);

        this.gameLevel = 1;
        this.levels = new ArrayList<>();
        levels.add(2);
        levels.add(3);
        levels.add(4);
        levels.add(5);
    }

    // == public methods ==
    public void update(float delta) {

        startGame(delta);

        if (gameState.isPlaying()) {

            GameManager.INSTANCE.updateDisplayScore(delta);
            monsterController.update(delta);
            floatingScoreController.update(delta);
            slugController.update(delta);
            slugBossController.update(delta);
            skullController.update(delta);
            skullSpikeTrapController.update(delta);
            redController.update(delta);
            bearController.update(delta);
            mageController.update(delta);
            spikeTrapController.update(delta);
            trapWarningSmokeController.update(delta);
            fireBallController.update(delta);
            coinController.update(delta);
            shieldController.update(delta);

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
                monsterController.getMonsters().get(0).walk();
            }
        }
    }

    private int setGameLevel() {

        Integer nextLevel = 1;
        if (!levels.isEmpty()) {
            Collections.shuffle(levels);
            nextLevel = levels.get(0);
            levels.remove(nextLevel);
        }
        return nextLevel;
    }

    private void gameLevelLogic(float delta) {
        switch (gameLevel) {
            case 1:
                level_1.update(delta);
                if (level_1.hasLevelFinished()) {
                    gameLevel = setGameLevel();
                }
                break;
            case 2:
                level_2.update(delta);
                if (level_2.hasLevelFinished()) {
                    gameLevel = setGameLevel();
                }
                break;
            case 3:
                level_3.update(delta);
                if (level_3.hasLevelFinished()) {
                    gameLevel = setGameLevel();
                }
                break;
            case 4:
                level_4.update(delta);
                if (level_4.hasLevelFinished()) {
                    gameLevel = setGameLevel();
                }
                break;
            case 5:
                level_5.update(delta);
                if (level_5.hasLevelFinished()) {
                    gameLevel = setGameLevel();
                }
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
        gameState = GameState.READY;

        level_1.reset();
        level_2.reset();
        level_3.reset();
        level_4.reset();
        level_5.reset();

        levels.add(2);
        levels.add(3);
        levels.add(4);
        levels.add(5);



        gameLevel = 1;
    }

    private void restartControllers() {
        monsterController.restart();
        floatingScoreController.restart();
        slugController.restart();
        slugBossController.restart();
        skullController.restart();
        spikeTrapController.restart();
        skullSpikeTrapController.restart();
        trapWarningSmokeController.restart();
        redController.restart();
        shieldController.restart();

        bearController.restart();
        mageController.restart();
        spikeTrapController.restart();
        fireBallController.restart();
        coinController.restart();
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

    public int getGameLevel() {
        return gameLevel;
    }
}
