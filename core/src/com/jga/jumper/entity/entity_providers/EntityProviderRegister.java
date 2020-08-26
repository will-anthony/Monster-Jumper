package com.jga.jumper.entity.entity_providers;

import com.jga.jumper.controllers.ControllerRegister;
import com.jga.jumper.screen.game.MasterController;

public class EntityProviderRegister {

    private ControllerRegister controllerRegister;

    private MonsterEntityProvider monsterEntityProvider;
    private BackgroundEntityProvider backgroundEntityProvider;
    private PlanetEntityProvider planetEntityProvider;
    private SlugEntityProvider slugEntityProvider;
    private SlugBossEntityProvider slugBossEntityProvider;
    private SkullEntityProvider skullEntityProvider;
    private TrapWarningSmokeEntityProvider smokeEntityProvider;
    private RedEntityProvider redEntityProvider;
    private BearEntityProvider bearEntityProvider;
    private MageEntityProvider mageEntityProvider;
    private FireBallEntityProvider fireBallEntityProvider;
    private SpikeTrapEntityProvider spikeTrapEntityProvider;
    private SparkEffectEntityProvider sparkEffectEntityProvider;
    private SkullSpikeTrapEntityProvider skullSpikeTrapEntityProvider;
    private ShieldEntityProvider shieldEntityProvider;
    private CoinEntityProvider coinEntityProvider;
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
        this.slugBossEntityProvider = new SlugBossEntityProvider(controllerRegister.getSlugBossController());
        this.skullEntityProvider = new SkullEntityProvider(controllerRegister.getSkullController());
        this.smokeEntityProvider = new TrapWarningSmokeEntityProvider(controllerRegister.getTrapWarningSmokeController());
        this.bearEntityProvider = new BearEntityProvider(controllerRegister.getBearController());
        this.coinEntityProvider = new CoinEntityProvider(controllerRegister.getCoinController());
        this.mageEntityProvider = new MageEntityProvider(controllerRegister.getMageController());
        this.fireBallEntityProvider = new FireBallEntityProvider(controllerRegister.getFireBallController());
        this.spikeTrapEntityProvider = new SpikeTrapEntityProvider(controllerRegister.getSpikeTrapController());
        this.skullSpikeTrapEntityProvider = new SkullSpikeTrapEntityProvider(controllerRegister.getSkullSpikeTrapController());
        this.sparkEffectEntityProvider = new SparkEffectEntityProvider(controllerRegister.getSparkEffectController());
        this.redEntityProvider = new RedEntityProvider(controllerRegister.getRedController());
        this.shieldEntityProvider = new ShieldEntityProvider(controllerRegister.getShieldController());
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

    public SlugBossEntityProvider getSlugBossEntityProvider() {
        return slugBossEntityProvider;
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

    public SpikeTrapEntityProvider getSpikeTrapEntityProvider() {
        return spikeTrapEntityProvider;
    }

    public CoinEntityProvider getCoinEntityProvider() {
        return coinEntityProvider;
    }

    public SkullEntityProvider getSkullEntityProvider() {
        return skullEntityProvider;
    }

    public SkullSpikeTrapEntityProvider getSkullSpikeTrapEntityProvider() {
        return skullSpikeTrapEntityProvider;
    }

    public RedEntityProvider getRedEntityProvider() {
        return redEntityProvider;
    }

    public TrapWarningSmokeEntityProvider getSmokeEntityProvider() {
        return smokeEntityProvider;
    }

    public ShieldEntityProvider getShieldEntityProvider() {
        return shieldEntityProvider;
    }

    public SparkEffectEntityProvider getSparkEffectEntityProvider() {
        return sparkEffectEntityProvider;
    }
}
