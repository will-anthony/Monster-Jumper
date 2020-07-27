package com.jga.jumper.entity;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.utils.Pool;
import com.jga.jumper.config.GameConfig;
import com.jga.jumper.entity.abstract_classes_and_interfaces.AwarenessCollider;
import com.jga.jumper.entity.abstract_classes_and_interfaces.EnemyBase;
import com.jga.jumper.entity.abstract_classes_and_interfaces.KillCollider;

public class SlugBoss extends EnemyBase implements Pool.Poolable, KillCollider, AwarenessCollider {
    private float angleDegreeSpeed;
    private float sensorAngleDegree;
    private int currentSlugBossState;
    private int hitPoints;

    private Polygon awarenessCollider;
    private boolean turnOnAwarenessCollider;
    private int randomAttack;

    private float slugBossDamagedTimer;
    private float timeBetweenSummons;
    private boolean summoningChildSlug;
    private float deathTimer;

    private boolean hasSummonAnimationStarted;
    private boolean hasDamagedAnimationStarted;
    private boolean hasLowAttackAnimationStarted;

    private Polygon killCollider;



    public SlugBoss() {
        angleDegreeSpeed = GameConfig.SLUG_BOSS_START_ANGULAR_SPEED;
        setSize(GameConfig.SLUG_BOSS_SIZE, GameConfig.SLUG_BOSS_SIZE);
        super.setRadius(GameConfig.PLANET_HALF_SIZE - GameConfig.MONSTER_SIZE);
        clockWise = false;

        this.killCollider = defineKillCollider();
        this.awarenessCollider = defineAwarenessCollider();
        this.hitPoints = 2;
        this.turnOnAwarenessCollider = true;

        this.slugBossDamagedTimer = 1f;
        this.deathTimer = 0.5f;
        this.timeBetweenSummons = MathUtils.random(3f, 6f);
        this.summoningChildSlug = false;

    }

    @Override
    public Polygon definePolygonCollider() {

        float[] polygonCoordinates = {1.8f, -0.5f,
                1.8f, 1,
                GameConfig.SLUG_BOSS_SIZE - 0.4f, 1,
                GameConfig.SLUG_BOSS_SIZE - 0.4f, -0.5f};

        Polygon polygon = new Polygon(polygonCoordinates);
        polygon.setOrigin(0, 0);

        return polygon;

    }

    @Override
    public Polygon defineKillCollider() {

        float[] polygonCoordinates = { 1.8f, 1,
                1.8f, 1.2f,
                GameConfig.SLUG_BOSS_SIZE - 0.4f, 1.2f,
                GameConfig.SLUG_BOSS_SIZE - 0.4f, 1 };

        Polygon polygon = new Polygon(polygonCoordinates);
        polygon.setOrigin(0, 0);

        return polygon;

    }

    @Override
    public Polygon defineAwarenessCollider() {

        float[] polygonCoordinatesAntiClockwise = {1.6f, -2,
                1.6f, 3,
                -1f, 3,
                -1f, -2};

        {
            Polygon polygon = new Polygon(polygonCoordinatesAntiClockwise);
            polygon.setOrigin(0, 0);


            polygon.setRotation(GameConfig.START_ANGLE - angleDegrees);

            return polygon;

        }
    }

    public void move(float delta) {

        angleDegrees -= angleDegreeSpeed * delta;
        setAngleDegree();

    }

    public void setStartingPosition(float value) {
        angleDegrees = value % 360;
        setAngleDegree();
    }


    @Override
    public void setAngleDegree() {

        sensorAngleDegree = angleDegrees + 3f;

        super.setAngleDegree();

        float originX = GameConfig.WORLD_CENTER_X;
        float originY = GameConfig.WORLD_CENTER_Y;

        float newX = originX + MathUtils.cosDeg(-sensorAngleDegree) * (radius);
        float newY = originY + MathUtils.sinDeg(-sensorAngleDegree) * (radius);

        polygonCollider.setPosition(newX, newY);
        polygonCollider.setRotation(GameConfig.START_ANGLE - angleDegrees - 26);

        killCollider.setPosition(newX, newY);
        killCollider.setRotation(GameConfig.START_ANGLE - angleDegrees - 26);

        awarenessCollider.setPosition(newX, newY);
        awarenessCollider.setRotation(GameConfig.START_ANGLE - angleDegrees);

    }

    @Override
    public void reset() {
        setPosition(0, 0);
        currentSlugBossState = GameConfig.ENEMY_SPAWNING_STATE;
        radius = GameConfig.PLANET_HALF_SIZE - GameConfig.MONSTER_SIZE;
        deathTimer = 0.5f;
        clockWise = false;
        slugBossDamagedTimer = 1f;
        hitPoints = 2;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
        setAngleDegree();
    }

    public float getDeathTimer() {
        return deathTimer;
    }

    public void setDeathTimer(float deathTimer) {
        this.deathTimer = deathTimer;
    }

    public int getCurrentSlugBossState() {
        return currentSlugBossState;
    }

    public void setCurrentSlugBossState(int currentSlugBossState) {
        this.currentSlugBossState = currentSlugBossState;
    }

    @Override
    public Polygon getKillCollider() {
        return this.killCollider;
    }

    @Override
    public Polygon getAwarenessCollider() {
        return this.awarenessCollider;
    }

    public boolean hasSummonAnimationStarted() {
        return hasSummonAnimationStarted;
    }

    public void setHasSummonAnimationStarted(boolean hasSummonAnimationStarted) {
        this.hasSummonAnimationStarted = hasSummonAnimationStarted;
    }

    public boolean hasDamagedAnimationStarted() {
        return hasDamagedAnimationStarted;
    }

    public void setHasDamagedAnimationStarted(boolean hasDamagedAnimationStarted) {
        this.hasDamagedAnimationStarted = hasDamagedAnimationStarted;
    }

    public boolean hasLowAttackAnimationStarted() {
        return hasLowAttackAnimationStarted;
    }

    public void setHasLowAttackAnimationStarted(boolean hasLowAttackAnimationStarted) {
        this.hasLowAttackAnimationStarted = hasLowAttackAnimationStarted;
    }

    public int getRandomAttack() {
        return randomAttack;
    }

    public void setRandomAttack(int randomAttack) {
        this.randomAttack = randomAttack;
    }

    public boolean isTurnOnAwarenessCollider() {
        return turnOnAwarenessCollider;
    }

    public void setTurnOnAwarenessCollider(boolean turnOnAwarenessCollider) {
        this.turnOnAwarenessCollider = turnOnAwarenessCollider;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public void setHitPoints(int hitPoints) {
        this.hitPoints = hitPoints;
    }

    public float getSlugBossDamagedTimer() {
        return slugBossDamagedTimer;
    }

    public void setSlugBossDamagedTimer(float slugBossDamagedTimer) {
        this.slugBossDamagedTimer = slugBossDamagedTimer;
    }

    public float getTimeBetweenSummons() {
        return timeBetweenSummons;
    }

    public void setTimeBetweenSummons(float timeBetweenSummons) {
        this.timeBetweenSummons = timeBetweenSummons;
    }

    public boolean isSummoningChildSlug() {
        return summoningChildSlug;
    }

    public void setSummoningChildSlug(boolean summoningChildSlug) {
        this.summoningChildSlug = summoningChildSlug;
    }
}
