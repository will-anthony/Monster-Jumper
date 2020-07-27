package com.jga.jumper.Renderers.underground_shadow;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.jga.jumper.entity.projectiles.ProjectileBase;

public class UnderGroundShadow<T extends ProjectileBase> {

    public void renderUnderGroundShadow(ShapeRenderer renderer, T projectile) {
        renderer.circle(projectile.getX(), projectile.getY(), 0.5f, 20);
    }
}
