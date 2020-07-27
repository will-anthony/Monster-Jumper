package com.jga.jumper.Renderers.spike_trap;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.jga.jumper.assets.RegionNames;
import com.jga.jumper.config.GameConfig;
import com.jga.jumper.entity.Planet;
import com.jga.jumper.entity.projectiles.SpikeTrap;

public class SpikeTrapGamePlayRenderer {
    // == attributes ==
    private TextureAtlas gamePlayAtlas;

    // == constructors ==
    public SpikeTrapGamePlayRenderer(TextureAtlas gamePlayAtlas) {
        this.gamePlayAtlas = gamePlayAtlas;
    }

    // == public methods ==
    public void renderSpikeTrapGamePlay(SpriteBatch batch, Array<SpikeTrap> spikeTraps) {

        TextureRegion spikeTrapRegion = gamePlayAtlas.findRegion(RegionNames.SPIKE_TRAP);
        for (SpikeTrap spikeTrap : spikeTraps) {

            batch.draw(spikeTrapRegion,
                    spikeTrap.getX(), spikeTrap.getY(),
                    0,0,
                    spikeTrap.getWidth(),spikeTrap.getHeight(),
                    1,1,
                    GameConfig.START_ANGLE - spikeTrap.getRotation(4)
            );
        }
    }

}
