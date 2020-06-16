package com.jga.jumper.Renderers.Monster;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.jga.jumper.Renderers.EntityGamePlayRenderer;
import com.jga.jumper.assets.RegionNames;
import com.jga.jumper.config.GameConfig;
import com.jga.jumper.entity.Monster;
import com.jga.jumper.state_machines.MonsterState;

public class MonsterGamePlayRenderer extends EntityGamePlayRenderer<Monster> {

    // == attributes ==
    private Animation<TextureRegion> jumpingAnimation;
    private Animation<TextureRegion> fallingAnimation;
    private Animation<TextureRegion> dashingAnimation;

    private boolean hasDashAnimationStarted;
    private boolean hasJumpAnimationStarted;
    private boolean hasFallAnimationStarted;

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
                hasIdleAnimationStarted = checkIfAnimationHasStarted(hasIdleAnimationStarted);
                super.drawGamePlay(batch, monsters, super.idleAnimation, delta);

            } else if (monster.getState() == MonsterState.DASHING) {
                hasDashAnimationStarted = checkIfAnimationHasStarted(hasDashAnimationStarted);
                batch.setColor(1.0f, 1.0f, 1.0f, 0.2f);
                super.drawGamePlay(batch, monsters, dashingAnimation, delta);
                batch.setColor(1.0f, 1.0f, 1.0f, 1.0f);

            } else if (monster.getState() == MonsterState.JUMPING) {
                hasJumpAnimationStarted = checkIfAnimationHasStarted(hasJumpAnimationStarted);
                super.drawGamePlay(batch, monsters, jumpingAnimation, delta);

            } else if (monster.getState() == MonsterState.FALLING) {
                hasFallAnimationStarted = checkIfAnimationHasStarted(hasFallAnimationStarted);
                super.drawGamePlay(batch, monsters, fallingAnimation, delta);

            } else if (monster.getState() == MonsterState.WALKING) {
                hasWalkAnimationStarted = checkIfAnimationHasStarted(hasWalkAnimationStarted);
                super.drawGamePlay(batch, monsters, super.walkingAnimation, delta);

            } else if (monster.getState() == MonsterState.DEAD) {
                hasDeadAnimationStarted = checkIfAnimationHasStarted(hasDeadAnimationStarted);
                super.drawGamePlay(batch, monsters, super.deathAnimation, delta);
            }
        }
    }

    @Override
    protected void setAllAnimationStatesToFalse() {
        super.setAllAnimationStatesToFalse();
        hasDashAnimationStarted = false;
        hasJumpAnimationStarted = false;
        hasFallAnimationStarted = false;

    }
}

