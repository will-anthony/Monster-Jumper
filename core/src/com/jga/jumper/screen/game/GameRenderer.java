package com.jga.jumper.screen.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.jga.jumper.Renderers.Background.BackgroundGamePlayRenderer;
import com.jga.jumper.Renderers.Coin.CoinGamePlayRenderer;
import com.jga.jumper.Renderers.EntityDebugRenderer;
import com.jga.jumper.Renderers.Monster.MonsterGamePlayRenderer;
import com.jga.jumper.Renderers.Planet.PlanetGamePlayRenderer;
import com.jga.jumper.Renderers.RendererRegister;
import com.jga.jumper.Renderers.Slug.SlugGamePlayRenderer;
import com.jga.jumper.Renderers.bear.BearGamePlayRenderer;
import com.jga.jumper.Renderers.fireball.FireBallGamePlayRenderer;
import com.jga.jumper.Renderers.mage.MageGamePlayRenderer;
import com.jga.jumper.Renderers.red.RedGamePlayRenderer;
import com.jga.jumper.Renderers.shield.ShieldGamePlayRenderer;
import com.jga.jumper.Renderers.skull.SkullGamePlayRenderer;
import com.jga.jumper.Renderers.skull_spike_trap.SkullSpikeTrapGamePlayRenderer;
import com.jga.jumper.Renderers.slugBoss.SlugBossGamePlayRenderer;
import com.jga.jumper.Renderers.sparks.SparkGamePlayRenderer;
import com.jga.jumper.Renderers.spike_trap.SpikeTrapGamePlayRenderer;
import com.jga.jumper.Renderers.trap_warning_smoke.TrapWarningSmokeGamePlayRenderer;
import com.jga.jumper.assets.AssetDescriptors;
import com.jga.jumper.box2d.Box2DTest;
import com.jga.jumper.config.GameConfig;
import com.jga.jumper.entity.Background;
import com.jga.jumper.entity.Bear;
import com.jga.jumper.entity.Coin;
import com.jga.jumper.entity.Mage;
import com.jga.jumper.entity.Monster;
import com.jga.jumper.entity.Planet;
import com.jga.jumper.entity.Red;
import com.jga.jumper.entity.Shield;
import com.jga.jumper.entity.Skull;
import com.jga.jumper.entity.Slug;
import com.jga.jumper.entity.SlugBoss;
import com.jga.jumper.entity.entity_providers.EntityProviderRegister;
import com.jga.jumper.entity.projectiles.FireBall;
import com.jga.jumper.entity.projectiles.SkullSpikeTrap;
import com.jga.jumper.entity.projectiles.SpikeTrap;
import com.jga.jumper.entity.smoke_effects.SparkEffect;
import com.jga.jumper.entity.smoke_effects.TrapWarningSmoke;
import com.jga.util.ViewportUtils;
import com.jga.util.debug.DebugCameraController;

public class GameRenderer implements Disposable {

    // == attributes ==
    private RendererRegister rendererRegister;
    private EntityProviderRegister entityProviderRegister;
    private Array<Planet> planets;
    private Array<Background> backgrounds;
    private Array<Monster> monsters;
    private Array<Slug> slugs;
    private Array<SlugBoss> slugBosses;
    private Array<Skull> skulls;
    private Array<SkullSpikeTrap> skullSpikeTraps;
    private Array<TrapWarningSmoke> smokes;
    private Array<SparkEffect> sparks;
    private Array<Red> reds;
    private Array<Bear> bears;
    private Array<Mage> mages;
    private Array<FireBall> fireBalls;
    private Array<SpikeTrap> spikeTraps;
    private Array<Coin> coins;
    private Array<Shield> shields;

    private final SpriteBatch batch;
    private final AssetManager assetManager;

    private OrthographicCamera camera;
    private Viewport viewport;
    private ShapeRenderer renderer;

    private DebugCameraController debugCameraController;

    private ParticleEffect spaceDust;
    private TextureAtlas gamePlayAtlas;

    private boolean debugIsOn;

    // == constructors ==
    public GameRenderer(SpriteBatch batch, AssetManager assetManager, EntityProviderRegister entityProviderRegister) {
        this.batch = batch;
        this.assetManager = assetManager;
        this.entityProviderRegister = entityProviderRegister;
        init();
    }

