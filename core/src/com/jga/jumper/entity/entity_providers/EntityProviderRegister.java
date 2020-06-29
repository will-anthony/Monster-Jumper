package com.jga.jumper.entity.entity_providers;

import com.jga.jumper.controllers.ControllerRegister;
import com.jga.jumper.screen.game.MasterController;

public class EntityProviderRegister {

    private ControllerRegister controllerRegister;

    private MonsterEntityProvider monsterEntityProvider;
    private BackgroundEntityProvider backgroundEntityProvider;
    private PlanetEntityProvider planetEntityProvider;
    private SlugEntityProvider slugEntityProvider;
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
}
