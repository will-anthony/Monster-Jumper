package com.jga.jumper.entity.entity_providers;

import com.badlogic.gdx.utils.Array;
import com.jga.jumper.controllers.SlugBossController;
import com.jga.jumper.controllers.SlugController;
import com.jga.jumper.entity.Slug;
import com.jga.jumper.entity.SlugBoss;

public class SlugBossEntityProvider implements EntityProvider<SlugBoss>{

    private final SlugBossController slugBossController;

    public SlugBossEntityProvider(SlugBossController slugBossController) {
        this.slugBossController = slugBossController;
    }

    @Override
    public Array<SlugBoss> getEntities() {
        return slugBossController.getSlugBosses();
    }
}