    // == init ==
    private void init() {
        camera = new OrthographicCamera();
        viewport = new FillViewport(GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT, camera);
        renderer = new ShapeRenderer();

        debugCameraController = new DebugCameraController();
        debugCameraController.setStartPosition(GameConfig.WORLD_CENTER_X, GameConfig.WORLD_CENTER_Y);

        gamePlayAtlas = assetManager.get(AssetDescriptors.GAME_PLAY);
        rendererRegister = new RendererRegister(gamePlayAtlas);

        shields = entityProviderRegister.getShieldEntityProvider().getEntities();
        planets = entityProviderRegister.getPlanetEntityProvider().getEntities();
        monsters = entityProviderRegister.getMonsterEntityProvider().getEntities();
        slugs = entityProviderRegister.getSlugEntityProvider().getEntities();
        slugBosses = entityProviderRegister.getSlugBossEntityProvider().getEntities();
        skulls = entityProviderRegister.getSkullEntityProvider().getEntities();
        skullSpikeTraps = entityProviderRegister.getSkullSpikeTrapEntityProvider().getEntities();
        sparks = entityProviderRegister.getSparkEffectEntityProvider().getEntities();
        smokes = entityProviderRegister.getSmokeEntityProvider().getEntities();
        reds = entityProviderRegister.getRedEntityProvider().getEntities();
        bears = entityProviderRegister.getBearEntityProvider().getEntities();
        mages = entityProviderRegister.getMageEntityProvider().getEntities();
        fireBalls = entityProviderRegister.getFireBallEntityProvider().getEntities();
        spikeTraps = entityProviderRegister.getSpikeTrapEntityProvider().getEntities();
        backgrounds = entityProviderRegister.getBackgroundEntityProvider().getEntities();
        coins = entityProviderRegister.getCoinEntityProvider().getEntities();


        spaceDust = assetManager.get(AssetDescriptors.DUST);
        spaceDust.getEmitters().first().setPosition(GameConfig.WORLD_CENTER_X, GameConfig.WORLD_HEIGHT);
        spaceDust.start();
    }

    // == public methods ==
    void render(float delta) {

        debugCameraController.handleDebugInput(delta);
        debugCameraController.applyTo(camera);
        spaceDust.update(delta);

        renderGamePlay(delta);
        renderDebug();
    }

    void resize(int width, int height) {
        viewport.update(width, height, true);
        ViewportUtils.debugPixelsPerUnit(viewport);
    }

    @Override
    public void dispose() {
        renderer.dispose();
    }

    private void renderDebug() {
        if (debugIsOn) {
            ViewportUtils.drawGrid(viewport, renderer, GameConfig.CELL_SIZE);
            viewport.apply();
            renderer.setProjectionMatrix(camera.combined);

            renderer.begin(ShapeRenderer.ShapeType.Line);

            drawDebug();


            renderer.end();
        }
    }

    private void drawDebug() {

        EntityDebugRenderer entityDebugRenderer = new EntityDebugRenderer();

        // slug polygon debug
        for (Slug slug : slugs) {
            entityDebugRenderer.renderEntityDebugLines(renderer, slug.getPolygonCollider().getTransformedVertices());
            entityDebugRenderer.renderEntityDebugLines(renderer, slug.getKillCollider().getTransformedVertices());
        }

        // slug boss polygon debug
        for (SlugBoss slugBoss : slugBosses) {
            entityDebugRenderer.renderEntityDebugLines(renderer, slugBoss.getPolygonCollider().getTransformedVertices());
            entityDebugRenderer.renderEntityDebugLines(renderer, slugBoss.getKillCollider().getTransformedVertices());
            entityDebugRenderer.renderEntityDebugLines(renderer, slugBoss.getAwarenessCollider().getTransformedVertices());
        }

        // skull
        for (Skull skull : skulls) {
            entityDebugRenderer.renderEntityDebugLines(renderer, skull.getPolygonCollider().getTransformedVertices());
        }

        // skull trap
        for(SkullSpikeTrap skullSpikeTrap : skullSpikeTraps) {
            entityDebugRenderer.renderEntityDebugLines(renderer, skullSpikeTrap.getPolygonCollider().getTransformedVertices());
        }

        // mage polygon debug
        for (Mage mage : mages) {
            entityDebugRenderer.renderEntityDebugLines(renderer, mage.getPolygonCollider().getTransformedVertices());
            entityDebugRenderer.renderEntityDebugLines(renderer, mage.getKillCollider().getTransformedVertices());
            entityDebugRenderer.renderEntityDebugLines(renderer, mage.getAwarenessCollider().getTransformedVertices());
        }

        // red polygon debug
        for (Red red : reds) {
            entityDebugRenderer.renderEntityDebugLines(renderer, red.getPolygonCollider().getTransformedVertices());
        }

        // shield
        for(Shield shield: shields) {
            entityDebugRenderer.renderEntityDebugLines(renderer, shield.getPolygonCollider().getTransformedVertices());
            entityDebugRenderer.renderEntityDebugLines(renderer, shield.getKillCollider().getTransformedVertices());
        }

        // bear polygon debug
        for (Bear bear : bears) {
            entityDebugRenderer.renderEntityDebugLines(renderer, bear.getPolygonCollider().getTransformedVertices());
            entityDebugRenderer.renderEntityDebugLines(renderer, bear.getKillCollider().getTransformedVertices());
            entityDebugRenderer.renderEntityDebugLines(renderer, bear.getAwarenessCollider().getTransformedVertices());
        }

        // fireball polygon debug
        for (FireBall fireBall : fireBalls) {
            entityDebugRenderer.renderEntityDebugLines(renderer, fireBall.getPolygonCollider().getTransformedVertices());
        }

        // spike trap polygon debug
        for(SpikeTrap spikeTrap : spikeTraps) {
            entityDebugRenderer.renderEntityDebugLines(renderer, spikeTrap.getPolygonCollider().getTransformedVertices());
        }

        // coin polygon debug
        for(Coin coin : coins) {
            entityDebugRenderer.renderEntityDebugLines(renderer, coin.getPolygonCollider().getTransformedVertices());
        }

        for(SparkEffect sparkEffect : sparks) {
            entityDebugRenderer.renderEntityDebugLines(renderer, sparkEffect.getPolygonCollider().getTransformedVertices());
        }
    }

