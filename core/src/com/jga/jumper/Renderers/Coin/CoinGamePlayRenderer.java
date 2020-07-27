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
    private Animation<TextureRegion> coinSpinningAnimation;
    protected Array<TextureAtlas.AtlasRegion> coinSpinningKeyframes;

    private Animation<TextureRegion> coinCollectedAnimation;
    protected Array<TextureAtlas.AtlasRegion> coinCollectedKeyframes;

    // == constructors ==
    public CoinGamePlayRenderer(TextureAtlas gamePlayAtlas) {

        coinSpinningAnimation = new Animation<TextureRegion>(0.05f,
                gamePlayAtlas.findRegions(RegionNames.COIN),
                Animation.PlayMode.LOOP);

        coinCollectedAnimation = new Animation<TextureRegion>(0.06f,
                gamePlayAtlas.findRegions(RegionNames.COIN_COLLECTED),
                Animation.PlayMode.NORMAL);
    }

    public void renderGamePlay(SpriteBatch batch, float delta, Array<Coin> coins) {
        for (Coin coin : coins) {
            if (coin.getCoinState() == GameConfig.COIN_COLLECTED) {
                drawCoinCollectedAnimation(batch, coin, delta);
            }
            else {
                drawCoinSpinningAnimation(batch, coin, delta);
            }
        }
    }

    public void drawGamePlay(SpriteBatch batch, Coin coin, Animation<TextureRegion> animation, float delta) {

        float animationTime = coin.getAnimationTime();
        coin.setAnimationTime(animationTime += delta);

        TextureRegion coinRegion = animation.getKeyFrame(animationTime);

        batch.draw(coinRegion,
                coin.getX(), coin.getY(),
                0, 0,
                coin.getWidth(), coin.getHeight(),
                1, 1,
                GameConfig.START_ANGLE - coin.getAngleDegrees() + coin.getVelocityX());
    }


    private void drawCoinSpinningAnimation(SpriteBatch batch, Coin coin, float delta) {
        coin.setHasCoinSpinningAnimationStarted(checkIfAnimationHasStarted(coin.hasCoinSpinningAnimationStarted(), coin));
        drawGamePlay(batch, coin, coinSpinningAnimation, delta);
    }

    private void drawCoinCollectedAnimation(SpriteBatch batch, Coin coin, float delta) {
        coin.setHasCoinCollectedAnimationStarted(checkIfAnimationHasStarted(coin.hasCoinCollectedAnimationStarted(), coin));
        drawGamePlay(batch, coin, coinCollectedAnimation, delta);
    }

    protected boolean checkIfAnimationHasStarted(boolean hasAnimationStarted, Coin coin) {
        if (!hasAnimationStarted) {
            resetAnimationTime(coin);
            setAllAnimationStatesToFalse(coin);
        }
        return true;
    }

    protected void setAllAnimationStatesToFalse(Coin coin) {
        coin.setHasCoinSpinningAnimationStarted(false);
        coin.setHasCoinCollectedAnimationStarted(false);
    }

    protected void resetAnimationTime(Coin coin) {
        coin.setAnimationTime(0);
    }
}
