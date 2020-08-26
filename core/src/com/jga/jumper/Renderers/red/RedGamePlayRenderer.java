package com.jga.jumper.Renderers.red;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.jga.jumper.Renderers.EntityGamePlayRenderer;
import com.jga.jumper.assets.RegionNames;
import com.jga.jumper.config.GameConfig;
import com.jga.jumper.entity.Red;

public class RedGamePlayRenderer extends EntityGamePlayRenderer<Red> {

    private Animation<TextureRegion> damageAnimation;
    private Animation<TextureRegion> damageAnimationReversed;
    private Array<TextureAtlas.AtlasRegion> damagedKeyframes;

    public RedGamePlayRenderer(TextureAtlas textureAtlas) {
        super(textureAtlas);
    }

    @Override
    protected void drawGamePlay(SpriteBatch batch, Red entity, Animation<TextureRegion> animation, float delta, float rotationOffset) {
        if(!entity.isClockWise()) {
            super.drawGamePlay(batch, entity, animation, delta, rotationOffset);
        } else {
            drawClockwiseGamePlay(batch, entity, animation, delta, rotationOffset);
        }
    }

    private void drawClockwiseGamePlay(SpriteBatch batch, Red entity, Animation<TextureRegion> animation, float delta, float rotationOffset) {
        float animationTime = entity.getAnimationTime();
        entity.setAnimationTime(animationTime += delta);

        TextureRegion textureRegion = animation.getKeyFrame(animationTime);
        batch.draw(textureRegion, entity.getX() + 0.3f, entity.getY(),
                -0.3f, 0,
                entity.getWidth(), entity.getHeight(),
                1f, 1f,
                GameConfig.START_ANGLE - entity.getRotation(rotationOffset));
    }


    private Array<TextureAtlas.AtlasRegion> createFlippedTextures(Array<TextureAtlas.AtlasRegion> keyframes) {

        Array<TextureAtlas.AtlasRegion> reversedTextures = new Array<>();

        for (TextureAtlas.AtlasRegion keyframe : keyframes) {
            TextureAtlas.AtlasRegion region = new TextureAtlas.AtlasRegion(keyframe);
            region.flip(true, false);
            reversedTextures.add(region);
        }
        return reversedTextures;
    }

    private void createKeyframes(TextureAtlas textureAtlas) {
        walkingKeyframes = textureAtlas.findRegions(RegionNames.RED_WALK);
        idleKeyframes = textureAtlas.findRegions(RegionNames.RED_IDLE);
        damagedKeyframes = textureAtlas.findRegions(RegionNames.RED_DAMAGED);
        attackKeyframes = textureAtlas.findRegions(RegionNames.RED_ATTACK);
        deathKeyframes = textureAtlas.findRegions(RegionNames.RED_DEAD);
    }

    @Override
    protected void createAnimations(TextureAtlas textureAtlas) {
        createKeyframes(textureAtlas);
        super.walkingAnimation = new Animation<TextureRegion>(0.12f,
                walkingKeyframes,
                Animation.PlayMode.LOOP);

        super.walkingAnimationReversed = new Animation<TextureRegion>(0.12f,
                createFlippedTextures(walkingKeyframes),
                Animation.PlayMode.LOOP);

        super.idleAnimation = new Animation<TextureRegion>(0.1f,
                idleKeyframes,
                Animation.PlayMode.LOOP);

        super.idleAnimationReversed = new Animation<TextureRegion>(0.1f,
                createFlippedTextures(idleKeyframes),
                Animation.PlayMode.LOOP);

        super.attackAnimation = new Animation<TextureRegion>(0.1f,
                attackKeyframes,
                Animation.PlayMode.NORMAL);

        super.attackAnimationReversed = new Animation<TextureRegion>(0.1f,
                createFlippedTextures(attackKeyframes),
                Animation.PlayMode.NORMAL);

        damageAnimation = new Animation<TextureRegion>(0.06f,
                damagedKeyframes,
                Animation.PlayMode.LOOP);

        damageAnimationReversed = new Animation<TextureRegion>(0.06f,
                createFlippedTextures(damagedKeyframes),
                Animation.PlayMode.LOOP);

        super.deathAnimation = new Animation<TextureRegion>(0.09f,
                deathKeyframes,
                Animation.PlayMode.NORMAL);

        super.deathAnimationReversed = new Animation<TextureRegion>(0.09f,
                createFlippedTextures(deathKeyframes),
                Animation.PlayMode.NORMAL);
    }

    @Override
    public void renderGamePlay(SpriteBatch batch, Array<Red> reds, float delta) {
        for (int i = 0; i < reds.size; i++) {
            Red red = reds.get(i);

            switch (red.getCurrentState()) {
                case 0:
                    // spawning
                    drawSpawningAnimation(batch, red, delta);
                    break;
                case 1:
                    // idle
                    drawIdleAnimation(batch, red, delta);
                    break;
                case 2:
                    // walking
                    drawWalkingAnimation(batch, red, delta);
                    break;
                case 3:
                    // attacking
                    drawAttackingAnimation(batch, red, delta);
                    break;
                case 4:
                    // dying
                    drawDeathAnimation(batch, red, delta);
                    break;
                case 5:

                    break;
                case 6:
                    // damaged
                    drawDamageAnimation(batch, red, delta);
                    break;
            }
        }
    }

    private void drawSpawningAnimation(SpriteBatch batch, Red red, float delta) {
        red.setHasIdleAnimationStarted(checkIfAnimationHasStarted(red.hasIdleAnimationStarted(), red));
        drawGamePlay(batch, red, red.isClockWise() ? super.idleAnimationReversed : super.idleAnimation, delta, 12);
    }

    private void drawIdleAnimation(SpriteBatch batch, Red red, float delta) {
        red.setHasIdleAnimationStarted(checkIfAnimationHasStarted(red.hasIdleAnimationStarted(), red));
        drawGamePlay(batch,red, red.isClockWise() ? super.idleAnimationReversed : super.idleAnimation, delta, 12);
    }

    private void drawWalkingAnimation(SpriteBatch batch, Red red, float delta) {
        red.setHasWalkAnimationStarted(checkIfAnimationHasStarted(red.hasWalkAnimationStarted(), red));
        drawGamePlay(batch, red, red.isClockWise() ? super.walkingAnimationReversed : super.walkingAnimation, delta, 12);
    }

    private void drawAttackingAnimation(SpriteBatch batch, Red red, float delta) {
        red.setHasAttackAnimationStarted(checkIfAnimationHasStarted(red.hasAttackAnimationStarted(), red));
        drawGamePlay(batch, red, red.isClockWise() ? super.attackAnimationReversed : super.attackAnimation, delta, 12);
    }

    private void drawDeathAnimation(SpriteBatch batch, Red red, float delta) {
        red.setHasDeadAnimationStarted(checkIfAnimationHasStarted(red.hasDeadAnimationStarted(), red));
        drawGamePlay(batch, red, red.isClockWise() ? super.deathAnimationReversed : super.deathAnimation, delta, 12);
    }

    private void drawDamageAnimation(SpriteBatch batch, Red red, float delta) {
        red.setHasDamageAnimationStarted(checkIfAnimationHasStarted(red.isHasDamageAnimationStarted(), red));
        drawGamePlay(batch, red, red.isClockWise() ? damageAnimationReversed : damageAnimation, delta, 12);
    }

}
