package com.jga.jumper.Renderers;

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

public abstract class EnemyGamePlayRenderer {
//    private Animation<TextureRegion> idleAnimation;
//    private Animation<TextureRegion> walkingAnimation;
//    private Animation<TextureRegion> attackAnimation;
//    private Animation<TextureRegion> deathAnimation;
//
//    // == constructors ==
//    public EnemyGamePlayRenderer()
//    {
//        init();
//    }
//    // == public methods ==
//    private void init() {
//        setIdleAnimation();
//        setWalkingAnimation();
//        setAttackAnimation();
//        setDeathAnimation();
//    }
//    protected abstract void setIdleAnimation();
//    protected abstract void setWalkingAnimation();
//    protected abstract void setAttackAnimation();
//    protected abstract void setDeathAnimation();
//
//    protected abstract void drawIdleAnimation();
//    protected abstract void drawWalkingAnimation();
//    protected abstract void drawAttackAnimation();
//    protected abstract void drawDeathAnimation();
//
//    public void renderEnemyGamePlay(SpriteBatch batch, float animationTime, Array<Monster> monsters) {
//
//        TextureRegion monsterRegion = monsterWalkingAnimation.getKeyFrame(animationTime);
//
//        Monster monster = monsters.get(0);
//        if (!monster.isMoving()) {
//            drawStaticMonster(batch, monsters, animationTime);
//
//        } else if (monster.getState() == MonsterState.DASHING) {
//            Color oldColor = batch.getColor();
//            batch.setColor(1.0f, 1.0f, 1.0f, 0.2f);
//            drawDashingMonster(batch, monsters, animationTime);
//            batch.setColor(1.0f, 1.0f, 1.0f, 1.0f);
//
//        } else if (monster.getState() == MonsterState.JUMPING) {
//            drawJumpingMonster(batch, monsters, animationTime);
//
//        }else if (monster.getState() == MonsterState.FALLING) {
//            drawFallingMonster(batch, monsters, animationTime);
//
//        } else {
//            batch.draw(monsterRegion,
//                    monster.getX(), monster.getY(),
//                    0, 0,
//                    monster.getWidth(), monster.getHeight(),
//                    1.5f, 1.5f,
//                    GameConfig.START_ANGLE - monster.getAngleDegrees()
//            );
//        }
//    }
//
//    private void drawStaticMonster(SpriteBatch batch, Array<Monster> monsters, float animationTime) {
//        TextureRegion monsterRegion = monsterIdleAnimation.getKeyFrame(animationTime);
//        for (Monster monster : monsters) {
//            batch.draw(monsterRegion, monster.getX(), monster.getY(),
//                    0, 0,
//                    monster.getWidth(), monster.getHeight(),
//                    1.5f, 1.5f,
//                    GameConfig.START_ANGLE - monster.getAngleDegrees());
//        }
//    }
//
//    private void drawJumpingMonster(SpriteBatch batch, Array<Monster> monsters, float animationTime) {
//        TextureRegion monsterRegion = monsterJumpingAnimation.getKeyFrame(animationTime);
//        for (Monster monster : monsters) {
//            batch.draw(monsterRegion, monster.getX(), monster.getY(),
//                    0, 0,
//                    monster.getWidth(), monster.getHeight(),
//                    1.5f, 1.5f,
//                    GameConfig.START_ANGLE - monster.getAngleDegrees());
//        }
//    }
//
//    private void drawFallingMonster(SpriteBatch batch, Array<Monster> monsters, float animationTime) {
//        TextureRegion monsterRegion = monsterFallingAnimation.getKeyFrame(animationTime);
//        for (Monster monster : monsters) {
//            batch.draw(monsterRegion, monster.getX(), monster.getY(),
//                    0, 0,
//                    monster.getWidth(), monster.getHeight(),
//                    1.5f, 1.5f,
//                    GameConfig.START_ANGLE - monster.getAngleDegrees());
//        }
//    }
//
//    private void drawDashingMonster(SpriteBatch batch, Array<Monster> monsters, float animationTime) {
//        TextureRegion monsterRegion = monsterDashingAnimation.getKeyFrame(animationTime);
//        for (Monster monster : monsters) {
//            batch.draw(monsterRegion, monster.getX(), monster.getY(),
//                    0, 0,
//                    monster.getWidth(), monster.getHeight(),
//                    1.5f, 1.5f,
//                    GameConfig.START_ANGLE - monster.getAngleDegrees());
//        }
//    }
}
