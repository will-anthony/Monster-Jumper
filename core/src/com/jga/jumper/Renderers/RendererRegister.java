package com.jga.jumper.Renderers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.jga.jumper.Renderers.Background.BackgroundGamePlayRenderer;
import com.jga.jumper.Renderers.Coin.CoinGamePlayRenderer;
import com.jga.jumper.Renderers.Coin.CoinDebugRenderer;
import com.jga.jumper.Renderers.Monster.MonsterDebugRenderer;
import com.jga.jumper.Renderers.Monster.MonsterGamePlayRenderer;
import com.jga.jumper.Renderers.Obstacle.ObstacleDebugRenderer;
import com.jga.jumper.Renderers.Obstacle.ObstacleGamePlayRenderer;
import com.jga.jumper.Renderers.Planet.PlanetDebugRenderer;
import com.jga.jumper.Renderers.Planet.PlanetGamePlayRenderer;
import com.jga.jumper.Renderers.Slug.SlugDebugRenderer;
import com.jga.jumper.Renderers.Slug.SlugGamePlayRenderer;
import com.jga.jumper.Renderers.bear.BearDebugRenderer;
import com.jga.jumper.Renderers.bear.BearGamePlayRenderer;
import com.jga.jumper.Renderers.fireball.FireBallDebugRenderer;
import com.jga.jumper.Renderers.fireball.FireBallGamePlayRenderer;
import com.jga.jumper.Renderers.mage.MageDebugRenderer;
import com.jga.jumper.Renderers.mage.MageGamePlayRenderer;
import com.jga.jumper.Renderers.red.RedGamePlayRenderer;
import com.jga.jumper.Renderers.shield.ShieldGamePlayRenderer;
import com.jga.jumper.Renderers.skull.SkullGamePlayRenderer;
import com.jga.jumper.Renderers.skull_spike_trap.SkullSpikeTrapGamePlayRenderer;
import com.jga.jumper.Renderers.slugBoss.SlugBossGamePlayRenderer;
import com.jga.jumper.Renderers.spike_trap.SpikeTrapGamePlayRenderer;
import com.jga.jumper.Renderers.trap_warning_smoke.TrapWarningSmokeGamePlayRenderer;
import com.jga.jumper.entity.Shield;

public class RendererRegister {

    // == attributes ==
    private BackgroundGamePlayRenderer backgroundGamePlayRenderer;
    private MonsterGamePlayRenderer monsterGamePlayRenderer;
    private PlanetGamePlayRenderer planetGamePlayRenderer;
    private SpikeTrapGamePlayRenderer spikeTrapGamePlayRenderer;

    private SlugGamePlayRenderer slugGamePlayRenderer;
    private SlugBossGamePlayRenderer slugBossGamePlayRenderer;
    private SkullGamePlayRenderer skullGamePlayRenderer;
    private SkullSpikeTrapGamePlayRenderer skullSpikeTrapGamePlayRenderer;
    private TrapWarningSmokeGamePlayRenderer trapWarningSmokeGamePlayRenderer;
    private RedGamePlayRenderer redGamePlayRenderer;
    private BearGamePlayRenderer bearGamePlayRenderer;
    private MageGamePlayRenderer mageGamePlayRenderer;
    private FireBallGamePlayRenderer fireBallGamePlayRenderer;
    private CoinGamePlayRenderer coinGamePlayRenderer;

    private MonsterDebugRenderer monsterDebugRenderer;
    private PlanetDebugRenderer planetDebugRenderer;

    private SlugDebugRenderer slugDebugRenderer;
    private BearDebugRenderer bearDebugRenderer;
    private MageDebugRenderer mageDebugRenderer;
    private FireBallDebugRenderer fireBallDebugRenderer;
    private ShieldGamePlayRenderer shieldGamePlayRenderer;

    private TextureAtlas gamePlayAtlas;

    public RendererRegister(TextureAtlas gamePlayAtlas) {
        this.gamePlayAtlas = gamePlayAtlas;
        init();
    }

