package com.jga.jumper.entity.entity_providers;

import com.badlogic.gdx.utils.Array;
import com.jga.jumper.controllers.BearController;
import com.jga.jumper.controllers.MageController;
import com.jga.jumper.entity.Bear;
import com.jga.jumper.entity.Mage;

public class MageEntityProvider implements EntityProvider {

    private final MageController mageController;

    public MageEntityProvider(MageController mageController) {
        this.mageController = mageController;
    }

    @Override
    public Array<Mage> getEntities() {
        return mageController.getMages();
    }
}
