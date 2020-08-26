package com.jga.jumper.entity;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Pool;

public class MonsterPool extends Pool<Monster> {


    public MonsterPool(int init, int max) {
        super(init, max);
    }

    @Override
    protected Monster newObject() {
        return new Monster() ;
    }
}
