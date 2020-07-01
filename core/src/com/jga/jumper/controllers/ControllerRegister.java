package com.jga.jumper.controllers;

import com.jga.jumper.common.SoundListener;
import com.jga.jumper.screen.game.MasterController;
import com.jga.jumper.utils.BackgroundController;

public class ControllerRegister {

    private MonsterController monsterController;
    private CoinController coinController;
    private ObstacleController obstacleController;
    private FloatingScoreController floatingScoreController;
    private BackgroundController backgroundController;
    private PlanetController planetController;
    private SlugController slugController;
    private BearController bearController;
    private OverlayCallbackController overlayCallbackController;

    private MasterController masterController;
// make this class instantiate all controllers. Instantiate this in screen. Pass all info to master

    public ControllerRegister(SoundListener soundListener) {
        this.monsterController = new MonsterController(soundListener);
        this.coinController = new CoinController(this, soundListener);
        this.obstacleController = new ObstacleController(this, soundListener);
        this.floatingScoreController = new FloatingScoreController();
        this.backgroundController =  new BackgroundController();
        this.planetController = new PlanetController();
        this.slugController = new SlugController(this, soundListener);
        this.bearController = new BearController(this, soundListener);
        this.overlayCallbackController = new OverlayCallbackController(this);
        this.masterController = new MasterController(this);
    }

    public MonsterController getMonsterController() {
        return monsterController;
    }

    public CoinController getCoinController() {
        return coinController;
    }

    public ObstacleController getObstacleController() {
        return obstacleController;
    }

    public FloatingScoreController getFloatingScoreController() {
        return floatingScoreController;
    }

    public BackgroundController getBackgroundController() {
        return backgroundController;
    }

    public MasterController getMasterController() {
        return masterController;
    }

    public PlanetController getPlanetController() {
        return planetController;
    }

    public OverlayCallbackController getOverlayCallbackController() {
        return overlayCallbackController;
    }

    public SlugController getSlugController() {
        return slugController;
    }

    public BearController getBearController() {
        return bearController;
    }
}