    private void init() {
        backgroundGamePlayRenderer = new BackgroundGamePlayRenderer(gamePlayAtlas);
        monsterGamePlayRenderer = new MonsterGamePlayRenderer(gamePlayAtlas);
        planetGamePlayRenderer = new PlanetGamePlayRenderer(gamePlayAtlas);
        spikeTrapGamePlayRenderer = new SpikeTrapGamePlayRenderer(gamePlayAtlas);
        slugGamePlayRenderer = new SlugGamePlayRenderer(gamePlayAtlas);
        slugBossGamePlayRenderer = new SlugBossGamePlayRenderer(gamePlayAtlas);
        skullGamePlayRenderer = new SkullGamePlayRenderer(gamePlayAtlas);
        skullSpikeTrapGamePlayRenderer = new SkullSpikeTrapGamePlayRenderer(gamePlayAtlas);
        trapWarningSmokeGamePlayRenderer = new TrapWarningSmokeGamePlayRenderer(gamePlayAtlas);
        redGamePlayRenderer = new RedGamePlayRenderer(gamePlayAtlas);
        bearGamePlayRenderer = new BearGamePlayRenderer(gamePlayAtlas);
        mageGamePlayRenderer = new MageGamePlayRenderer(gamePlayAtlas);
        fireBallGamePlayRenderer = new FireBallGamePlayRenderer(gamePlayAtlas);
        coinGamePlayRenderer = new CoinGamePlayRenderer(gamePlayAtlas);
        shieldGamePlayRenderer = new ShieldGamePlayRenderer(gamePlayAtlas);


        monsterDebugRenderer = new MonsterDebugRenderer();
        planetDebugRenderer = new PlanetDebugRenderer();
        slugDebugRenderer = new SlugDebugRenderer();
        bearDebugRenderer = new BearDebugRenderer();
        mageDebugRenderer = new MageDebugRenderer();
        fireBallDebugRenderer = new FireBallDebugRenderer();
    }

    public BackgroundGamePlayRenderer getBackgroundGamePlayRenderer() {
        return backgroundGamePlayRenderer;
    }

    public MonsterGamePlayRenderer getMonsterGamePlayRenderer() {
        return monsterGamePlayRenderer;
    }

    public MonsterDebugRenderer getMonsterDebugRenderer() {
        return monsterDebugRenderer;
    }

    public PlanetDebugRenderer getPlanetDebugRenderer() {
        return planetDebugRenderer;
    }

    public PlanetGamePlayRenderer getPlanetGamePlayRenderer() {
        return planetGamePlayRenderer;
    }

    public SlugGamePlayRenderer getSlugGamePlayRenderer() {
        return  slugGamePlayRenderer;
    }

    public SlugDebugRenderer getSlugDebugRenderer() {
        return slugDebugRenderer;
    }

    public SlugBossGamePlayRenderer getSlugBossGamePlayRenderer() {
        return slugBossGamePlayRenderer;
    }

    public BearGamePlayRenderer getBearGamePlayRenderer() {
        return bearGamePlayRenderer;
    }

    public BearDebugRenderer getBearDebugRenderer() {
        return bearDebugRenderer;
    }

    public MageGamePlayRenderer getMageGamePlayRenderer() {
        return mageGamePlayRenderer;
    }

    public MageDebugRenderer getMageDebugRenderer() {
        return mageDebugRenderer;
    }

    public FireBallGamePlayRenderer getFireBallGamePlayRenderer() {
        return fireBallGamePlayRenderer;
    }

    public FireBallDebugRenderer getFireBallDebugRenderer() {
        return fireBallDebugRenderer;
    }

    public CoinGamePlayRenderer getCoinGamePlayRenderer() {
        return coinGamePlayRenderer;
    }

    public SpikeTrapGamePlayRenderer getSpikeTrapGamePlayRenderer() {
        return spikeTrapGamePlayRenderer;
    }

    public SkullGamePlayRenderer getSkullGamePlayRenderer() {
        return skullGamePlayRenderer;
    }

    public SkullSpikeTrapGamePlayRenderer getSkullSpikeTrapGamePlayRenderer() {
        return skullSpikeTrapGamePlayRenderer;
    }

    public RedGamePlayRenderer getRedGamePlayRenderer() {
        return redGamePlayRenderer;
    }

    public TrapWarningSmokeGamePlayRenderer getTrapWarningSmokeGamePlayRenderer() {
        return trapWarningSmokeGamePlayRenderer;
    }

    public ShieldGamePlayRenderer getShieldGamePlayRenderer() {
        return shieldGamePlayRenderer;
    }
}
