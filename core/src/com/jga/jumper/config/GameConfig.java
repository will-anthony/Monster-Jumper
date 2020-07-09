package com.jga.jumper.config;

public class GameConfig {

    // == constants ==

    // world
    public static final float WIDTH = 480f;
    public static final float HEIGHT = 800f;

    public static final float WORLD_WIDTH = 16f;
    public static final float WORLD_HEIGHT = 24f;

    public static final float HUD_WIDTH = 480f; // world units
    public static final float HUD_HEIGHT = 800f; // world units

    public static final float WORLD_CENTER_X = WORLD_WIDTH / 2;
    public static final float WORLD_CENTER_Y = WORLD_HEIGHT / 2;

    public static final int CELL_SIZE = 1;

    // planet
    public static final float PLANET_SIZE = 9f;
    public static final float PLANET_HALF_SIZE = PLANET_SIZE / 2;

    // monster
    public static final float MONSTER_SIZE = 1.5f;
    public static final float MONSTER_HALF_SIZE = MONSTER_SIZE / 2;
    public static final float MONSTER_START_ANGULAR_SPEED = 40f;
    public static final float START_ANGLE = -90f;
    public static final float MONSTER_MAX_SPEED = 2f;
    public static final float MONSTER_START_ACCELERATION = 7f;
    public static final float MONSTER_BOUNCE_ACCELERATION = 6.3f;
    public static final float MONSTER_GRAVITY = 0.4f;
    public static final float MONSTER_DASH_DURATION = 0.16f;
    public static final float MONSTER_MAX_JUMP_TIME = 0.7f;

    // enemy
    public static final int ENEMY_SPAWNING_STATE = 0;
    public static final int ENEMY_IDLE_STATE = 1;
    public static final int ENEMY_WALKING_STATE = 2;
    public static final int ENEMY_ATTACKING_STATE = 3;
    public static final int ENEMY_DYING_STATE = 4;
    public static final int ENEMY_DEAD_STATE = 5;
    public static final int ENEMY_DAMAGED_STATE = 6;

    // projectiles
    public static final int PROJECTILE_SPAWNING_STATE = 0;
    public static final int PROJECTILE_MOVING_STATE = 1;
    public static final int PROJECTILE_DYING_STATE = 2;
    public static final int PROJECTILE_DEATH_STATE = 3;


    // slug
    public static final float SLUG_SIZE = 1.25f;
    public static final float SLUG_START_ANGULAR_SPEED = 8f;

    // bear
    public static final float BEAR_SIZE = 2f;
    public static final float BEAR_START_ANGULAR_SPEED = 3f;
    public static final float BEAR_CHARGE_SPEED = 30f;

    // mage
    public static final float MAGE_SIZE = 1.5f;
    public static final float MAGE_START_ANGULAR_SPEED = 4f;
    public static final float MAGE_POLYGON_ROTATION_OFFSET = 16f;

    // fire ball
    public static final float FIRE_BALL_SIZE = 0.5f;
    public static final float FIRE_BALL_START_ANGULAR_SPEED = 50f;

    // coin
    public static final float COIN_SIZE = 1f;
    public static final float COIN_HALF_SIZE = COIN_SIZE / 2f;
    public static final float COIN_SPAWN_TIME = 1.25f;
    public static final int MAX_COINS = 2;
    public static final int COIN_SCORE = 10;

    // obstacle
    public static final float OBSTACLE_SIZE = 1f; // world units
    public static final float OBSTACLE_HALF_SIZE = OBSTACLE_SIZE / 2f;
    public static final float OBSTACLE_SPAWN_TIME = 0.75f;
    public static final int MAX_OBSTACLES = 10;
    public static final int OBSTACLE_SCORE = 5;

    // common
    public static final float START_WAIT_TIME = 3f;
    public static final float MIN_ANG_DIST = 20f;

    // background
    public static final float FLOATING_DURATION = 0.75f;
    public static final float BACKGROUND_SCROLLING_SPEED = 2.5f;

    // == constants ==
    private GameConfig() {}
}
