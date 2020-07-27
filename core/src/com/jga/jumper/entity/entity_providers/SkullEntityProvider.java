package com.jga.jumper.entity.entity_providers;

import com.badlogic.gdx.utils.Array;
import com.jga.jumper.controllers.SkullController;
import com.jga.jumper.entity.Skull;

public class SkullEntityProvider implements EntityProvider<Skull> {

    SkullController skullController;

    public SkullEntityProvider(SkullController skullController) {
        this.skullController = skullController;
    }

    @Override
    public Array<Skull> getEntities() {
        return skullController.getSkulls();
    }
}
