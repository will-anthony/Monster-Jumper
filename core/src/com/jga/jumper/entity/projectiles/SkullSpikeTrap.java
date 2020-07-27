package com.jga.jumper.entity.projectiles;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.utils.Pool;
import com.jga.jumper.config.GameConfig;
import com.jga.jumper.entity.abstract_classes_and_interfaces.EntityBase;
import com.jga.jumper.entity.Skull;

public class SkullSpikeTrap extends EntityBase implements Pool.Poolable {

    private int currentSpikeTrapState;
    private float spawnDelayTimer;
    private Skull parentSkull;

    public SkullSpikeTrap() {

        setRadius(GameConfig.PLANET_HALF_SIZE - GameConfig.MONSTER_SIZE);
        setSize(1.5f,1.5f);
        currentSpikeTrapState = GameConfig.SPIKE_TRAP_SPAWNING_STATE;
        this.spawnDelayTimer = 1f;
    }

    @Override
    public Polygon definePolygonCollider() {
        float[] polygonCoordinates = { 0f , 0f ,
                0.7f , 1f ,
                1f , 0f ,
        };

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
        radius = (GameConfig.PLANET_HALF_SIZE - GameConfig.MONSTER_SIZE);
        spawnDelayTimer = 1;
    }

    @Override
    public void setRadius(float radius) {
        super.setRadius(radius);
        setAngleDegree();
    }

    public float getSpawnDelayTimer() {
        return spawnDelayTimer;
    }

    public void setSpawnDelayTimer(float spawnDelayTimer) {
        this.spawnDelayTimer = spawnDelayTimer;
    }

    public Skull getParentSkull() {
        return parentSkull;
    }

    public void setParentSkull(Skull parentSkull) {
        this.parentSkull = parentSkull;
    }
}
