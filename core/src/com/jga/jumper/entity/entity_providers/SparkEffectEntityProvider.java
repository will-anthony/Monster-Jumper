package com.jga.jumper.entity.entity_providers;

import com.badlogic.gdx.utils.Array;
import com.jga.jumper.controllers.projectiles.SparkEffectController;
import com.jga.jumper.controllers.projectiles.TrapWarningSmokeController;
import com.jga.jumper.entity.smoke_effects.SparkEffect;
import com.jga.jumper.entity.smoke_effects.TrapWarningSmoke;

public class SparkEffectEntityProvider implements EntityProvider<SparkEffect> {

    private final SparkEffectController sparkEffectController;

    public SparkEffectEntityProvider(SparkEffectController sparkEffectController) {
        this.sparkEffectController = sparkEffectController;
    }

    @Override
    public Array<SparkEffect> getEntities() {
        return sparkEffectController.getSparks();
    }
}
