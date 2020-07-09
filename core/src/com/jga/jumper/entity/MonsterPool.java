package com.jga.jumper.entity;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Pool;

public class MonsterPool extends Pool<Monster> {

    private World world;

    public MonsterPool(int init, int max, World world) {
        super(init, max);
        this.world = world;
    }

    @Override
    protected Monster newObject() {
        return new Monster(this.world) ;
    }
}
