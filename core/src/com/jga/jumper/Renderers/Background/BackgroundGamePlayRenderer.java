package com.jga.jumper.Renderers.Background;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.jga.jumper.assets.RegionNames;
import com.jga.jumper.entity.Background;

public class BackgroundGamePlayRenderer {

    // == attributes ==
    private TextureAtlas gamePlayAtlas;

    // == constructors ==
    public BackgroundGamePlayRenderer(TextureAtlas gamePlayAtlas) {
        this.gamePlayAtlas = gamePlayAtlas;
    }

    // == public methods ==
    public void renderBackgroundGamePlay(SpriteBatch batch, Array<Background> backgrounds) {

        TextureRegion backgroundRegion = gamePlayAtlas.findRegion(RegionNames.BACKGROUND);
        for (Background background : backgrounds) {

            batch.draw(backgroundRegion,
                    background.getXPos(), background.getYPos(),
                    background.getWidth(), background.getHeight()
            );
        }
    }
}
