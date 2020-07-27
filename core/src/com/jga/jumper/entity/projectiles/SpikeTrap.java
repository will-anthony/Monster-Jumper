package com.jga.jumper.entity.projectiles;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.utils.Pool;
import com.jga.jumper.config.GameConfig;
import com.jga.jumper.entity.abstract_classes_and_interfaces.EntityBase;

public class SpikeTrap extends EntityBase implements Pool.Poolable {

    private int currentSpikeTrapState;
    private float spikeTrapIdleTimer;
    private float spawnDelayTimer;

    public SpikeTrap() {

        setRadius(GameConfig.PLANET_HALF_SIZE - GameConfig.MONSTER_SIZE);
        setSize(1.5f,1.5f);
        currentSpikeTrapState = GameConfig.SPIKE_TRAP_SPAWNING_STATE;
        spikeTrapIdleTimer = 2f;
        spawnDelayTimer = 1f;

    }

    @Override
    public Polygon definePolygonCollider() {
        float[] polygonCoordinates = {0, 0,
                0,1,
                1,1,
                1,0};

        Polygon polygon = new Polygon(polygonCoordinates);
        polygon.setOrigin(0, 0);

        polygon.setRotation(GameConfig.START_ANGLE - angleDegrees);

        return polygon;
    }

    @Override
    public void setAngleDegree() {

        super.setAngleDegree();

        float originX = GameConfig.WORLD_CENTER_X;
        float originY = GameConfig.WORLD_CENTER_Y;

        float newX = originX + MathUtils.cosDeg(-angleDegrees) * (radius);
        float newY = originY + MathUtils.sinDeg(-angleDegrees) * (radius);

        polygonCollider.setPosition(newX, newY);
        polygonCollider.setRotation(GameConfig.START_ANGLE - angleDegrees - 10f);

    }

    public int getCurrentSpikeTrapState() {
        return currentSpikeTrapState;
    }

    public void setCurrentSpikeTrapState(int currentSpikeTrapState) {
        this.currentSpikeTrapState = currentSpikeTrapState;
    }

    @Override
    public void reset() {
        currentSpikeTrapState = GameConfig.SPIKE_TRAP_SPAWNING_STATE;
        spikeTrapIdleTimer = 2f;
        radius = (GameConfig.PLANET_HALF_SIZE - GameConfig.MONSTER_SIZE);
        spawnDelayTimer = 1;
    }

    @Override
    public void setRadius(float radius) {
        super.setRadius(radius);
        setAngleDegree();
    }

    public float getSpikeTrapIdleTimer() {
        return spikeTrapIdleTimer;
    }

    public void setSpikeTrapIdleTimer(float spikeTrapIdleTimer) {
        this.spikeTrapIdleTimer = spikeTrapIdleTimer;
    }

    public float getSpawnDelayTimer() {
        return spawnDelayTimer;
    }

    public void setSpawnDelayTimer(float spawnDelayTimer) {
        this.spawnDelayTimer = spawnDelayTimer;
    }
}
