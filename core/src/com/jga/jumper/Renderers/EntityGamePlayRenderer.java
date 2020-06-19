package com.jga.jumper.Renderers;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.jga.jumper.config.GameConfig;
import com.jga.jumper.entity.EntityBase;

public abstract class EntityGamePlayRenderer<T extends EntityBase> {
    // == attributes ==
    protected Animation<TextureRegion> walkingAnimation;
    protected Animation<TextureRegion> idleAnimation;
    protected Animation<TextureRegion> deathAnimation;

    protected float animationTime = 0;

    protected boolean hasIdleAnimationStarted;
    protected boolean hasWalkAnimationStarted;
    protected boolean hasDeadAnimationStarted;

    // == constructors ==
    public EntityGamePlayRenderer(TextureAtlas textureAtlas) {
        // create animations in constructor
        createAnimations(textureAtlas);
    }

    // == public methods ==
    protected abstract void createAnimations(TextureAtlas textureAtlas);

    public abstract void renderGamePlay(SpriteBatch batch, Array<T> entities, float delta);
    // check current states in this method and and call drawGamePlay with corresponding animation

    protected void drawGamePlay(SpriteBatch batch, Array<T> entities, Animation<TextureRegion> animation, float delta, float rotationOffset) {
        this.animationTime += delta;
        TextureRegion textureRegion = animation.getKeyFrame(animationTime);
        for (EntityBase entity : entities) {
            batch.draw(textureRegion, entity.getX(), entity.getY(),
                    0f, 0,
                    entity.getWidth(), entity.getHeight(),
                    1.5f, 1.5f,
                    GameConfig.START_ANGLE - entity.getRotation(rotationOffset));
        }
    }

    protected boolean checkIfAnimationHasStarted(boolean hasAnimationStarted) {
        if (!hasAnimationStarted) {
            resetAnimationTime();
            setAllAnimationStatesToFalse();
        }
        return true;
    }

    protected void setAllAnimationStatesToFalse() {
        hasIdleAnimationStarted = false;
        hasWalkAnimationStarted = false;
        hasDeadAnimationStarted = false;
    }

    protected void resetAnimationTime() {
        this.animationTime = 0;
    }
}