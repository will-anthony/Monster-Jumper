package com.jga.jumper.Renderers;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class EntityDebugRenderer {

    public void renderEntityDebugLines(ShapeRenderer renderer, float[] vertices) {

        renderer.polygon(vertices);

    }
}

