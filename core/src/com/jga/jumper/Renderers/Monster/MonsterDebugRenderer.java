package com.jga.jumper.Renderers.Monster;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
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
            Rectangle monsterBounds = monster.getBounds();

            renderer.rect(monsterBounds.x, monsterBounds.y,
                    0,0,
                    monsterBounds.width, monsterBounds.height,
                    1,1,
                    GameConfig.START_ANGLE - monster.getAngleDegrees());
        }
    }
}
