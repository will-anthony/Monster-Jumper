package com.jga.jumper.Renderers.sparks;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.jga.jumper.assets.RegionNames;
import com.jga.jumper.config.GameConfig;
import com.jga.jumper.entity.smoke_effects.SparkEffect;

public class SparkGamePlayRenderer {

    private TextureAtlas textureAtlas;

    private Animation<TextureRegion> sparkAnimation;
    private Array<TextureAtlas.AtlasRegion> sparkKeyframes;

    public SparkGamePlayRenderer(TextureAtlas textureAtlas) {
        this.textureAtlas = textureAtlas;
        createAnimations(this.textureAtlas);
    }

    private void createKeyframes(TextureAtlas textureAtlas) {
        sparkKeyframes = textureAtlas.findRegions(RegionNames.SPARK);
    }

    protected void createAnimations(TextureAtlas textureAtlas) {

        createKeyframes(textureAtlas);
        sparkAnimation = new Animation<TextureRegion>(0.2f,
                sparkKeyframes,
                Animation.PlayMode.NORMAL);
    }

    public void renderGamePlay(SpriteBatch batch, Array<SparkEffect> sparks, float delta) {
        for (int i = 0; i < sparks.size; i++) {
            SparkEffect spark = sparks.get(i);
            drawSparkAnimation(batch, spark, delta);
        }
    }

    private void drawSparkAnimation(SpriteBatch batch, SparkEffect spark, float delta) {
        spark.setHasSparkAnimationStarted(checkIfAnimationHasStarted(spark.hasSparkAnimationStarted(), spark));
        drawGamePlay(batch, spark, sparkAnimation, delta, 12);
    }

    protected void drawGamePlay(SpriteBatch batch, SparkEffect
            spark, Animation<TextureRegion> animation, float delta, float rotationOffset) {

        float animationTime = spark.getAnimationTime();
        spark.setAnimationTime(animationTime += delta);

        TextureRegion textureRegion = animation.getKeyFrame(animationTime);
        batch.draw(textureRegion, spark.getX(), spark.getY(),
                0f, 0,
                spark.getWidth(), spark.getHeight(),
                1f, 1f,
                GameConfig.START_ANGLE - spark.getRotation(rotationOffset));
    }

    protected boolean checkIfAnimationHasStarted(boolean hasAnimationStarted, SparkEffect spark) {
        if (!hasAnimationStarted) {
            resetAnimationTime(spark);
            setAllAnimationStatesToFalse(spark);
        }
        return true;
    }

    protected void setAllAnimationStatesToFalse( SparkEffect spark ) {
        spark.setHasSparkAnimationStarted(false);
    }

    protected void resetAnimationTime(SparkEffect spark) {
        spark.setAnimationTime(0);
    }

}
