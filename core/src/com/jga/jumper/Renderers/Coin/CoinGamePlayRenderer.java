package com.jga.jumper.Renderers.Coin;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.jga.jumper.assets.RegionNames;
import com.jga.jumper.config.GameConfig;
import com.jga.jumper.entity.Coin;

public class CoinGamePlayRenderer {

    // == attributes ==
    private Animation<TextureRegion> coinAnimation;

    // == constructors ==
    public CoinGamePlayRenderer(TextureAtlas gamePlayAtlas) {

        coinAnimation = new Animation<TextureRegion>(0.2f,
                gamePlayAtlas.findRegions(RegionNames.COIN),
                Animation.PlayMode.LOOP_PINGPONG);
    }

    // == public methods ==
    public void renderCoinGamePlay(SpriteBatch batch, float animationTime, Array<Coin> coins) {
        TextureRegion coinRegion = coinAnimation.getKeyFrame(animationTime);

        for (Coin coin : coins) {
            batch.draw(coinRegion,
                    coin.getX(), coin.getY(),
                    0, 0,
                    coin.getWidth(), coin.getHeight(),
                    coin.getScale(), coin.getScale(),
                    GameConfig.START_ANGLE - coin.getAngleDegree());
        }
    }
}