    private void renderGamePlay(float delta) {
        viewport.apply();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        drawGamePlay(delta);
        batch.end();
    }

    private void drawGamePlay(float delta) {

        // background
        BackgroundGamePlayRenderer backgroundGamePlayRenderer = rendererRegister.getBackgroundGamePlayRenderer();
        backgroundGamePlayRenderer.renderBackgroundGamePlay(batch, backgrounds);

        // dust
        spaceDust.draw(batch);

        // slug
        SlugGamePlayRenderer slugGamePlayRenderer = rendererRegister.getSlugGamePlayRenderer();
        slugGamePlayRenderer.renderGamePlay(batch, slugs, delta);

        // slug boss
        SlugBossGamePlayRenderer slugBossGamePlayRenderer = rendererRegister.getSlugBossGamePlayRenderer();
        slugBossGamePlayRenderer.renderGamePlay(batch, slugBosses, delta);

        // skull
        SkullGamePlayRenderer skullGamePlayRenderer = rendererRegister.getSkullGamePlayRenderer();
        skullGamePlayRenderer.renderGamePlay(batch, skulls, delta);

        // skull spike trap
        SkullSpikeTrapGamePlayRenderer skullSpikeTrapGamePlayRenderer = rendererRegister.getSkullSpikeTrapGamePlayRenderer();
        skullSpikeTrapGamePlayRenderer.renderSpikeTrapGamePlay(batch, skullSpikeTraps);

        // red
        RedGamePlayRenderer redGamePlayRenderer = rendererRegister.getRedGamePlayRenderer();
        redGamePlayRenderer.renderGamePlay(batch, reds, delta);

        // bear
        BearGamePlayRenderer bearGamePlayRenderer = rendererRegister.getBearGamePlayRenderer();
        bearGamePlayRenderer.renderGamePlay(batch, bears, delta);

        // mage
        MageGamePlayRenderer mageGamePlayRenderer = rendererRegister.getMageGamePlayRenderer();
        mageGamePlayRenderer.renderGamePlay(batch, mages, delta);
        // shield
        ShieldGamePlayRenderer shieldGamePlayRenderer = rendererRegister.getShieldGamePlayRenderer();
        shieldGamePlayRenderer.renderGamePlay(batch, shields, delta);

        // fire ball
        FireBallGamePlayRenderer fireBallGamePlayRenderer = rendererRegister.getFireBallGamePlayRenderer();
        fireBallGamePlayRenderer.renderGamePlay(batch, fireBalls, delta);

        // spike trap
        SpikeTrapGamePlayRenderer spikeTrapGamePlayRenderer = rendererRegister.getSpikeTrapGamePlayRenderer();
        spikeTrapGamePlayRenderer.renderSpikeTrapGamePlay(batch, spikeTraps);

        // coins
        CoinGamePlayRenderer coinGamePlayRenderer = rendererRegister.getCoinGamePlayRenderer();
        coinGamePlayRenderer.renderGamePlay(batch, delta, coins);

        // planet
        PlanetGamePlayRenderer planetGamePlayRenderer = rendererRegister.getPlanetGamePlayRenderer();
        planetGamePlayRenderer.renderPlanetGamePlay(batch, planets);

        // monster
        MonsterGamePlayRenderer monsterGamePlayRenderer = rendererRegister.getMonsterGamePlayRenderer();
        monsterGamePlayRenderer.renderGamePlay(batch, monsters, delta);

        // trap warning smoke
        TrapWarningSmokeGamePlayRenderer trapWarningSmokeGamePlayRenderer = rendererRegister.getTrapWarningSmokeGamePlayRenderer();
        trapWarningSmokeGamePlayRenderer.renderGamePlay(batch, smokes, delta);

        // sparks
        SparkGamePlayRenderer sparkGamePlayRenderer = rendererRegister.getSparkGamePlayRenderer();
        sparkGamePlayRenderer.renderGamePlay(batch, sparks, delta);

    }

}

