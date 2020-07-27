package com.jga.jumper.entity.entity_providers;

import com.badlogic.gdx.utils.Array;
import com.jga.jumper.controllers.SkullSpikeTrapController;
import com.jga.jumper.entity.projectiles.SkullSpikeTrap;

public class SkullSpikeTrapEntityProvider implements EntityProvider<SkullSpikeTrap> {

    private final SkullSpikeTrapController skullSpikeTrapController;

    public SkullSpikeTrapEntityProvider(SkullSpikeTrapController skullSpikeTrapController) {
        this.skullSpikeTrapController = skullSpikeTrapController;
    }

    @Override
    public Array<SkullSpikeTrap> getEntities() {
        return skullSpikeTrapController.getSpikeTraps();
    }
}
