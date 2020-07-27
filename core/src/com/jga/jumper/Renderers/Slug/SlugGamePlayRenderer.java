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

    private Array<TextureAtlas.AtlasRegion> createFlippedTextures(Array<TextureAtlas.AtlasRegion> keyframes) {

        Array<TextureAtlas.AtlasRegion> reversedTextures = new Array<>();

        for(TextureAtlas.AtlasRegion keyframe: keyframes) {
            TextureAtlas.AtlasRegion region = new TextureAtlas.AtlasRegion(keyframe);
            region.flip(true, false);
            reversedTextures.add(region);
        }
        return reversedTextures;
    }

    private void createKeyframes(TextureAtlas textureAtlas) {
        walkingKeyframes = textureAtlas.findRegions(RegionNames.SLUG_WALK);
        idleKeyframes = textureAtlas.findRegions(RegionNames.SLUG_IDLE);
        attackKeyframes = textureAtlas.findRegions(RegionNames.SLUG_ATTACK);
        deathKeyframes = textureAtlas.findRegions(RegionNames.SLUG_DEATH);
    }

    @Override
    protected void createAnimations(TextureAtlas textureAtlas) {
        createKeyframes(textureAtlas);
        super.walkingAnimation = new Animation<TextureRegion>(0.08f,
                walkingKeyframes,
                Animation.PlayMode.LOOP_PINGPONG);

        super.walkingAnimationReversed = new Animation<TextureRegion>(0.08f,
                createFlippedTextures(walkingKeyframes),
                Animation.PlayMode.LOOP_PINGPONG);

        super.idleAnimation = new Animation<TextureRegion>(0.1f,
                idleKeyframes,
                Animation.PlayMode.LOOP);

        super.idleAnimationReversed = new Animation<TextureRegion>(0.1f,
                createFlippedTextures(idleKeyframes),
                Animation.PlayMode.LOOP);

        super.attackAnimation = new Animation<TextureRegion>(0.1f,
                attackKeyframes,
                Animation.PlayMode.LOOP);

        super.attackAnimationReversed = new Animation<TextureRegion>(0.1f,
                createFlippedTextures(attackKeyframes),
                Animation.PlayMode.LOOP);

        super.deathAnimation = new Animation<TextureRegion>(0.06f,
                deathKeyframes,
                Animation.PlayMode.NORMAL);

        super.deathAnimationReversed = new Animation<TextureRegion>(0.06f,
                createFlippedTextures(deathKeyframes),
                Animation.PlayMode.NORMAL);
    }

    @Override
    public void renderGamePlay(SpriteBatch batch, Array<Slug> slugs, float delta) {
        for (int i = 0; i < slugs.size; i++) {
            Slug slug = slugs.get(i);

            switch (slug.getCurrentState()) {
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
                    // attacking
                    drawAttackingAnimation(batch, slug, delta);
                    break;
                case 4:
                    // dying
                    drawDeathAnimation(batch, slug, delta);
                    break;
                case 5:

                    break;
            }
        }
    }

    private void drawSpawningAnimation(SpriteBatch batch, Slug slug, float delta) {
        slug.setHasIdleAnimationStarted(checkIfAnimationHasStarted(slug.hasIdleAnimationStarted(), slug));
        super.drawGamePlay(batch, slug, slug.isClockWise() ? super.idleAnimationReversed : super.idleAnimation, delta, 12);
    }

    private void drawIdleAnimation(SpriteBatch batch, Slug slug, float delta) {
        slug.setHasIdleAnimationStarted(checkIfAnimationHasStarted(slug.hasIdleAnimationStarted(), slug));
        super.drawGamePlay(batch, slug, slug.isClockWise() ? super.idleAnimationReversed : super.idleAnimation, delta, 12);
    }

    private void drawWalkingAnimation(SpriteBatch batch, Slug slug, float delta) {
        slug.setHasWalkAnimationStarted(checkIfAnimationHasStarted(slug.hasWalkAnimationStarted(), slug));
        super.drawGamePlay(batch, slug, slug.isClockWise() ? super.idleAnimationReversed : super.idleAnimation, delta, 12);
    }

    private void drawAttackingAnimation(SpriteBatch batch, Slug slug, float delta) {
        slug.setHasAttackAnimationStarted(checkIfAnimationHasStarted(slug.hasAttackAnimationStarted(), slug));
        super.drawGamePlay(batch, slug, slug.isClockWise() ? super.attackAnimationReversed : super.attackAnimation, delta, 12);
    }

    private void drawDeathAnimation(SpriteBatch batch, Slug slug, float delta) {
        slug.setHasDeadAnimationStarted(checkIfAnimationHasStarted(slug.hasDeadAnimationStarted(), slug));
        super.drawGamePlay(batch, slug, slug.isClockWise() ? super.deathAnimationReversed : super.deathAnimation, delta, 12);
    }

}

