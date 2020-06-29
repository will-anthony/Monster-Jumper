package com.jga.jumper.levels;

public interface Level {

    void update(float delta);
    boolean hasLevelFinished();
    void reset();
}
