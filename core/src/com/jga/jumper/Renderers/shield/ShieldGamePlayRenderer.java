package com.jga.jumper.Renderers.shield;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.jga.jumper.assets.RegionNames;
import com.jga.jumper.config.GameConfig;
import com.jga.jumper.entity.Shield;

public class ShieldGamePlayRenderer {

    private TextureAtlas textureAtlas;

    private Animation<TextureRegion> animation;
    private Array<TextureAtlas.AtlasRegion> keyframes;

    public ShieldGamePlayRenderer(TextureAtlas textureAtlas) {
        this.textureAtlas = textureAtlas;
        createKeyframes(textureAtlas);
        createAnimations();
    }

    private void createKeyframes(TextureAtlas textureAtlas) {
        keyframes = textureAtlas.findRegions(RegionNames.SHIELD);
    }

    protected void createAnimations() {

        animation = new Animation<TextureRegion>(0.08f,
                keyframes,
                Animation.PlayMode.LOOP);

    }

    public void renderGamePlay(SpriteBatch batch, Array<Shield> shields, float delta) {
        for (int i = 0; i < shields.size; i++) {
            Shield shield = shields.get(i);
            float alpha = shield.getShieldAlphaPercentage();

            batch.setColor(1.0f, 1.0f, 1.0f, alpha);
            drawGamePlay(batch, shield, animation, delta, 12);
            batch.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        }
    }

    private void drawGamePlay(SpriteBatch batch, Shield
            shield, Animation<TextureRegion> animation, float delta, float rotationOffset) {

        float animationTime = shield.getAnimationTime();
        shield.setAnimationTime(animationTime += delta);

        TextureRegion textureRegion = animation.getKeyFrame(animationTime);
        batch.draw(textureRegion, shield.getX(), shield.getY(),
                0f, 0,
                shield.getWidth(), shield.getHeight(),
                1f, 1f,
                GameConfig.START_ANGLE - shield.getRotation(rotationOffset));
    }
}
