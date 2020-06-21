package com.jga.jumper.Renderers.Slug;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.jga.jumper.Renderers.EntityGamePlayRenderer;
import com.jga.jumper.assets.RegionNames;
import com.jga.jumper.entity.Slug;

public class SlugGamePlayRenderer extends EntityGamePlayRenderer<Slug> {

    public SlugGamePlayRenderer(TextureAtlas textureAtlas) {
        super(textureAtlas);
    }

    @Override
    protected void createAnimations(TextureAtlas textureAtlas) {
        super.walkingAnimation = new Animation<TextureRegion>(0.08f,
                textureAtlas.findRegions(RegionNames.WALK),
                Animation.PlayMode.LOOP_PINGPONG);

        super.idleAnimation = new Animation<TextureRegion>(0.1f,
                textureAtlas.findRegions(RegionNames.SLUG_IDLE),
                Animation.PlayMode.LOOP);

        super.deathAnimation = new Animation<TextureRegion>(0.06f,
                textureAtlas.findRegions(RegionNames.SLUG_DEATH),
                Animation.PlayMode.NORMAL);
    }

    @Override
    public void renderGamePlay(SpriteBatch batch, Array<Slug> slugs, float delta) {
        for (int i = 0; i < slugs.size; i++) {
            Slug slug = slugs.get(i);

            switch (slug.getCurrentSlugState()) {
                case 0:
                    // spawning
                    drawSpawningAnimation(batch, slug, delta);
                    break;
                case 1:
                    // idle
                    drawIdleAnimation(batch, slug, delta);
                    break;
                case 2:
                    // walking
                    drawWalkingAnimation(batch, slug, delta);
                    break;
                case 3:
                    // dying
                    drawDeathAnimation(batch, slug, delta);
                    break;
                case 4:
                    break;
            }
        }
    }

    private void drawSpawningAnimation(SpriteBatch batch, Slug slug, float delta) {
        slug.setHasIdleAnimationStarted(checkIfAnimationHasStarted(slug.hasIdleAnimationStarted(), slug));
        super.drawGamePlay(batch, slug, super.idleAnimation, delta, 12);
    }

    private void drawIdleAnimation(SpriteBatch batch, Slug slug, float delta) {
        slug.setHasIdleAnimationStarted(checkIfAnimationHasStarted(slug.hasIdleAnimationStarted(), slug));
        super.drawGamePlay(batch, slug, super.idleAnimation, delta, 12);
    }

    private void drawWalkingAnimation(SpriteBatch batch, Slug slug, float delta) {
        slug.setHasWalkAnimationStarted(checkIfAnimationHasStarted(slug.hasWalkAnimationStarted(), slug));
        super.drawGamePlay(batch, slug, super.idleAnimation, delta, 12);
    }

    private void drawDeathAnimation(SpriteBatch batch, Slug slug, float delta) {
        slug.setHasDeadAnimationStarted(checkIfAnimationHasStarted(slug.hasDeadAnimationStarted(), slug));
        super.drawGamePlay(batch, slug, super.deathAnimation, delta, 12);
    }

}

