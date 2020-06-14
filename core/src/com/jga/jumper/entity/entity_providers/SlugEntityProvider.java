package com.jga.jumper.entity.entity_providers;

import com.badlogic.gdx.utils.Array;
import com.jga.jumper.controllers.SlugController;
import com.jga.jumper.entity.Slug;

public class SlugEntityProvider implements EntityProvider<Slug> {

    private final SlugController slugController;

    public SlugEntityProvider(SlugController slugController) {
        this.slugController = slugController;
    }

    @Override
    public Array<Slug> getEntities() {
        return slugController.getSlugs();
    }
}
