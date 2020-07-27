package com.jga.jumper.entity.entity_providers;

import com.badlogic.gdx.utils.Array;
import com.jga.jumper.controllers.MageController;
import com.jga.jumper.controllers.RedController;
import com.jga.jumper.entity.Mage;
import com.jga.jumper.entity.Red;

public class RedEntityProvider implements EntityProvider {

    private final RedController redController;

    public RedEntityProvider(RedController redController) {
        this.redController = redController;
    }

    @Override
    public Array<Red> getEntities() {
        return redController.getReds();
    }
}
