package com.jga.jumper.Renderers.Monster;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.jga.jumper.Renderers.EntityGamePlayRenderer;
import com.jga.jumper.assets.RegionNames;
import com.jga.jumper.entity.Monster;
import com.jga.jumper.state_machines.MonsterState;

public class MonsterGamePlayRenderer extends EntityGamePlayRenderer<Monster> {

    // == attributes ==
    private Animation<TextureRegion> jumpingAnimation;
    private Animation<TextureRegion> fallingAnimation;
    private Animation<TextureRegion> dashingAnimation;

    // == constructors ==
    public MonsterGamePlayRenderer(TextureAtlas textureAtlas) {
        super(textureAtlas);
    }

    @Override
    protected void createAnimations(TextureAtlas textureAtlas) {
        super.walkingAnimation = new Animation<TextureRegion>(0.08f,
                textureAtlas.findRegions(RegionNames.WALK),
                Animation.PlayMode.LOOP_PINGPONG);

        super.idleAnimation = new Animation<TextureRegion>(0.12f,
                textureAtlas.findRegions(RegionNames.PLAYER_IDLE),
                Animation.PlayMode.LOOP_PINGPONG);

        super.deathAnimation = new Animation<TextureRegion>(0.1f,
                textureAtlas.findRegions(RegionNames.PLAYER_DEATH),
                Animation.PlayMode.NORMAL);

        jumpingAnimation = new Animation<TextureRegion>(1.3f,
                textureAtlas.findRegions(RegionNames.PLAYER_JUMP),
                Animation.PlayMode.NORMAL);

        fallingAnimation = new Animation<TextureRegion>(0.5f,
                textureAtlas.findRegions(RegionNames.PLAYER_FALL),
                Animation.PlayMode.NORMAL);

        dashingAnimation = new Animation<TextureRegion>(1f,
                textureAtlas.findRegions(RegionNames.PLAYER_DASH),
                Animation.PlayMode.NORMAL);
    }

    // == public methods ==
    public void renderGamePlay(SpriteBatch batch, Array<Monster> monsters, float delta) {

        for (int i = 0; i < monsters.size; i++) {
            Monster monster = monsters.get(i);

            if (monster.getState() == MonsterState.IDLE) {
                monster.setHasIdleAnimationStarted(checkIfAnimationHasStarted(monster.hasIdleAnimationStarted(), monster));
                super.drawGamePlay(batch, monster, super.idleAnimation, delta, 0);

            } else if (monster.getState() == MonsterState.DASHING) {
                monster.setHasDashAnimationStarted(checkIfAnimationHasStarted(monster.isHasDashAnimationStarted(), monster));
                batch.setColor(1.0f, 1.0f, 1.0f, 0.2f);
                super.drawGamePlay(batch, monster, dashingAnimation, delta, 0);
                batch.setColor(1.0f, 1.0f, 1.0f, 1.0f);

            } else if (monster.getState() == MonsterState.JUMPING) {
                monster.setHasJumpAnimationStarted(checkIfAnimationHasStarted(monster.isHasJumpAnimationStarted(), monster));
                super.drawGamePlay(batch, monster, jumpingAnimation, delta, 0);

            } else if (monster.getState() == MonsterState.FALLING) {
                monster.setHasFallAnimationStarted(checkIfAnimationHasStarted(monster.isHasFallAnimationStarted(), monster));
                super.drawGamePlay(batch, monster, fallingAnimation, delta, 0);

            } else if (monster.getState() == MonsterState.WALKING) {
                monster.setHasWalkAnimationStarted(checkIfAnimationHasStarted(monster.hasWalkAnimationStarted(), monster));
                super.drawGamePlay(batch, monster, super.walkingAnimation, delta, 0);

            } else if (monster.getState() == MonsterState.DEAD) {
                monster.setHasDeadAnimationStarted(checkIfAnimationHasStarted(monster.hasDeadAnimationStarted(), monster));
                super.drawGamePlay(batch, monster, super.deathAnimation, delta, 0);
            }
        }
    }

    @Override
    protected void setAllAnimationStatesToFalse(Monster monster) {
        super.setAllAnimationStatesToFalse(monster);
        monster.setHasDashAnimationStarted(false);
        monster.setHasJumpAnimationStarted(false);
        monster.setHasFallAnimationStarted(false);
    }
}

