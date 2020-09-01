package com.jga.jumper.controllers;

import com.badlogic.gdx.physics.box2d.World;
import com.jga.jumper.common.SoundListener;
import com.jga.jumper.controllers.projectiles.FireBallController;
import com.jga.jumper.controllers.projectiles.SparkEffectController;
import com.jga.jumper.controllers.projectiles.SpikeTrapController;
import com.jga.jumper.controllers.projectiles.TrapWarningSmokeController;
import com.jga.jumper.levels.LevelController;
import com.jga.jumper.screen.game.MasterController;
import com.jga.jumper.utils.BackgroundController;
import com.jga.jumper.waves.WaveController;

public class ControllerRegister {

    private MonsterController monsterController;
    private CoinController coinController;
    private FloatingScoreController floatingScoreController;
    private BackgroundController backgroundController;
    private PlanetController planetController;
    private SlugController slugController;
    private SlugBossController slugBossController;
    private SkullController skullController;
    private SkullSpikeTrapController skullSpikeTrapController;
    private TrapWarningSmokeController trapWarningSmokeController;
    private SparkEffectController sparkEffectController;
    private ShieldController shieldController;
    private RedController redController;
    private BearController bearController;
    private MageController mageController;
    private com.jga.jumper.controllers.projectiles.SpikeTrapController spikeTrapController;
    private FireBallController fireBallController;
    private OverlayCallbackController overlayCallbackController;
    private LevelController levelController;
    private WaveController waveController;

    private MasterController masterController;

    // make this class instantiate all controllers. Instantiate this in screen. Pass all info to master
    public ControllerRegister(SoundListener soundListener) {
        this.monsterController = new MonsterController(soundListener, this);
        this.coinController = new CoinController(this, soundListener);
        this.floatingScoreController = new FloatingScoreController();
        this.backgroundController =  new BackgroundController();
        this.planetController = new PlanetController();
        this.slugController = new SlugController(this, soundListener);
        this.slugBossController = new SlugBossController(this, soundListener);
        this.skullController = new SkullController(this, soundListener);
        this.skullSpikeTrapController = new SkullSpikeTrapController(this, soundListener);
        this.trapWarningSmokeController = new TrapWarningSmokeController(this, soundListener);
        this.sparkEffectController = new SparkEffectController(this, soundListener);
        this.redController = new RedController(this, soundListener);
        this.bearController = new BearController(this, soundListener);
        this.fireBallController = new FireBallController(this, soundListener);
        this.spikeTrapController = new SpikeTrapController(this, soundListener);
        this.shieldController = new ShieldController(this, soundListener);
        this.overlayCallbackController = new OverlayCallbackController(this);
        this.mageController = new MageController(this, soundListener);
        this.levelController = new LevelController(this);
        this.waveController = new WaveController(this);
        this.masterController = new MasterController(this);

    }

    public MonsterController getMonsterController() {
        return monsterController;
    }

    public LevelController getLevelController() {
        return levelController;
    }

    public CoinController getCoinController() {
        return coinController;
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

    public SlugBossController getSlugBossController() {
        return slugBossController;
    }

    public BearController getBearController() {
        return bearController;
    }

    public MageController getMageController() {
        return mageController;
    }

    public FireBallController getFireBallController() {
        return fireBallController;
    }

    public SpikeTrapController getSpikeTrapController() {
        return spikeTrapController;
    }

    public SkullController getSkullController() {
        return skullController;
    }

    public SkullSpikeTrapController getSkullSpikeTrapController() {
        return skullSpikeTrapController;
    }

    public RedController getRedController() {
        return redController;
    }

    public TrapWarningSmokeController getTrapWarningSmokeController() {
        return trapWarningSmokeController;
    }

    public ShieldController getShieldController() {
        return shieldController;
    }

    public SparkEffectController getSparkEffectController() {
        return sparkEffectController;
    }

    public WaveController getWaveController() {
        return waveController;
    }
}
