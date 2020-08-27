package com.jga.jumper.Renderers.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.jga.jumper.assets.AssetDescriptors;
import com.jga.jumper.common.FloatingScore;
import com.jga.jumper.common.GameManager;
import com.jga.jumper.config.GameConfig;
import com.jga.jumper.controllers.ControllerRegister;
import com.jga.jumper.screen.menu.GameOverOverlay;
import com.jga.jumper.screen.menu.MenuOverlay;
import com.jga.jumper.state_machines.GameState;

public class HudRenderer implements Disposable {

    // == attributes ==
    private final ControllerRegister controllerRegister;
    private final SpriteBatch spriteBatch;
    private final AssetManager assetManager;

    private Viewport hudViewport;
    private BitmapFont font;

    private final GlyphLayout layout = new GlyphLayout();

    private Stage hudStage;
    private MenuOverlay menuOverlay;
    private GameOverOverlay gameOverOverlay;

    // == constructors ==
    public HudRenderer(ControllerRegister controllerRegister, SpriteBatch spriteBatch, AssetManager assetManager) {
        this.controllerRegister = controllerRegister;
        this.spriteBatch = spriteBatch;
        this.assetManager = assetManager;

        init();
    }

    public void init() {
        hudViewport = new FitViewport(GameConfig.HUD_WIDTH, GameConfig.HUD_HEIGHT);
        hudStage = new Stage(hudViewport, spriteBatch);
        font = assetManager.get(AssetDescriptors.FONT);

        Skin skin = assetManager.get(AssetDescriptors.SKIN);

        menuOverlay = new MenuOverlay(skin, controllerRegister.getOverlayCallbackController().getCallBack());
        gameOverOverlay = new GameOverOverlay(skin, controllerRegister.getOverlayCallbackController().getCallBack());

        hudStage.addActor(menuOverlay);
        hudStage.addActor(gameOverOverlay);
        hudStage.setDebugAll(false);

        Gdx.input.setInputProcessor(hudStage);
    }

    // == public methods ==
    public void render() {
        renderHud();
    }

    public void resize(int width, int height) {
        hudViewport.update(width, height, true);
    }

    private void renderHud() {
        hudViewport.apply();

        menuOverlay.setVisible(false);
        gameOverOverlay.setVisible(false);

        GameState gameState = controllerRegister.getMasterController().getGameState();

        if (gameState.isPlayingOrReady()) {

            spriteBatch.setProjectionMatrix(hudViewport.getCamera().combined);
            spriteBatch.begin();

            drawHud();

            spriteBatch.end();
            return;
        }

        if (gameState.isMenu() && !menuOverlay.isVisible()) {
            menuOverlay.updateLabel();
            menuOverlay.setVisible(true);
        } else if (gameState.isGameOver() && !gameOverOverlay.isVisible()) {
            gameOverOverlay.updateLabels();
            gameOverOverlay.setVisible(true);
        }

        hudStage.act();
        hudStage.draw();
    }

    private void drawHud() {
        float padding = 20f;

        // level
        String levelString = "LEVEL: " + controllerRegister.getMasterController().getGameLevelDisplay();
        layout.setText(font, levelString);
        font.draw(spriteBatch, layout, padding, GameConfig.HUD_HEIGHT);

        // score
        String scoreString = "SCORE: " + GameManager.INSTANCE.getDisplayScore();
        layout.setText(font, scoreString);
        font.draw(spriteBatch, layout,
                GameConfig.HUD_WIDTH - layout.width - padding,
                GameConfig.HUD_HEIGHT
        );

        float startWaitTimer = controllerRegister.getMasterController().getStartWaitTimer();

        if (startWaitTimer >= 0) {
            int waitTime = (int) startWaitTimer;
            String waitTimeString = waitTime == 0 ? "GO!" : "" + waitTime;
            layout.setText(font, waitTimeString);

            font.draw(spriteBatch, layout,
                    (GameConfig.HUD_WIDTH - layout.width) / 2f,
                    (GameConfig.HUD_HEIGHT + layout.height) / 2f
            );
        }

        Color oldFontColor = new Color(font.getColor());

        drawFloatingScore();

        font.setColor(oldFontColor);
    }


    private void drawFloatingScore() {

        for (FloatingScore floatingScore : controllerRegister.getFloatingScoreController().getFloatingScores()) {

            font.setColor(floatingScore.getColor());
            layout.setText(font, floatingScore.getScoreString());

            font.draw(spriteBatch, layout,
                    floatingScore.getX() - layout.width / 2f,
                    floatingScore.getY() - layout.height / 2f);

        }
    }
    @Override
    public void dispose() {

    }
}
