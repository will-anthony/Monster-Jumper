package com.jga.jumper.entity.entity_providers;

import com.badlogic.gdx.utils.Array;
import com.jga.jumper.controllers.BearController;
import com.jga.jumper.entity.Bear;

public class BearEntityProvider implements EntityProvider<Bear> {

    private final BearController bearController;

    public BearEntityProvider(BearController bearController) {
        this.bearController = bearController;
    }

    @Override
    public Array<Bear> getEntities() {
        return bearController.getBears();
    }
}
