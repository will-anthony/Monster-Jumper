package com.jga.jumper.screen.menu;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.jga.jumper.assets.ButtonStyleNames;
import com.jga.jumper.assets.RegionNames;
import com.jga.jumper.common.GameManager;

public class GameOverOverlay extends Table {

    // == attributes ==
    private final OverlayCallBack callBack;

    private Label scoreLabel;
    private Label highScoreLabel;

    // == constructors ==
    public GameOverOverlay(Skin skin, OverlayCallBack callBack) {
        super(skin);
        this.callBack = callBack;
        init();
    }

    private void init() {
        defaults(). pad(20);

        Image gameOverImage = new Image(getSkin(), RegionNames.GAME_OVER);

        // score table
        Table scoreTable = new Table(getSkin());
        scoreTable.defaults().pad(10);
        scoreTable.setBackground(RegionNames.PANEL);

        scoreTable.add("SCORE ").row();
        scoreLabel = new Label("", getSkin());
        scoreTable.add(scoreLabel).row();
        scoreTable.add("BEST ").row();
        highScoreLabel = new Label("", getSkin());
        scoreTable.add(highScoreLabel);

        scoreTable.center();

        // button table
        Table buttonTable = new Table();

        ImageButton homeButton = new ImageButton(getSkin(), ButtonStyleNames.HOME);
        homeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                callBack.home();
            }
        });

        ImageButton restartButton = new ImageButton(getSkin(), ButtonStyleNames.RESTART);
        restartButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                callBack.ready();
            }
        });

        buttonTable.add(homeButton).left().expandX().padRight(60);
        buttonTable.add(restartButton).right().expandX().padLeft(60);

        add(gameOverImage).row();
        add(scoreTable).row();
        add(buttonTable).expand().bottom();

        center();
        setFillParent(true);
        pack();

        updateLabels();
    }

    public void updateLabels() {
        scoreLabel.setText("" + GameManager.INSTANCE.getScore());
        highScoreLabel.setText("" + GameManager.INSTANCE.getHighScore());
    }
}
