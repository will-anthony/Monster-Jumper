package com.jga.jumper.entity.entity_providers;

import com.badlogic.gdx.utils.Array;
import com.jga.jumper.controllers.projectiles.TrapWarningSmokeController;
import com.jga.jumper.entity.smoke_effects.TrapWarningSmoke;

public class TrapWarningSmokeEntityProvider implements EntityProvider<TrapWarningSmoke> {

    private final TrapWarningSmokeController trapWarningSmokeController;

    public TrapWarningSmokeEntityProvider(TrapWarningSmokeController trapWarningSmokeController) {
        this.trapWarningSmokeController = trapWarningSmokeController;
    }

    @Override
    public Array<TrapWarningSmoke> getEntities() {
        return trapWarningSmokeController.getTrapWarningSmokes();
    }
}
