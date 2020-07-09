package com.jga.jumper.box2d;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public class Box2DTest {

    private World world;

    public Box2DTest() {
        this.world = new World(new Vector2(0, 0), true );
    }

    public void updatePhysics(float delta) {
        world.step(delta, 3,3);
    }

    public World getWorld() {
        return world;
    }

}
