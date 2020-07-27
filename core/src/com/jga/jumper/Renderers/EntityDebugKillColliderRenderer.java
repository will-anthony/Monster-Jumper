package com.jga.jumper.Renderers;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.utils.Array;
import com.jga.jumper.entity.abstract_classes_and_interfaces.KillCollider;

public class EntityDebugKillColliderRenderer<T extends KillCollider>  {

    public void renderEntityDebugLines(ShapeRenderer renderer, Array<T> entities) {

        for (T entity : entities) {
            Polygon polygonCollider = entity.getKillCollider();

            float[] polygonCoordinates = polygonCollider.getTransformedVertices();

            renderer.polygon(polygonCoordinates);

        }
    }

}
