package com.jga.jumper.Renderers.fireball;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.jga.jumper.Renderers.ProjectileGamePlayRenderer;
import com.jga.jumper.assets.RegionNames;
import com.jga.jumper.entity.projectiles.FireBall;

public class FireBallGamePlayRenderer extends ProjectileGamePlayRenderer<FireBall> {

    public FireBallGamePlayRenderer(TextureAtlas textureAtlas) {
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
        super.spawnKeyframes = textureAtlas.findRegions(RegionNames.FIRE_BALL_SPAWN);
        super.moveKeyframes = textureAtlas.findRegions(RegionNames.FIRE_BALL_MOVE);
        super.deathKeyframes = textureAtlas.findRegions(RegionNames.FIRE_BALL_DEATH);
    }

    @Override
    protected void createAnimations(TextureAtlas textureAtlas) {
        createKeyframes(textureAtlas);
        super.spawnAnimation = new Animation<TextureRegion>(0.3f,
                spawnKeyframes,
                Animation.PlayMode.NORMAL);

        super.spawnAnimationReversed = new Animation<TextureRegion>(0.3f,
                createFlippedTextures(spawnKeyframes),
                Animation.PlayMode.NORMAL);

        super.moveAnimation = new Animation<TextureRegion>(0.1f,
                moveKeyframes,
                Animation.PlayMode.LOOP);

        super.moveAnimationReversed = new Animation<TextureRegion>(0.1f,
                createFlippedTextures(moveKeyframes),
                Animation.PlayMode.LOOP);

        super.deathAnimation = new Animation<TextureRegion>(0.05f,
                deathKeyframes,
                Animation.PlayMode.NORMAL);

        super.deathAnimationReversed = new Animation<TextureRegion>(0.15f,
                createFlippedTextures(deathKeyframes),
                Animation.PlayMode.NORMAL);
    }

    @Override
    public void renderGamePlay(SpriteBatch batch, Array<FireBall> fireBalls, float delta) {
        for (int i = 0; i < fireBalls.size; i++) {
            FireBall fireBall = fireBalls.get(i);

            switch (fireBall.getCurrentFireBallState()) {
                case 0:
                    // spawning
                    drawSpawningAnimation(batch, fireBall, delta);
                    break;
                case 1:
                    // moving
                    drawMoveAnimation(batch, fireBall, delta);
                    break;
                case 2:
                    // dying
                    drawDeathAnimation(batch, fireBall, delta);
                    break;
                case 3:

                    break;
            }
        }
    }

    private void drawSpawningAnimation(SpriteBatch batch, FireBall fireBall, float delta) {

        fireBall.setHasSpawnAnimationStarted(checkIfAnimationHasStarted(fireBall.hasSpawnAnimationStarted(), fireBall));
        super.drawGamePlay(batch, fireBall, fireBall.isClockWise() ? super.spawnAnimationReversed : super.spawnAnimation, delta, 12);

    }

    private void drawMoveAnimation(SpriteBatch batch, FireBall fireBall, float delta) {

        fireBall.setHasMoveAnimationStarted(checkIfAnimationHasStarted(fireBall.hasMoveAnimationStarted(), fireBall));
        super.drawGamePlay(batch, fireBall, fireBall.isClockWise() ? super.moveAnimationReversed : super.moveAnimation, delta, 12);

    }

    private void drawDeathAnimation(SpriteBatch batch, FireBall fireBall, float delta) {
        fireBall.setHasDeathAnimationStarted(checkIfAnimationHasStarted(fireBall.hasDeathAnimationStarted(), fireBall));
        super.drawGamePlay(batch, fireBall, fireBall.isClockWise() ? super.deathAnimationReversed : super.deathAnimation, delta, 12);
    }

}

