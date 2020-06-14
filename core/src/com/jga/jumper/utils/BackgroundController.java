package com.jga.jumper.utils;

import com.badlogic.gdx.utils.Array;
import com.jga.jumper.config.GameConfig;
import com.jga.jumper.entity.Background;

import java.sql.Time;

// add a background class, pass both backgrounds to this class, when background1 jumps, set to top of background2

public class BackgroundController {

    // == attributes ==
    private float scrollingSpeed;
    private Background bG1, bG2;
    private Array<Background> backgrounds = new Array<>();

    // == constructors ==
    public BackgroundController() {
        this.bG1 = new Background();
        this.bG2 = new Background();
        init();
    }

    // == init ==
    private void init() {

        scrollingSpeed = GameConfig.BACKGROUND_SCROLLING_SPEED;

        bG1.setSize(GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT);
        bG1.setXPos(0);
        bG1.setYPos(0);

        bG2.setSize(GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT);
        bG2.setXPos(0);
        bG2.setYPos(bG1.getYPos() + GameConfig.WORLD_HEIGHT);

        backgrounds.add(bG1);
        backgrounds.add(bG2);
    }

    // == public methods ==
    public void update(float delta) {
        float currentYPos = bG1.getYPos();

        bG1.setYPos(currentYPos -= delta * scrollingSpeed);
        bG2.setYPos(bG1.getYPos() + GameConfig.WORLD_HEIGHT);

        if (bG1.getYPos() <= -GameConfig.WORLD_HEIGHT) {
            bG1.setYPos(bG2.getYPos());
        }
    }

    public Background getBG1() {
        return bG1;
    }

    public Background getBG2() {
        return bG2;
    }

    public Array<Background> getBackgrounds() {
        return backgrounds;
    }
}
