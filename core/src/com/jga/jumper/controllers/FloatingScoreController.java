package com.jga.jumper.controllers;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;
import com.jga.jumper.common.FloatingScore;
import com.jga.jumper.config.GameConfig;

public class FloatingScoreController {

    // == attributes ==
    private final Array<FloatingScore> floatingScores = new Array<>();;
    private Pool<FloatingScore> floatingScorePool;

    // == constructors ==
    public FloatingScoreController() {
        floatingScorePool = Pools.get(FloatingScore.class);
    }

    // == public methods ==
    public void update(float delta) {
        for (int i = 0; i < floatingScores.size; i++) {
            FloatingScore floatingScore = floatingScores.get(i);
            floatingScore.update(delta);

            if (floatingScore.isFinished()) {
                floatingScorePool.free(floatingScore);
                floatingScores.removeIndex(i);
            }
        }
    }

    public void restart() {
        floatingScorePool.freeAll(floatingScores);
        floatingScores.clear();
    }

    public Array<FloatingScore> getFloatingScores() {
        return floatingScores;
    }

    public void addFloatingScore(int score) {
        if(floatingScores.size <= 0) {
            FloatingScore floatingScore = floatingScorePool.obtain();
            floatingScore.setStartPosition(GameConfig.HUD_WIDTH / 2f, GameConfig.HUD_HEIGHT / 2f);
            floatingScore.setScore(score);
            floatingScores.add(floatingScore);
            System.out.println(score);
        } else {
            FloatingScore currentFloatingScore = floatingScores.get(0);
            currentFloatingScore.addToScore(score);
        }
    }
}
