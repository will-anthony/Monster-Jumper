package com.jga.jumper.Renderers.skull;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.jga.jumper.Renderers.EntityGamePlayRenderer;
import com.jga.jumper.assets.RegionNames;
import com.jga.jumper.entity.Skull;

public class SkullGamePlayRenderer extends EntityGamePlayRenderer<Skull> {

    public SkullGamePlayRenderer(TextureAtlas textureAtlas) {
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
        walkingKeyframes = textureAtlas.findRegions(RegionNames.SKULL_WALK);
        idleKeyframes = textureAtlas.findRegions(RegionNames.SKULL_IDLE);
        attackKeyframes = textureAtlas.findRegions(RegionNames.SKULL_ATTACK);
        deathKeyframes = textureAtlas.findRegions(RegionNames.SKULL_DEATH);
    }

    @Override
    protected void createAnimations(TextureAtlas textureAtlas) {
        createKeyframes(textureAtlas);
        super.walkingAnimation = new Animation<TextureRegion>(0.04f,
                walkingKeyframes,
                Animation.PlayMode.LOOP);

        super.walkingAnimationReversed = new Animation<TextureRegion>(0.04f,
                createFlippedTextures(walkingKeyframes),
                Animation.PlayMode.LOOP);

        super.idleAnimation = new Animation<TextureRegion>(0.06f,
                idleKeyframes,
                Animation.PlayMode.LOOP);

        super.idleAnimationReversed = new Animation<TextureRegion>(0.06f,
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
    public void renderGamePlay(SpriteBatch batch, Array<Skull> skulls, float delta) {
        for (int i = 0; i < skulls.size; i++) {
            Skull skull = skulls.get(i);

            switch (skull.getCurrentSkullState()) {
                case 0:
                    // spawning
                    drawSpawningAnimation(batch, skull, delta);
                    break;
                case 1:
                    // idle
                    drawIdleAnimation(batch, skull, delta);
                    break;
                case 2:
                    // walking
                    drawWalkingAnimation(batch, skull, delta);
                    break;
                case 3:
                    // attacking
                    drawAttackingAnimation(batch, skull, delta);
                    break;
                case 4:
                    // dying
                    drawDeathAnimation(batch, skull, delta);
                    break;
                case 5:

                    break;
            }
        }
    }

    private void drawSpawningAnimation(SpriteBatch batch, Skull skull, float delta) {
        skull.setHasIdleAnimationStarted(checkIfAnimationHasStarted(skull.hasIdleAnimationStarted(), skull));
        super.drawGamePlay(batch, skull, skull.isClockWise() ? super.idleAnimationReversed : super.idleAnimation, delta, 12);
    }

    private void drawIdleAnimation(SpriteBatch batch, Skull skull, float delta) {
        skull.setHasIdleAnimationStarted(checkIfAnimationHasStarted(skull.hasIdleAnimationStarted(), skull));
        super.drawGamePlay(batch, skull, skull.isClockWise() ? super.idleAnimationReversed : super.idleAnimation, delta, 12);
    }

    private void drawWalkingAnimation(SpriteBatch batch, Skull skull, float delta) {
        skull.setHasWalkAnimationStarted(checkIfAnimationHasStarted(skull.hasWalkAnimationStarted(), skull));
        super.drawGamePlay(batch, skull, skull.isClockWise() ? super.walkingAnimationReversed : super.walkingAnimation, delta, 12);
    }

    private void drawAttackingAnimation(SpriteBatch batch, Skull skull, float delta) {
        skull.setHasAttackAnimationStarted(checkIfAnimationHasStarted(skull.hasAttackAnimationStarted(), skull));
        super.drawGamePlay(batch, skull, skull.isClockWise() ? super.attackAnimationReversed : super.attackAnimation, delta, 12);
    }

    private void drawDeathAnimation(SpriteBatch batch, Skull skull, float delta) {
        skull.setHasDeadAnimationStarted(checkIfAnimationHasStarted(skull.hasDeadAnimationStarted(), skull));
        super.drawGamePlay(batch, skull, skull.isClockWise() ? super.deathAnimationReversed : super.deathAnimation, delta, 12);
    }

}
