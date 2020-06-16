package com.jga.jumper.entity.entity_providers;

import com.jga.jumper.controllers.ControllerRegister;
import com.jga.jumper.entity.Monster;

public class EntityProviderRegister {

    private ControllerRegister controllerRegister;

    private CoinEntityProvider coinEntityProvider;
    private ObstacleEntityProvider obstacleEntityProvider;
    private MonsterEntityProvider monsterEntityProvider;
    private BackgroundEntityProvider backgroundEntityProvider;
    private PlanetEntityProvider planetEntityProvider;
    private SlugEntityProvider slugEntityProvider;

    public EntityProviderRegister(ControllerRegister controllerRegister) {
        this.controllerRegister = controllerRegister;
        init();
    }

    private void init() {
        this.coinEntityProvider = new CoinEntityProvider(controllerRegister.getCoinController());
        this.obstacleEntityProvider = new ObstacleEntityProvider(controllerRegister.getObstacleController());
        this.monsterEntityProvider = new MonsterEntityProvider(controllerRegister.getMonsterController());
        this.backgroundEntityProvider = new BackgroundEntityProvider(controllerRegister.getBackgroundController());
        this.planetEntityProvider = new PlanetEntityProvider(controllerRegister.getPlanetController());
        this.slugEntityProvider = new SlugEntityProvider(controllerRegister.getSlugController());
    }

    public CoinEntityProvider getCoinEntityProvider() {
        return coinEntityProvider;
    }

    public ObstacleEntityProvider getObstacleEntityProvider() {
        return obstacleEntityProvider;
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
