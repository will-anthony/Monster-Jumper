package com.jga.jumper.screen.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.jga.jumper.Renderers.Background.BackgroundGamePlayRenderer;
import com.jga.jumper.Renderers.EntityDebugKillColliderRenderer;
import com.jga.jumper.Renderers.EntityDebugRenderer;
import com.jga.jumper.Renderers.Monster.MonsterDebugRenderer;
import com.jga.jumper.Renderers.Monster.MonsterGamePlayRenderer;
import com.jga.jumper.Renderers.Planet.PlanetDebugRenderer;
import com.jga.jumper.Renderers.Planet.PlanetGamePlayRenderer;
import com.jga.jumper.Renderers.RendererRegister;
import com.jga.jumper.Renderers.Slug.SlugDebugRenderer;
import com.jga.jumper.Renderers.Slug.SlugGamePlayRenderer;
import com.jga.jumper.Renderers.bear.BearDebugRenderer;
import com.jga.jumper.Renderers.bear.BearGamePlayRenderer;
import com.jga.jumper.Renderers.fireball.FireBallDebugRenderer;
import com.jga.jumper.Renderers.fireball.FireBallGamePlayRenderer;
import com.jga.jumper.Renderers.mage.MageDebugRenderer;
import com.jga.jumper.Renderers.mage.MageGamePlayRenderer;
import com.jga.jumper.assets.AssetDescriptors;
import com.jga.jumper.box2d.Box2DTest;
import com.jga.jumper.config.GameConfig;
import com.jga.jumper.entity.Background;
import com.jga.jumper.entity.Bear;
import com.jga.jumper.entity.Mage;
import com.jga.jumper.entity.Monster;
import com.jga.jumper.entity.Planet;
import com.jga.jumper.entity.Slug;
import com.jga.jumper.entity.entity_providers.EntityProviderRegister;
import com.jga.jumper.entity.projectiles.FireBall;
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
    private Array<Bear> bears;
    private Array<Mage> mages;
    private Array<FireBall> fireBalls;

    private final SpriteBatch batch;
    private final AssetManager assetManager;

    private OrthographicCamera camera;
    private Viewport viewport;
    private ShapeRenderer renderer;

    private DebugCameraController debugCameraController;

    private ParticleEffect spaceDust;
    private TextureAtlas gamePlayAtlas;

    private boolean debugIsOn = true;

    // == constructors ==
    public GameRenderer(SpriteBatch batch, AssetManager assetManager, EntityProviderRegister entityProviderRegister, Box2DTest box2DTest) {
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

        planets = entityProviderRegister.getPlanetEntityProvider().getEntities();
        monsters = entityProviderRegister.getMonsterEntityProvider().getEntities();
        slugs = entityProviderRegister.getSlugEntityProvider().getEntities();
        bears = entityProviderRegister.getBearEntityProvider().getEntities();
        mages = entityProviderRegister.getMageEntityProvider().getEntities();
        fireBalls = entityProviderRegister.getFireBallEntityProvider().getEntities();
        backgrounds = entityProviderRegister.getBackgroundEntityProvider().getEntities();

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

    // == private methods ==
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

        // mage polygon debug
        for (Mage mage : mages) {
            entityDebugRenderer.renderEntityDebugLines(renderer, mage.getPolygonCollider().getTransformedVertices());
            entityDebugRenderer.renderEntityDebugLines(renderer, mage.getKillCollider().getTransformedVertices());
        }

        // bear polygon debug
        for (Bear bear : bears) {
            entityDebugRenderer.renderEntityDebugLines(renderer, bear.getPolygonCollider().getTransformedVertices());
            entityDebugRenderer.renderEntityDebugLines(renderer, bear.getKillCollider().getTransformedVertices());
        }

        // fireball polygon debug
        for (FireBall fireBall : fireBalls) {
            entityDebugRenderer.renderEntityDebugLines(renderer, fireBall.getPolygonCollider().getTransformedVertices());
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

        // bear
        BearGamePlayRenderer bearGamePlayRenderer = rendererRegister.getBearGamePlayRenderer();
        bearGamePlayRenderer.renderGamePlay(batch, bears, delta);

        // mage
        MageGamePlayRenderer mageGamePlayRenderer = rendererRegister.getMageGamePlayRenderer();
        mageGamePlayRenderer.renderGamePlay(batch, mages, delta);

        // fire ball
        FireBallGamePlayRenderer fireBallGamePlayRenderer = rendererRegister.getFireBallGamePlayRenderer();
        fireBallGamePlayRenderer.renderGamePlay(batch, fireBalls, delta);

        // planet
        PlanetGamePlayRenderer planetGamePlayRenderer = rendererRegister.getPlanetGamePlayRenderer();
        planetGamePlayRenderer.renderPlanetGamePlay(batch, planets);

        // monster
        MonsterGamePlayRenderer monsterGamePlayRenderer = rendererRegister.getMonsterGamePlayRenderer();
        monsterGamePlayRenderer.renderGamePlay(batch, monsters, delta);

    }
}

