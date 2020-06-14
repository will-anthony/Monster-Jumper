package com.jga.jumper.Renderers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.utils.Array;
import com.jga.jumper.assets.RegionNames;
import com.jga.jumper.entity.Coin;

public class CoinDebugRenderer {

    // == constructors ==
    public CoinDebugRenderer() {
    }

    // == public methods ==
    public void renderDebug(ShapeRenderer renderer, Array<Coin> coins) {
        for (Coin coin : coins) {
            Circle coinBounds = coin.getBounds();
            renderer.circle(coinBounds.x, coinBounds.y, coinBounds.radius, 30);
        }
    }
}
