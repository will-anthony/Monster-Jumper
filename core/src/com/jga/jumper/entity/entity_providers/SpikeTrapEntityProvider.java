package com.jga.jumper.entity.entity_providers;

import com.badlogic.gdx.utils.Array;
import com.jga.jumper.controllers.projectiles.SpikeTrapController;
import com.jga.jumper.entity.projectiles.SpikeTrap;

public class SpikeTrapEntityProvider implements EntityProvider<SpikeTrap>{

    private final SpikeTrapController spikeTrapController;

    public SpikeTrapEntityProvider(SpikeTrapController spikeTrapController) {
        this.spikeTrapController = spikeTrapController;
    }

    @Override
    public Array<SpikeTrap> getEntities() {
        return spikeTrapController.getSpikeTraps();
    }
}
