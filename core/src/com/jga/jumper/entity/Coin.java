package com.jga.jumper.entity;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Pool;
import com.jga.jumper.config.GameConfig;

public class Coin extends EntityBase implements Pool.Poolable {

    // == constants ==
    private static final float SCALE_MAX = 1.0f;

    // == attributes ==
    private boolean offset;
    private float scale;

    // == constructors ==
    public Coin() {
        setSize(GameConfig.COIN_SIZE, GameConfig.COIN_SIZE);
    }

    // == public methods ==

    public void update(float delta) {
        if (scale < SCALE_MAX) {
            scale += delta;
        }
    }

    public void setAngleDegree(float value) {
        angleDegrees = value % 360;

        float radius = GameConfig.PLANET_HALF_SIZE;

        if (offset) {
            radius += GameConfig.COIN_SIZE;
        }

        float originX = GameConfig.WORLD_CENTER_X;
        float originY = GameConfig.WORLD_CENTER_Y;

        float newX = originX + MathUtils.cosDeg(-angleDegrees) * radius;
        float newY = originY + MathUtils.sinDeg(-angleDegrees) * radius;

        setPosition(newX, newY);

    }

    public float getScale() {
        return scale;
    }

    public void setOffset(boolean offset) {
        this.offset = offset;
    }

    @Override
    public void reset() {
        offset = false;
        scale = 0.0f;
    }
}
