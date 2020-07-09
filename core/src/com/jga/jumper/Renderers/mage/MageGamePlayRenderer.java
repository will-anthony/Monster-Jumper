package com.jga.jumper.Renderers.mage;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.jga.jumper.Renderers.EntityGamePlayRenderer;
import com.jga.jumper.assets.RegionNames;
import com.jga.jumper.config.GameConfig;
import com.jga.jumper.entity.Bear;
import com.jga.jumper.entity.Mage;

public class MageGamePlayRenderer extends EntityGamePlayRenderer<Mage> {

    private Animation<TextureRegion> damageAnimation;
    private Animation<TextureRegion> damageAnimationReversed;
    private Array<TextureAtlas.AtlasRegion> damagedKeyframes;

    public MageGamePlayRenderer(TextureAtlas textureAtlas) {
        super(textureAtlas);
    }

    @Override
    protected void drawGamePlay(SpriteBatch batch, Mage entity, Animation<TextureRegion> animation, float delta, float rotationOffset) {
        if(!entity.isClockWise()) {
            super.drawGamePlay(batch, entity, animation, delta, rotationOffset);
        } else {
            drawClockwiseGamePlay(batch, entity, animation, delta, rotationOffset);
        }
    }

    private void drawClockwiseGamePlay(SpriteBatch batch, Mage entity, Animation<TextureRegion> animation, float delta, float rotationOffset) {
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
        walkingKeyframes = textureAtlas.findRegions(RegionNames.MAGE_WALK);
        idleKeyframes = textureAtlas.findRegions(RegionNames.MAGE_IDLE);
        damagedKeyframes = textureAtlas.findRegions(RegionNames.MAGE_DAMAGE);
        attackKeyframes = textureAtlas.findRegions(RegionNames.MAGE_ATTACK);
        deathKeyframes = textureAtlas.findRegions(RegionNames.MAGE_DEATH);
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
    public void renderGamePlay(SpriteBatch batch, Array<Mage> mages, float delta) {
        for (int i = 0; i < mages.size; i++) {
            Mage mage = mages.get(i);

            switch (mage.getCurrentMageState()) {
                case 0:
                    // spawning
                    drawSpawningAnimation(batch, mage, delta);
                    break;
                case 1:
                    // idle
                    drawIdleAnimation(batch, mage, delta);
                    break;
                case 2:
                    // walking
                    drawWalkingAnimation(batch, mage, delta);
                    break;
                case 3:
                    // attacking
                    drawAttackingAnimation(batch, mage, delta);
                    break;
                case 4:
                    // dying
                    drawDeathAnimation(batch, mage, delta);
                    break;
                case 5:

                    break;
                case 6:
                    // damaged
                    drawDamageAnimation(batch, mage, delta);
                    break;
            }
        }
    }

    private void drawSpawningAnimation(SpriteBatch batch, Mage mage, float delta) {
        mage.setHasIdleAnimationStarted(checkIfAnimationHasStarted(mage.hasIdleAnimationStarted(), mage));
        drawGamePlay(batch, mage, mage.isClockWise() ? super.idleAnimationReversed : super.idleAnimation, delta, 12);
    }

    private void drawIdleAnimation(SpriteBatch batch, Mage mage, float delta) {
        mage.setHasIdleAnimationStarted(checkIfAnimationHasStarted(mage.hasIdleAnimationStarted(), mage));
        drawGamePlay(batch,mage, mage.isClockWise() ? super.idleAnimationReversed : super.idleAnimation, delta, 12);
    }

    private void drawWalkingAnimation(SpriteBatch batch, Mage mage, float delta) {
        mage.setHasWalkAnimationStarted(checkIfAnimationHasStarted(mage.hasWalkAnimationStarted(), mage));
        drawGamePlay(batch, mage, mage.isClockWise() ? super.walkingAnimationReversed : super.walkingAnimation, delta, 12);
    }

    private void drawAttackingAnimation(SpriteBatch batch, Mage mage, float delta) {
        mage.setHasAttackAnimationStarted(checkIfAnimationHasStarted(mage.hasAttackAnimationStarted(), mage));
        drawGamePlay(batch, mage, mage.isClockWise() ? super.attackAnimationReversed : super.attackAnimation, delta, 12);
    }

    private void drawDeathAnimation(SpriteBatch batch, Mage mage, float delta) {
        mage.setHasDeadAnimationStarted(checkIfAnimationHasStarted(mage.hasDeadAnimationStarted(), mage));
        drawGamePlay(batch, mage, mage.isClockWise() ? super.deathAnimationReversed : super.deathAnimation, delta, 12);
    }

    private void drawDamageAnimation(SpriteBatch batch, Mage mage, float delta) {
        mage.setHasDamageAnimationStarted(checkIfAnimationHasStarted(mage.isHasDamageAnimationStarted(), mage));
        drawGamePlay(batch, mage, mage.isClockWise() ? damageAnimationReversed : damageAnimation, delta, 12);
    }

}
