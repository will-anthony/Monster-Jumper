package com.jga.jumper.screen.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.jga.jumper.Renderers.hud.HudRenderer;
import com.jga.jumper.assets.AssetDescriptors;
import com.jga.jumper.box2d.Box2DTest;
import com.jga.jumper.common.SoundListener;
import com.jga.jumper.controllers.ControllerRegister;
import com.jga.jumper.entity.entity_providers.EntityProviderRegister;
import com.jga.util.GdxUtils;

public class GameScreen extends ScreenAdapter {

    // == attributes ==
    private final GameBase game;
    private final AssetManager assetManager;
    private final SoundListener soundListener;

    private ControllerRegister controllerRegister;
    private GameRenderer gameRenderer;
    private HudRenderer hudRenderer;

    private Sound coinSound;
    private Sound popSound;
    private Sound jumpSound;
    private Sound loseSound;

    // == constructors ==
    public GameScreen(GameBase game) {
        this.game = game;
        this.assetManager = game.getAssetManager();

        soundListener = new SoundListener() {
            @Override
            public void hitCoin() {
                coinSound.play();
            }

            @Override
            public void jump() {
                jumpSound.play();
            }

            @Override
            public void pop() {
                popSound.play();
            }

            @Override
            public void lose() {
                loseSound.play();
            }
        };

        this.controllerRegister = new ControllerRegister(this.soundListener);
    }

    // == public methods ==
    @Override
    public void show() {

        coinSound = assetManager.get(AssetDescriptors.COIN);
//        popSound = assetManager.get(AssetDescriptors.POP);
        jumpSound = assetManager.get(AssetDescriptors.JUMP);
        loseSound = assetManager.get(AssetDescriptors.LOSE);

        final EntityProviderRegister entityProviderRegister = new EntityProviderRegister(controllerRegister);
        gameRenderer = new GameRenderer(game.getBatch(), assetManager, entityProviderRegister);
        hudRenderer = new HudRenderer(controllerRegister, game.getBatch(), assetManager);

    }

    @Override
    public void render(float delta) {
        GdxUtils.clearScreen();

        controllerRegister.getMasterController().update(delta);
        gameRenderer.render(delta);
        hudRenderer.render();

    }

    @Override
    public void resize(int width, int height) {
        gameRenderer.resize(width, height);
        hudRenderer.resize(width, height);
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        gameRenderer.dispose();
    }
}
