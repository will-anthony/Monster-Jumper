package com.jga.jumper.entity.entity_providers;

import com.badlogic.gdx.utils.Array;

public interface EntityProvider<T> {
    Array<T> getEntities();
}
