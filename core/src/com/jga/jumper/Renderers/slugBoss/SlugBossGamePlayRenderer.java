package com.jga.jumper.Renderers.slugBoss;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.jga.jumper.Renderers.EntityGamePlayRenderer;
import com.jga.jumper.assets.RegionNames;
import com.jga.jumper.config.GameConfig;
import com.jga.jumper.entity.SlugBoss;

public class SlugBossGamePlayRenderer extends EntityGamePlayRenderer<SlugBoss> {

    protected Animation<TextureRegion> summonAnimation;
    protected Animation<TextureRegion> summonAnimationReversed;
    protected Array<TextureAtlas.AtlasRegion> summonKeyframes;

    protected Animation<TextureRegion> damagedAnimation;
    protected Array<TextureAtlas.AtlasRegion> damagedKeyframes;
    protected Animation<TextureRegion> damagedAnimationReversed;

    protected Animation<TextureRegion> lowAttackAnimation;
    protected Array<TextureAtlas.AtlasRegion> lowAttackKeyframes;
    protected Animation<TextureRegion> lowAttackAnimationReversed;

    public SlugBossGamePlayRenderer(TextureAtlas textureAtlas) {
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
        walkingKeyframes = textureAtlas.findRegions(RegionNames.SLUG_BOSS_WALK);
        idleKeyframes = textureAtlas.findRegions(RegionNames.SLUG_BOSS_IDLE);
        attackKeyframes = textureAtlas.findRegions(RegionNames.SLUG_BOSS_HIGH_ATTACK);
        lowAttackKeyframes = textureAtlas.findRegions(RegionNames.SLUG_BOSS_LOW_ATTACK);
        summonKeyframes = textureAtlas.findRegions(RegionNames.SLUG_BOSS_SUMMON);
        damagedKeyframes = textureAtlas.findRegions(RegionNames.SLUG_BOSS_DAMAGED);
        deathKeyframes = textureAtlas.findRegions(RegionNames.SLUG_BOSS_DEATH);
    }

    @Override
    protected void createAnimations(TextureAtlas textureAtlas) {
        createKeyframes(textureAtlas);
        super.walkingAnimation = new Animation<TextureRegion>(0.15f,
                walkingKeyframes,
                Animation.PlayMode.LOOP);

        super.walkingAnimationReversed = new Animation<TextureRegion>(0.15f,
                createFlippedTextures(walkingKeyframes),
                Animation.PlayMode.LOOP);

        super.idleAnimation = new Animation<TextureRegion>(0.1f,
                idleKeyframes,
                Animation.PlayMode.LOOP);

        super.idleAnimationReversed = new Animation<TextureRegion>(0.1f,
                createFlippedTextures(idleKeyframes),
                Animation.PlayMode.LOOP);

        super.attackAnimation = new Animation<TextureRegion>(0.15f,
                attackKeyframes,
                Animation.PlayMode.LOOP);

        super.attackAnimationReversed = new Animation<TextureRegion>(0.12f,
                createFlippedTextures(attackKeyframes),
                Animation.PlayMode.LOOP);

        this.lowAttackAnimation = new Animation<TextureRegion>(0.12f,
                lowAttackKeyframes,
                Animation.PlayMode.LOOP);

        this.lowAttackAnimationReversed = new Animation<TextureRegion>(0.12f,
                createFlippedTextures(lowAttackKeyframes),
                Animation.PlayMode.LOOP);

        this.summonAnimation = new Animation<TextureRegion>(0.15f,
                summonKeyframes,
                Animation.PlayMode.NORMAL);

        this.summonAnimationReversed = new Animation<TextureRegion>(0.15f,
                createFlippedTextures(summonKeyframes),
                Animation.PlayMode.NORMAL);

        this.damagedAnimation = new Animation<TextureRegion>(0.08f,
                damagedKeyframes,
                Animation.PlayMode.LOOP);

        this.damagedAnimationReversed = new Animation<TextureRegion>(0.08f,
                createFlippedTextures(damagedKeyframes),
                Animation.PlayMode.LOOP);

        super.deathAnimation = new Animation<TextureRegion>(0.06f,
                deathKeyframes,
                Animation.PlayMode.NORMAL);

        super.deathAnimationReversed = new Animation<TextureRegion>(0.06f,
                createFlippedTextures(deathKeyframes),
                Animation.PlayMode.NORMAL);


    }

    @Override
    public void renderGamePlay(SpriteBatch batch, Array<SlugBoss> slugBosses, float delta) {
        for (int i = 0; i < slugBosses.size; i++) {
            SlugBoss slugBoss = slugBosses.get(i);

            switch (slugBoss.getCurrentSlugBossState()) {

                case GameConfig.SLUG_BOSS_SPAWNING_STATE:
                    drawSpawningAnimation(batch, slugBoss, delta);
                    break;

                case GameConfig.SLUG_BOSS_IDLE_STATE:
                    drawIdleAnimation(batch, slugBoss, delta);
                    break;

                case GameConfig.SLUG_BOSS_WALKING_STATE:
                    drawWalkingAnimation(batch, slugBoss, delta);
                    break;

                case GameConfig.SLUG_BOSS_ATTACKING_HIGH_STATE:
                    drawAttackingAnimation(batch, slugBoss, delta);
                    break;

                case GameConfig.SLUG_BOSS_ATTACKING_LOW_STATE:
                    drawLowAttackingAnimation(batch, slugBoss, delta);
                    break;

                case GameConfig.SLUG_BOSS_SUMMON_STATE:
                    drawSummonAnimation(batch, slugBoss, delta);
                    if (summonAnimation.isAnimationFinished(slugBoss.getAnimationTime())) {
                        slugBoss.setSummoningChildSlug(true);
                    }
                    break;

                case GameConfig.SLUG_BOSS_DAMAGED_STATE:
                    drawDamagedAnimation(batch, slugBoss, delta);
                    break;

                case GameConfig.SLUG_BOSS_DYING_STATE:
                    drawDeathAnimation(batch, slugBoss, delta);
                    break;

                case GameConfig.SLUG_BOSS_DEAD_STATE:
                    break;

            }
        }
    }

