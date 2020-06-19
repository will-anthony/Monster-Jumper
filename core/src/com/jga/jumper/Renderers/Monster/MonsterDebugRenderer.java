package com.jga.jumper.Renderers.Monster;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.jga.jumper.config.GameConfig;
import com.jga.jumper.entity.Monster;

public class MonsterDebugRenderer {

    // == constructors ==
    public MonsterDebugRenderer() {

    }

    // == public methods ==
    public void renderMonsterDebug(ShapeRenderer renderer, Array<Monster> monsters) {

        for (Monster monster : monsters) {
            Circle monsterBounds = monster.getBounds();

            renderer.circle(monsterBounds.x + 0.5f, monsterBounds.y + 0.5f, monsterBounds.radius, 30);
        }


    }
}
