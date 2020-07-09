package com.jga.jumper.Renderers;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.utils.Array;
import com.jga.jumper.entity.EntityBase;
import com.jga.jumper.entity.Monster;

public class EntityDebugRenderer {

    public void renderEntityDebugLines(ShapeRenderer renderer, float[] vertices) {

        renderer.polygon(vertices);

    }
}

