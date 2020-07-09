package com.jga.jumper.entity.entity_providers;

import com.jga.jumper.controllers.ControllerRegister;
import com.jga.jumper.entity.projectiles.FireBall;
import com.jga.jumper.screen.game.MasterController;

public class EntityProviderRegister {

    private ControllerRegister controllerRegister;

    private MonsterEntityProvider monsterEntityProvider;
    private BackgroundEntityProvider backgroundEntityProvider;
    private PlanetEntityProvider planetEntityProvider;
    private SlugEntityProvider slugEntityProvider;
    private BearEntityProvider bearEntityProvider;
    private MageEntityProvider mageEntityProvider;
    private FireBallEntityProvider fireBallEntityProvider;
    private MasterController masterController;

    public EntityProviderRegister(ControllerRegister controllerRegister) {
        this.controllerRegister = controllerRegister;
        this.masterController = controllerRegister.getMasterController();
        init();
    }

    private void init() {
        this.monsterEntityProvider = new MonsterEntityProvider(controllerRegister.getMonsterController());
        this.backgroundEntityProvider = new BackgroundEntityProvider(controllerRegister.getBackgroundController());
        this.planetEntityProvider = new PlanetEntityProvider(controllerRegister.getPlanetController());
        this.slugEntityProvider = new SlugEntityProvider(controllerRegister.getSlugController());
        this.bearEntityProvider = new BearEntityProvider(controllerRegister.getBearController());
        this.mageEntityProvider = new MageEntityProvider(controllerRegister.getMageController());
        this.fireBallEntityProvider = new FireBallEntityProvider(controllerRegister.getFireBallController());
    }

    public MonsterEntityProvider getMonsterEntityProvider() {
        return monsterEntityProvider;
    }

    public BackgroundEntityProvider getBackgroundEntityProvider() {
        return backgroundEntityProvider;
    }

    public PlanetEntityProvider getPlanetEntityProvider() {
        return planetEntityProvider;
    }

    public SlugEntityProvider getSlugEntityProvider() {
        return slugEntityProvider;
    }

    public BearEntityProvider getBearEntityProvider() {
        return bearEntityProvider;
    }

    public MageEntityProvider getMageEntityProvider() {
        return mageEntityProvider;
    }

    public FireBallEntityProvider getFireBallEntityProvider() {
        return fireBallEntityProvider;
    }
}
