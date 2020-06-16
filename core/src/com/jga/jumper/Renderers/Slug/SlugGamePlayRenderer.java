package com.jga.jumper.Renderers.Slug;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.jga.jumper.Renderers.EntityGamePlayRenderer;
import com.jga.jumper.assets.RegionNames;
import com.jga.jumper.entity.Slug;
import com.jga.jumper.state_machines.SlugState;

public class SlugGamePlayRenderer extends EntityGamePlayRenderer<Slug> {

    public SlugGamePlayRenderer(TextureAtlas textureAtlas) {
        super(textureAtlas);
    }

    @Override
    protected void createAnimations(TextureAtlas textureAtlas) {
        //        super.walkingAnimation = new Animation<TextureRegion>(0.08f,
//                gamePlayAtlas.findRegions(RegionNames.WALK),
//                Animation.PlayMode.LOOP_PINGPONG);

        super.idleAnimation = new Animation<TextureRegion>(0.1f,
                textureAtlas.findRegions(RegionNames.SLUG_IDLE),
                Animation.PlayMode.LOOP);

//        super.deathAnimation = new Animation<TextureRegion>(0.1f,
//                gamePlayAtlas.findRegions(RegionNames.PLAYER_DEATH),
//                Animation.PlayMode.NORMAL);
    }

    @Override
    public void renderGamePlay(SpriteBatch batch, Array<Slug> slugs, float delta) {
        for (int i = 0; i < slugs.size; i++) {
            Slug slug = slugs.get(i);

            if (slug.getState() == SlugState.IDLE) {
                hasIdleAnimationStarted = checkIfAnimationHasStarted(hasIdleAnimationStarted);
                super.drawGamePlay(batch, slugs, super.idleAnimation, delta);

            } else if (slug.getState() == SlugState.WALKING) {
                hasWalkAnimationStarted = checkIfAnimationHasStarted(hasWalkAnimationStarted);
                super.drawGamePlay(batch, slugs, super.idleAnimation, delta);

            } else if (slug.getState() == SlugState.DEAD) {
                hasDeadAnimationStarted = checkIfAnimationHasStarted(hasDeadAnimationStarted);
                super.drawGamePlay(batch, slugs, super.idleAnimation, delta);
            }
        }
    }
}
