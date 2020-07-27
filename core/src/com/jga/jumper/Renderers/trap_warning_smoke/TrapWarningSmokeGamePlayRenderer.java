package com.jga.jumper.Renderers.trap_warning_smoke;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.jga.jumper.assets.RegionNames;
import com.jga.jumper.config.GameConfig;
import com.jga.jumper.entity.smoke_effects.TrapWarningSmoke;

public class TrapWarningSmokeGamePlayRenderer {

    private TextureAtlas textureAtlas;

    private Animation<TextureRegion> spawnAnimation;
    private Array<TextureAtlas.AtlasRegion> spawnKeyframes;

    private Animation<TextureRegion> withdrawAnimation;
    private Array<TextureAtlas.AtlasRegion> withdrawKeyframes;

    public TrapWarningSmokeGamePlayRenderer(TextureAtlas textureAtlas) {
        this.textureAtlas = textureAtlas;
        createAnimations(this.textureAtlas);
    }

    private void createKeyframes(TextureAtlas textureAtlas) {
        spawnKeyframes = textureAtlas.findRegions(RegionNames.TRAP_WARNING_SMOKE_SPAWN);
        withdrawKeyframes = textureAtlas.findRegions(RegionNames.TRAP_WARNING_SMOKE_WITHDRAW);
    }

    protected void createAnimations(TextureAtlas textureAtlas) {

        createKeyframes(textureAtlas);
        spawnAnimation = new Animation<TextureRegion>(0.2f,
                spawnKeyframes,
                Animation.PlayMode.NORMAL);


        withdrawAnimation = new Animation<TextureRegion>(0.08f,
                withdrawKeyframes,
                Animation.PlayMode.NORMAL);
    }

    public void renderGamePlay(SpriteBatch batch, Array<TrapWarningSmoke> smokes, float delta) {
        for (int i = 0; i < smokes.size; i++) {
            TrapWarningSmoke smoke = smokes.get(i);

            switch (smoke.getCurrentState()) {
                case 0:
                    // spawning
                    drawSpawningAnimation(batch, smoke, delta);
                    break;
                case 1:
                    // withdraw
                    drawWithdrawAnimation(batch, smoke, delta);
                    break;
            }
        }
    }

    private void drawSpawningAnimation(SpriteBatch batch, TrapWarningSmoke smoke, float delta) {
        smoke.setHasSpawnAnimationStarted(checkIfAnimationHasStarted(smoke.hasSpawnAnimationStarted(), smoke));
        drawGamePlay(batch, smoke, spawnAnimation, delta, 12);
    }

    private void drawWithdrawAnimation(SpriteBatch batch, TrapWarningSmoke smoke, float delta) {
        smoke.setHasWithdrawAnimationStarted(checkIfAnimationHasStarted(smoke.hasWithdrawAnimationStarted(), smoke));
        drawGamePlay(batch, smoke, withdrawAnimation, delta, 12);
    }

    protected void drawGamePlay(SpriteBatch batch, TrapWarningSmoke
            smoke, Animation<TextureRegion> animation, float delta, float rotationOffset) {

        float animationTime = smoke.getAnimationTime();
        smoke.setAnimationTime(animationTime += delta);

        TextureRegion textureRegion = animation.getKeyFrame(animationTime);
        batch.draw(textureRegion, smoke.getX(), smoke.getY(),
                0f, 0,
                smoke.getWidth(), smoke.getHeight(),
                1f, 1f,
                GameConfig.START_ANGLE - smoke.getRotation(rotationOffset));
    }

    protected boolean checkIfAnimationHasStarted(boolean hasAnimationStarted, TrapWarningSmoke smoke) {
        if (!hasAnimationStarted) {
            resetAnimationTime(smoke);
            setAllAnimationStatesToFalse(smoke);
        }
        return true;
    }

    protected void setAllAnimationStatesToFalse(TrapWarningSmoke smoke) {
        smoke.setHasSpawnAnimationStarted(false);
        smoke.setHasWithdrawAnimationStarted(false);
    }

    protected void resetAnimationTime(TrapWarningSmoke smoke) {
        smoke.setAnimationTime(0);
    }
}
