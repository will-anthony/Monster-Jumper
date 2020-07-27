package com.jga.jumper.Renderers;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.jga.jumper.config.GameConfig;
import com.jga.jumper.entity.abstract_classes_and_interfaces.EnemyBase;

public abstract class EntityGamePlayRenderer<T extends EnemyBase> {
    // == attributes ==
    protected Animation<TextureRegion> walkingAnimation;
    protected Animation<TextureRegion> walkingAnimationReversed;
    protected Array<TextureAtlas.AtlasRegion> walkingKeyframes;

    protected Animation<TextureRegion> idleAnimation;
    protected Animation<TextureRegion> idleAnimationReversed;
    protected Array<TextureAtlas.AtlasRegion> idleKeyframes;

    protected Animation<TextureRegion> attackAnimation;
    protected Animation<TextureRegion> attackAnimationReversed;
    protected Array<TextureAtlas.AtlasRegion> attackKeyframes;

    protected Animation<TextureRegion> deathAnimation;
    protected Array<TextureAtlas.AtlasRegion> deathKeyframes;
    protected Animation<TextureRegion> deathAnimationReversed;

    // == constructors ==
    public EntityGamePlayRenderer(TextureAtlas textureAtlas) {
        // create animations in constructor
        createAnimations(textureAtlas);
    }

    // == public methods ==
    protected abstract void createAnimations(TextureAtlas textureAtlas);

    public abstract void renderGamePlay(SpriteBatch batch, Array<T> entities, float delta);
    // check current states in this method and and call drawGamePlay with corresponding animation

    protected void drawGamePlay(SpriteBatch batch, T entity, Animation<TextureRegion> animation, float delta, float rotationOffset) {

        float animationTime = entity.getAnimationTime();
        entity.setAnimationTime(animationTime += delta);

        TextureRegion textureRegion = animation.getKeyFrame(animationTime);
        batch.draw(textureRegion, entity.getX(), entity.getY(),
                0f, 0,
                entity.getWidth(), entity.getHeight(),
                1f, 1f,
                GameConfig.START_ANGLE - entity.getRotation(rotationOffset));
    }

    protected boolean checkIfAnimationHasStarted(boolean hasAnimationStarted, T entity) {
        if (!hasAnimationStarted) {
            resetAnimationTime(entity);
            setAllAnimationStatesToFalse(entity);
        }
        return true;
    }

    protected void setAllAnimationStatesToFalse(T entity) {
       entity.setHasIdleAnimationStarted(false);
       entity.setHasWalkAnimationStarted(false);
       entity.setHasAttackAnimationStarted(false);
       entity.setHasDeadAnimationStarted(false);
    }

    protected void resetAnimationTime(T entity) {
        entity.setAnimationTime(0);
    }
}