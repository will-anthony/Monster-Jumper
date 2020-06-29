package com.jga.jumper.Renderers.Monster;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.jga.jumper.assets.RegionNames;
import com.jga.jumper.config.GameConfig;
import com.jga.jumper.entity.Monster;
import com.jga.jumper.state_machines.MonsterState;

public class MonsterGamePlayRenderer {

    // == attributes ==
    private Animation<TextureRegion> walkingAnimation;
    private Animation<TextureRegion> idleAnimation;
    private Animation<TextureRegion> deathAnimation;
    private Animation<TextureRegion> jumpingAnimation;
    private Animation<TextureRegion> fallingAnimation;
    private Animation<TextureRegion> dashingAnimation;

    // == constructors ==
    public MonsterGamePlayRenderer(TextureAtlas textureAtlas) {
        createAnimations(textureAtlas);
    }

    private void createAnimations(TextureAtlas textureAtlas) {
        walkingAnimation = new Animation<TextureRegion>(0.08f,
                textureAtlas.findRegions(RegionNames.WALK),
                Animation.PlayMode.LOOP_PINGPONG);

        idleAnimation = new Animation<TextureRegion>(0.12f,
                textureAtlas.findRegions(RegionNames.PLAYER_IDLE),
                Animation.PlayMode.LOOP_PINGPONG);

        deathAnimation = new Animation<TextureRegion>(0.1f,
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
                drawGamePlay(batch, monster, idleAnimation, delta, 0);

            } else if (monster.getState() == MonsterState.DASHING) {
                monster.setHasDashAnimationStarted(checkIfAnimationHasStarted(monster.isHasDashAnimationStarted(), monster));
                batch.setColor(1.0f, 1.0f, 1.0f, 0.2f);
                drawGamePlay(batch, monster, dashingAnimation, delta, 0);
                batch.setColor(1.0f, 1.0f, 1.0f, 1.0f);

            } else if (monster.getState() == MonsterState.JUMPING) {
                monster.setHasJumpAnimationStarted(checkIfAnimationHasStarted(monster.isHasJumpAnimationStarted(), monster));
                drawGamePlay(batch, monster, jumpingAnimation, delta, 0);

            } else if (monster.getState() == MonsterState.FALLING) {
                monster.setHasFallAnimationStarted(checkIfAnimationHasStarted(monster.isHasFallAnimationStarted(), monster));
                drawGamePlay(batch, monster, fallingAnimation, delta, 0);

            } else if (monster.getState() == MonsterState.WALKING) {
                monster.setHasWalkAnimationStarted(checkIfAnimationHasStarted(monster.hasWalkAnimationStarted(), monster));
                drawGamePlay(batch, monster, walkingAnimation, delta, 0);

            } else if (monster.getState() == MonsterState.DEAD) {
                monster.setHasDeadAnimationStarted(checkIfAnimationHasStarted(monster.hasDeadAnimationStarted(), monster));
                drawGamePlay(batch, monster, deathAnimation, delta, 0);
            }
        }
    }

    protected void setAllAnimationStatesToFalse(Monster monster) {
        monster.setHasIdleAnimationStarted(false);
        monster.setHasWalkAnimationStarted(false);
        monster.setHasDeadAnimationStarted(false);
        monster.setHasDashAnimationStarted(false);
        monster.setHasJumpAnimationStarted(false);
        monster.setHasFallAnimationStarted(false);
    }


    protected void drawGamePlay(SpriteBatch batch, Monster monster, Animation<TextureRegion> animation, float delta, float rotationOffset) {

        float animationTime = monster.getAnimationTime();
        monster.setAnimationTime(animationTime += delta);
        TextureRegion textureRegion = animation.getKeyFrame(animationTime);
        batch.draw(textureRegion, monster.getX(), monster.getY(),
                0f, 0,
                monster.getWidth(), monster.getHeight(),
                1f, 1f,
                GameConfig.START_ANGLE - monster.getRotation(rotationOffset));
    }

    protected boolean checkIfAnimationHasStarted(boolean hasAnimationStarted, Monster monster) {
        if (!hasAnimationStarted) {
            resetAnimationTime(monster);
            setAllAnimationStatesToFalse(monster);
        }
        return true;
    }

    protected void resetAnimationTime(Monster monster) {
        monster.setAnimationTime(0);
    }
}
