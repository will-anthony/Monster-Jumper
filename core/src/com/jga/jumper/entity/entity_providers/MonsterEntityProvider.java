package com.jga.jumper.entity.entity_providers;

import com.badlogic.gdx.utils.Array;
import com.jga.jumper.controllers.MonsterController;
import com.jga.jumper.entity.Monster;

public class MonsterEntityProvider implements EntityProvider<Monster> {

    private final MonsterController monsterController;

    public MonsterEntityProvider(MonsterController monsterController) {
        this.monsterController = monsterController;
    }

    @Override
    public Array<Monster> getEntities() {
        return monsterController.getMonsters();
    }
}
