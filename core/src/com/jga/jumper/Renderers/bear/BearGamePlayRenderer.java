package com.jga.jumper.Renderers.bear;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.jga.jumper.Renderers.EntityGamePlayRenderer;
import com.jga.jumper.assets.RegionNames;
import com.jga.jumper.entity.Bear;

public class BearGamePlayRenderer extends EntityGamePlayRenderer<Bear> {

    private Animation<TextureRegion> damageAnimation;
    private Animation<TextureRegion> damageAnimationReversed;
    private Array<TextureAtlas.AtlasRegion> damagedKeyframes;

    private Animation<TextureRegion> summonBeginAnimation;
    private Animation<TextureRegion> summonBeginAnimationReversed;
    private Array<TextureAtlas.AtlasRegion> summonBeginKeyframes;

    public BearGamePlayRenderer(TextureAtlas textureAtlas) {
        super(textureAtlas);
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
        walkingKeyframes = textureAtlas.findRegions(RegionNames.BEAR_WALK);
        idleKeyframes = textureAtlas.findRegions(RegionNames.BEAR_IDLE);
        damagedKeyframes = textureAtlas.findRegions(RegionNames.BEAR_DAMAGE);
        summonBeginKeyframes = textureAtlas.findRegions(RegionNames.BEAR_SUMMON_BEGIN);
//        attackKeyframes = textureAtlas.findRegions(RegionNames.BEAR_ATTACK);
        deathKeyframes = textureAtlas.findRegions(RegionNames.BEAR_DEATH);
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

        super.attackAnimation = new Animation<TextureRegion>(0.06f,
                walkingKeyframes,
                Animation.PlayMode.LOOP);

        super.attackAnimationReversed = new Animation<TextureRegion>(0.06f,
                createFlippedTextures(walkingKeyframes),
                Animation.PlayMode.LOOP);

        damageAnimation = new Animation<TextureRegion>(0.06f,
                damagedKeyframes,
                Animation.PlayMode.LOOP);

        damageAnimationReversed = new Animation<TextureRegion>(0.06f,
                createFlippedTextures(damagedKeyframes),
                Animation.PlayMode.LOOP);

        summonBeginAnimation = new Animation<TextureRegion>(0.2f,
                summonBeginKeyframes,
                Animation.PlayMode.NORMAL);

        summonBeginAnimationReversed = new Animation<TextureRegion>(0.1f,
                createFlippedTextures(summonBeginKeyframes),
                Animation.PlayMode.NORMAL);

        super.deathAnimation = new Animation<TextureRegion>(0.11f,
                deathKeyframes,
                Animation.PlayMode.NORMAL);

        super.deathAnimationReversed = new Animation<TextureRegion>(0.11f,
                createFlippedTextures(deathKeyframes),
                Animation.PlayMode.NORMAL);
    }

    @Override
    public void renderGamePlay(SpriteBatch batch, Array<Bear> bears, float delta) {
        for (int i = 0; i < bears.size; i++) {
            Bear bear = bears.get(i);

            switch (bear.getCurrentBearState()) {
                case 0:
                    // spawning
                    drawSpawningAnimation(batch, bear, delta);
                    break;
                case 1:
                    // idle
                    drawIdleAnimation(batch, bear, delta);
                    break;
                case 2:
                    // walking
                    drawWalkingAnimation(batch, bear, delta);
                    break;
                case 3:
                    // attacking
                    drawAttackingAnimation(batch, bear, delta);
                    break;
                case 4:
                    // dying
                    drawDeathAnimation(batch, bear, delta);
                    break;
                case 5:

                    break;
                case 6:
                    // damaged
                    drawDamageAnimation(batch, bear, delta);
                    break;
                case 7:
                    // stomp attack
                    drawSummonBeginAnimation(batch, bear, delta);
                    break;
            }
        }
    }

    private void drawSpawningAnimation(SpriteBatch batch, Bear bear, float delta) {
        bear.setHasIdleAnimationStarted(checkIfAnimationHasStarted(bear.hasIdleAnimationStarted(), bear));
        super.drawGamePlay(batch, bear, bear.isClockWise() ? super.idleAnimationReversed : super.idleAnimation, delta, 20);
    }

    private void drawIdleAnimation(SpriteBatch batch, Bear bear, float delta) {
        bear.setHasIdleAnimationStarted(checkIfAnimationHasStarted(bear.hasIdleAnimationStarted(), bear));
        super.drawGamePlay(batch, bear, bear.isClockWise() ? super.idleAnimationReversed : super.idleAnimation, delta, 20);
    }

    private void drawWalkingAnimation(SpriteBatch batch, Bear bear, float delta) {
        bear.setHasWalkAnimationStarted(checkIfAnimationHasStarted(bear.hasWalkAnimationStarted(), bear));
        super.drawGamePlay(batch, bear, bear.isClockWise() ? super.walkingAnimationReversed : super.walkingAnimation, delta, 20);
    }

    private void drawAttackingAnimation(SpriteBatch batch, Bear bear, float delta) {
        bear.setHasAttackAnimationStarted(checkIfAnimationHasStarted(bear.hasAttackAnimationStarted(), bear));
        super.drawGamePlay(batch, bear, bear.isClockWise() ? super.walkingAnimationReversed : super.walkingAnimation, delta, 20);
    }

    private void drawSummonBeginAnimation(SpriteBatch batch, Bear bear, float delta) {
        bear.setHasSummonBeginAnimationStarted(checkIfAnimationHasStarted(bear.hasSummonBeginAnimationStarted(), bear));
        super.drawGamePlay(batch, bear, bear.isClockWise() ? summonBeginAnimationReversed : summonBeginAnimation, delta, 20);
    }

    private void drawDeathAnimation(SpriteBatch batch, Bear bear, float delta) {
        bear.setHasDeadAnimationStarted(checkIfAnimationHasStarted(bear.hasDeadAnimationStarted(), bear));
        super.drawGamePlay(batch, bear, bear.isClockWise() ? super.deathAnimationReversed : super.deathAnimation, delta, 20);
    }

    private void drawDamageAnimation(SpriteBatch batch, Bear bear, float delta) {
        bear.setHasDamageAnimationStarted(checkIfAnimationHasStarted(bear.hasDamageAnimationStarted(), bear));
        super.drawGamePlay(batch, bear, bear.isClockWise() ? damageAnimationReversed : damageAnimation, delta, 20);
    }

    @Override
    protected void setAllAnimationStatesToFalse(Bear entity) {
        super.setAllAnimationStatesToFalse(entity);
        entity.setHasSummonBeginAnimationStarted(false);
        entity.setHasDamageAnimationStarted(false);
    }
}
