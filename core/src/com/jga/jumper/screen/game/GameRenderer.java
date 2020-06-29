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
import com.badlogic.gdx.utils.viewport.Viewport;
import com.jga.jumper.Renderers.Background.BackgroundGamePlayRenderer;
import com.jga.jumper.Renderers.Monster.MonsterDebugRenderer;
import com.jga.jumper.Renderers.Monster.MonsterGamePlayRenderer;
import com.jga.jumper.Renderers.Planet.PlanetDebugRenderer;
import com.jga.jumper.Renderers.Planet.PlanetGamePlayRenderer;
import com.jga.jumper.Renderers.RendererRegister;
import com.jga.jumper.Renderers.Slug.SlugDebugRenderer;
import com.jga.jumper.Renderers.Slug.SlugGamePlayRenderer;
import com.jga.jumper.assets.AssetDescriptors;
import com.jga.jumper.config.GameConfig;
import com.jga.jumper.entity.Background;
import com.jga.jumper.entity.Monster;
import com.jga.jumper.entity.Planet;
import com.jga.jumper.entity.Slug;
import com.jga.jumper.entity.entity_providers.EntityProviderRegister;
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

        planets = entityProviderRegister.getPlanetEntityProvider().getEntities();
        monsters = entityProviderRegister.getMonsterEntityProvider().getEntities();
        slugs = entityProviderRegister.getSlugEntityProvider().getEntities();
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

        // draw planet
        PlanetDebugRenderer planetDebugRenderer = rendererRegister.getPlanetDebugRenderer();
        planetDebugRenderer.renderPlanetDebug(renderer, planets);

        // draw player
        MonsterDebugRenderer monsterDebugRenderer = rendererRegister.getMonsterDebugRenderer();
        monsterDebugRenderer.renderMonsterDebug(renderer, monsters);

        // slug
        SlugDebugRenderer slugDebugRenderer = rendererRegister.getSlugDebugRenderer();
        slugDebugRenderer.renderObstacleDebug(renderer, slugs);
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

        // planet
        PlanetGamePlayRenderer planetGamePlayRenderer = rendererRegister.getPlanetGamePlayRenderer();
        planetGamePlayRenderer.renderPlanetGamePlay(batch, planets);

        // monster
        MonsterGamePlayRenderer monsterGamePlayRenderer = rendererRegister.getMonsterGamePlayRenderer();
        monsterGamePlayRenderer.renderGamePlay(batch, monsters, delta);
    }
}

