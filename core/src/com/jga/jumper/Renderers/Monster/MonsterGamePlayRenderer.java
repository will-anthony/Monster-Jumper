package com.jga.jumper.Renderers.Monster;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.jga.jumper.assets.RegionNames;
import com.jga.jumper.config.GameConfig;
import com.jga.jumper.entity.Monster;
import com.jga.jumper.entity.MonsterState;

public class MonsterGamePlayRenderer {

    // == attributes ==
    private Animation<TextureRegion> monsterWalkingAnimation;
    private Animation<TextureRegion> monsterIdleAnimation;
    private Animation<TextureRegion> monsterJumpingAnimation;
    private Animation<TextureRegion> monsterFallingAnimation;
    private Animation<TextureRegion> monsterDashingAnimation;
    private Animation<TextureRegion> monsterDeathAnimation;

    private float deathAnimationTime = 0;
    private float fallAnimationTime = 0;
    private float jumpAnimationTime = 0;
    private float dashAnimationTime = 0;

    // == constructors ==
    public MonsterGamePlayRenderer(TextureAtlas gamePlayAtlas) {
        monsterWalkingAnimation = new Animation<TextureRegion>(0.08f,
                gamePlayAtlas.findRegions(RegionNames.WALK),
                Animation.PlayMode.LOOP_PINGPONG);

        monsterIdleAnimation = new Animation<TextureRegion>(0.12f,
                gamePlayAtlas.findRegions(RegionNames.PLAYER_IDLE),
                Animation.PlayMode.LOOP_PINGPONG);

        monsterJumpingAnimation = new Animation<TextureRegion>(1.3f,
                gamePlayAtlas.findRegions(RegionNames.PLAYER_JUMP),
                Animation.PlayMode.NORMAL);

        monsterFallingAnimation = new Animation<TextureRegion>(0.5f,
                gamePlayAtlas.findRegions(RegionNames.PLAYER_FALL),
                Animation.PlayMode.NORMAL);

        monsterDashingAnimation = new Animation<TextureRegion>(1f,
                gamePlayAtlas.findRegions(RegionNames.PLAYER_DASH),
                Animation.PlayMode.NORMAL);

        monsterDeathAnimation = new Animation<TextureRegion>(0.1f,
                gamePlayAtlas.findRegions(RegionNames.PLAYER_DEATH),
                Animation.PlayMode.NORMAL);
    }

    // check animation time. If animation time starts on go, tie static monster into that
    // == public methods ==
    public void renderMonsterGamePlay(SpriteBatch batch, float animationTime, Array<Monster> monsters, float delta) {

        Monster monster = monsters.get(0);
        if (monster.getState() == MonsterState.IDLE) {
            drawIdleMonster(batch, monsters, animationTime);

        } else if (monster.getState() == MonsterState.DASHING) {
            batch.setColor(1.0f, 1.0f, 1.0f, 0.2f);
            drawDashingMonster(batch, monsters, dashAnimationTime, delta);
            batch.setColor(1.0f, 1.0f, 1.0f, 1.0f);

        } else if (monster.getState() == MonsterState.JUMPING) {
            drawJumpingMonster(batch, monsters, jumpAnimationTime, delta );

        } else if (monster.getState() == MonsterState.FALLING) {
            drawFallingMonster(batch, monsters, fallAnimationTime, delta);

        } else if (monster.getState() == MonsterState.WALKING) {
            drawWalkingMonster(batch, monsters, animationTime);

        } else if (monster.getState() == MonsterState.DEAD) {

            drawDeadMonster(batch, monsters, deathAnimationTime, delta);
        }
        resetAnimationTimers(monster);
    }

    private void drawIdleMonster(SpriteBatch batch, Array<Monster> monsters, float animationTime) {

        TextureRegion monsterRegion = monsterIdleAnimation.getKeyFrame(animationTime);
        for (Monster monster : monsters) {
            batch.draw(monsterRegion, monster.getX(), monster.getY(),
                    0, 0,
                    monster.getWidth(), monster.getHeight(),
                    1.5f, 1.5f,
                    GameConfig.START_ANGLE - monster.getAngleDegrees());
        }
    }

    private void drawWalkingMonster(SpriteBatch batch, Array<Monster> monsters, float animationTime) {
        TextureRegion monsterRegion = monsterWalkingAnimation.getKeyFrame(animationTime);
        for (Monster monster : monsters) {
            batch.draw(monsterRegion,
                    monster.getX(), monster.getY(),
                    0, 0,
                    monster.getWidth(), monster.getHeight(),
                    1.5f, 1.5f,
                    GameConfig.START_ANGLE - monster.getAngleDegrees());
        }
    }

    private void drawJumpingMonster(SpriteBatch batch, Array<Monster> monsters, float jumpAnimationTime, float delta) {
        this.jumpAnimationTime += delta;
        TextureRegion monsterRegion = monsterJumpingAnimation.getKeyFrame(jumpAnimationTime);
        for (Monster monster : monsters) {
            batch.draw(monsterRegion, monster.getX(), monster.getY(),
                    0, 0,
                    monster.getWidth(), monster.getHeight(),
                    1.5f, 1.5f,
                    GameConfig.START_ANGLE - monster.getAngleDegrees());
        }
    }

    private void drawFallingMonster(SpriteBatch batch, Array<Monster> monsters, float fallAnimationTime, float delta) {
        this.fallAnimationTime += delta;
        TextureRegion monsterRegion = monsterFallingAnimation.getKeyFrame(fallAnimationTime);
        for (Monster monster : monsters) {
            batch.draw(monsterRegion, monster.getX(), monster.getY(),
                    0, 0,
                    monster.getWidth(), monster.getHeight(),
                    1.5f, 1.5f,
                    GameConfig.START_ANGLE - monster.getAngleDegrees());
        }
    }

    private void drawDashingMonster(SpriteBatch batch, Array<Monster> monsters, float dashAnimationTime, float delta) {
        this.dashAnimationTime += delta;
        TextureRegion monsterRegion = monsterDashingAnimation.getKeyFrame(dashAnimationTime);
        for (Monster monster : monsters) {
            batch.draw(monsterRegion, monster.getX(), monster.getY(),
                    0, 0,
                    monster.getWidth(), monster.getHeight(),
                    1.5f, 1.5f,
                    GameConfig.START_ANGLE - monster.getAngleDegrees());
        }
    }

    private void drawDeadMonster(SpriteBatch batch, Array<Monster> monsters, float fallAnimationTime, float delta) {
        this.deathAnimationTime += delta;
        TextureRegion monsterRegion = monsterDeathAnimation.getKeyFrame(fallAnimationTime);
        for (Monster monster : monsters) {
            batch.draw(monsterRegion, monster.getX(), monster.getY(),
                    0, 0,
                    monster.getWidth(), monster.getHeight(),
                    1.5f, 1.5f,
                    GameConfig.START_ANGLE - monster.getAngleDegrees());
        }
    }

    private void resetAnimationTimers(Monster monster) {

        if(monster.getState() != MonsterState.DEAD) {
            deathAnimationTime = 0;
        }
        if(monster.getState() != MonsterState.JUMPING) {
            jumpAnimationTime = 0;
        }
        if(monster.getState() != MonsterState.FALLING) {
            fallAnimationTime = 0;
        }
        if(monster.getState() != MonsterState.DASHING) {
            dashAnimationTime = 0;
        }
    }
}

