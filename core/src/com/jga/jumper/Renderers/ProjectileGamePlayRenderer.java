package com.jga.jumper.Renderers;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.jga.jumper.config.GameConfig;
import com.jga.jumper.entity.projectiles.FireBall;
import com.jga.jumper.entity.projectiles.ProjectileBase;

public abstract class ProjectileGamePlayRenderer<T extends ProjectileBase> {
    protected Animation<TextureRegion> spawnAnimation;
    protected Animation<TextureRegion> spawnAnimationReversed;
    protected Array<TextureAtlas.AtlasRegion> spawnKeyframes;

    protected Animation<TextureRegion> moveAnimation;
    protected Animation<TextureRegion> moveAnimationReversed;
    protected Array<TextureAtlas.AtlasRegion> moveKeyframes;

    protected Animation<TextureRegion> deathAnimation;
    protected Animation<TextureRegion> deathAnimationReversed;
    protected Array<TextureAtlas.AtlasRegion> deathKeyframes;

    // == constructors ==
    public ProjectileGamePlayRenderer(TextureAtlas textureAtlas) {
        // create animations in constructor
        createAnimations(textureAtlas);
    }

    // == public methods ==
    protected abstract void createAnimations(TextureAtlas textureAtlas);

    public abstract void renderGamePlay(SpriteBatch batch, Array<T> projectiles, float delta);
    // check current states in this method and and call drawGamePlay with corresponding animation

    protected void drawGamePlay(SpriteBatch batch, T projectile, Animation<TextureRegion> animation, float delta, float rotationOffset) {

        float animationTime = projectile.getAnimationTime();
        projectile.setAnimationTime(animationTime += delta);

        TextureRegion textureRegion = animation.getKeyFrame(animationTime);
        batch.draw(textureRegion, projectile.getX(), projectile.getY(),
                0f, 0,
                projectile.getWidth(), projectile.getHeight(),
                1f, 1f,
                GameConfig.START_ANGLE - projectile.getRotation(rotationOffset));
    }

    protected boolean checkIfAnimationHasStarted(boolean hasAnimationStarted, T projectile) {
        if (!hasAnimationStarted) {
            resetAnimationTime(projectile);
            setAllAnimationStatesToFalse(projectile);
        }
        return true;
    }

    protected void setAllAnimationStatesToFalse(T projectile) {
        projectile.setHasMoveAnimationStarted(false);
        projectile.setHasSpawnAnimationStarted(false);
        projectile.setHasDeathAnimationStarted(false);
    }

    protected void resetAnimationTime(T projectile) {
        projectile.setAnimationTime(0);
    }
}
