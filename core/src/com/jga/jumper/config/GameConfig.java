package com.jga.jumper.config;

public class GameConfig {

    // == constants ==
    public static final float WIDTH = 480f;
    public static final float HEIGHT = 800f;

    public static final float WORLD_WIDTH = 16f;
    public static final float WORLD_HEIGHT = 24f;

    public static final float HUD_WIDTH = 480f; // world units
    public static final float HUD_HEIGHT = 800f; // world units

    public static final float WORLD_CENTER_X = WORLD_WIDTH / 2;
    public static final float WORLD_CENTER_Y = WORLD_HEIGHT / 2;

    public static final int CELL_SIZE = 1;

    public static final float PLANET_SIZE = 9f;
    public static final float PLANET_HALF_SIZE = PLANET_SIZE / 2;

    public static final float MONSTER_SIZE = 1.5f;
    public static final float MONSTER_HALF_SIZE = MONSTER_SIZE / 2;
    public static final float MONSTER_START_ANGULAR_SPEED = 45f;
    public static final float START_ANGLE = -90f;

    // slug
    public static final float SLUG_SIZE = 1.5f;
    public static final float SLUG_START_ANGULAR_SPEED = 8f;
    public static final int ENEMY_SPAWNING_STATE = 0;
    public static final int ENEMY_IDLE_STATE = 1;
    public static final int ENEMY_WALKING_STATE = 2;
    public static final int ENEMY_ATTACKING_STATE = 3;
    public static final int ENEMY_DYING_STATE = 4;
    public static final int ENEMY_DEAD_STATE = 5;

    public static final float MONSTER_MAX_SPEED = 2f;
    public static final float MONSTER_START_ACCELERATION = 4f;

    public static final float COIN_SIZE = 1f;
    public static final float COIN_HALF_SIZE = COIN_SIZE / 2f;
    public static final float COIN_SPAWN_TIME = 1.25f;
    public static final int MAX_COINS = 2;
    public static final int COIN_SCORE = 10;

    public static final float OBSTACLE_SIZE = 1f; // world units
    public static final float OBSTACLE_HALF_SIZE = OBSTACLE_SIZE / 2f;
    public static final float OBSTACLE_SPAWN_TIME = 0.75f;
    public static final int MAX_OBSTACLES = 10;
    public static final int OBSTACLE_SCORE = 5;

    public static final float START_WAIT_TIME = 3f;
    public static final float MIN_ANG_DIST = 40f;

    public static final float FLOATING_DURATION = 0.75f;
    public static final float BACKGROUND_SCROLLING_SPEED = 2.5f;

    // == constants ==
    private GameConfig() {}
}
