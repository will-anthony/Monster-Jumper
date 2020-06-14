package com.jga.jumper.entity.entity_providers;

import com.badlogic.gdx.utils.Array;
import com.jga.jumper.entity.Background;
import com.jga.jumper.utils.BackgroundController;

public class BackgroundEntityProvider implements EntityProvider<Background> {

    private final BackgroundController backgroundController;

    public BackgroundEntityProvider(BackgroundController backgroundController) {
        this.backgroundController = backgroundController;
    }

    @Override
    public Array<Background> getEntities() {
        return backgroundController.getBackgrounds();
    }
}
