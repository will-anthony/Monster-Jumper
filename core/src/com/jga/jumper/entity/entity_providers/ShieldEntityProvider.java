package com.jga.jumper.entity.entity_providers;

import com.badlogic.gdx.utils.Array;
import com.jga.jumper.controllers.ShieldController;
import com.jga.jumper.entity.Shield;

public class ShieldEntityProvider implements EntityProvider<Shield> {

    private final ShieldController shieldController;

    public ShieldEntityProvider(ShieldController shieldController) {
        this.shieldController = shieldController;
    }

    @Override
    public Array<Shield> getEntities() {
        return shieldController.getShields();
    }
}