    private void drawSpawningAnimation(SpriteBatch batch, SlugBoss slugBoss, float delta) {
        slugBoss.setHasIdleAnimationStarted(checkIfAnimationHasStarted(slugBoss.hasIdleAnimationStarted(), slugBoss));
        super.drawGamePlay(batch, slugBoss, slugBoss.isClockWise() ?
                super.walkingAnimationReversed : super.walkingAnimation, delta, GameConfig.SLUG_BOSS_ANIMATION_ROTATION_OFFSET);
    }

    private void drawIdleAnimation(SpriteBatch batch, SlugBoss slugBoss, float delta) {
        slugBoss.setHasIdleAnimationStarted(checkIfAnimationHasStarted(slugBoss.hasIdleAnimationStarted(), slugBoss));
        super.drawGamePlay(batch, slugBoss, slugBoss.isClockWise() ? super.walkingAnimationReversed : super.walkingAnimation, delta, GameConfig.SLUG_BOSS_ANIMATION_ROTATION_OFFSET);
    }

    private void drawWalkingAnimation(SpriteBatch batch, SlugBoss slugBoss, float delta) {
        slugBoss.setHasWalkAnimationStarted(checkIfAnimationHasStarted(slugBoss.hasWalkAnimationStarted(), slugBoss));
        super.drawGamePlay(batch, slugBoss, slugBoss.isClockWise() ? super.walkingAnimationReversed : super.walkingAnimation, delta, GameConfig.SLUG_BOSS_ANIMATION_ROTATION_OFFSET);
    }

    private void drawAttackingAnimation(SpriteBatch batch, SlugBoss slugBoss, float delta) {
        slugBoss.setHasAttackAnimationStarted(checkIfAnimationHasStarted(slugBoss.hasAttackAnimationStarted(), slugBoss));
        super.drawGamePlay(batch, slugBoss, slugBoss.isClockWise() ? super.attackAnimationReversed : super.attackAnimation, delta, GameConfig.SLUG_BOSS_ANIMATION_ROTATION_OFFSET);
    }

    private void drawLowAttackingAnimation(SpriteBatch batch, SlugBoss slugBoss, float delta) {
        slugBoss.setHasLowAttackAnimationStarted(checkIfAnimationHasStarted(slugBoss.hasLowAttackAnimationStarted(), slugBoss));
        super.drawGamePlay(batch, slugBoss, slugBoss.isClockWise() ? this.lowAttackAnimationReversed : this.lowAttackAnimation, delta, GameConfig.SLUG_BOSS_ANIMATION_ROTATION_OFFSET);
    }

    private void drawDeathAnimation(SpriteBatch batch, SlugBoss slugBoss, float delta) {
        slugBoss.setHasDeadAnimationStarted(checkIfAnimationHasStarted(slugBoss.hasDeadAnimationStarted(), slugBoss));
        super.drawGamePlay(batch, slugBoss, slugBoss.isClockWise() ? super.deathAnimationReversed : super.deathAnimation, delta, GameConfig.SLUG_BOSS_ANIMATION_ROTATION_OFFSET);
    }

    private void drawSummonAnimation(SpriteBatch batch, SlugBoss slugBoss, float delta) {
        slugBoss.setHasSummonAnimationStarted(checkIfAnimationHasStarted(slugBoss.hasSummonAnimationStarted(), slugBoss));
        super.drawGamePlay(batch, slugBoss, slugBoss.isClockWise() ? this.summonAnimationReversed : this.summonAnimation, delta, GameConfig.SLUG_BOSS_ANIMATION_ROTATION_OFFSET);
    }

    private void drawDamagedAnimation(SpriteBatch batch, SlugBoss slugBoss, float delta) {
        slugBoss.setHasDamagedAnimationStarted(checkIfAnimationHasStarted(slugBoss.hasDamagedAnimationStarted(), slugBoss));
        super.drawGamePlay(batch, slugBoss, slugBoss.isClockWise() ? this.damagedAnimationReversed : this.damagedAnimation, delta,  GameConfig.SLUG_BOSS_ANIMATION_ROTATION_OFFSET);
    }

    @Override
    protected void setAllAnimationStatesToFalse(SlugBoss entity) {

        super.setAllAnimationStatesToFalse(entity);

        entity.setHasSummonAnimationStarted(false);
        entity.setHasLowAttackAnimationStarted(false);
        entity.setHasDamagedAnimationStarted(false);

    }
}
