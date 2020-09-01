package com.jga.jumper.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.utils.Array;
import com.jga.jumper.common.SoundListener;
import com.jga.jumper.config.GameConfig;
import com.jga.jumper.entity.Monster;
import com.jga.jumper.entity.MonsterPool;
import com.jga.jumper.object_distance_checker.DistanceChecker;
import com.jga.jumper.state_machines.MonsterState;

public class MonsterController{

    // == attributes ==
    private Monster monster;
    private final Array<Monster> monsters;
    private final MonsterPool monsterPool;

    private ControllerRegister controllerRegister;
    private float monsterStartX;
    private float monsterStartY;

    private boolean isJumping;
    private float maxJumpTime = GameConfig.MONSTER_MAX_JUMP_TIME;
    private float jumpTimeCounter;

    private SoundListener soundListener;

    // == constructors ==
    public MonsterController(SoundListener soundListener, ControllerRegister controllerRegister) {
        this.soundListener = soundListener;
        this.controllerRegister = controllerRegister;
        this.monsters = new Array<>();
        this.monsterPool = new MonsterPool(1, 4);
        init();
    }

    // == init ==
    private void init() {

        monsterStartX = GameConfig.WORLD_CENTER_X - GameConfig.MONSTER_HALF_SIZE;
        monsterStartY = GameConfig.WORLD_CENTER_Y + GameConfig.PLANET_HALF_SIZE;

        monster = monsterPool.obtain();
        monster.setPosition(monsterStartX, monsterStartY);
        monsters.add(monster);
    }

    // == public methods ==
    public void update(float delta) {
        for (Monster monster: monsters) {
            checkInput(delta);
            monster.update(delta);
        }
    }

    private void checkInput(float delta){

        // input which allows minimum jump height when key is first pressed
        if ((Gdx.input.isTouched() || Gdx.input.isKeyPressed(Input.Keys.SPACE))
                && monster.getState() == MonsterState.WALKING) {
            soundListener.jump();
            isJumping = true;
            monster.setAcceleration(GameConfig.MONSTER_START_ACCELERATION);
            monster.setDoubleJumpAcceleration(GameConfig.MONSTER_START_ACCELERATION);
            controllerRegister.getTrapWarningSmokeController().spawnTrapWarningSmoke(
                    monster, 0, 0,
                    GameConfig.TRAP_WARNING_SMOKE_WITHDRAW_STATE, GameConfig.PLANET_HALF_SIZE);
            monster.jump();
        }

        // input which checks how long the key is held and increases jump height up to a max
        if(Gdx.input.isKeyPressed(Input.Keys.SPACE) || (Gdx.input.isTouched() && isJumping && jumpTimeCounter < maxJumpTime )) {
            float currentMonsterAcceleration = monster.getAcceleration();
            monster.setAcceleration(currentMonsterAcceleration + 0.25f);
            jumpTimeCounter += delta;
        }

        // resets jumping variables when monster leaves jump state
        if(monster.getState() == MonsterState.WALKING) {
            isJumping = false;
            jumpTimeCounter = 0;
            monster.setDoubleJumpAcceleration(0);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.ALT_LEFT) || (Gdx.input.getRoll() > 30) && monster.getDashInterval() <= 0) {
            monster.dash();
        }
    }

    public void restart() {
        monsterPool.freeAll(monsters);
        monster.reset();
        monster.setPosition(monsterStartX, monsterStartY);
    }

    public Array<Monster> getMonsters() {
        return monsters;
    }

    public boolean isMonsterNearBy(float angle) {
        DistanceChecker<Monster> monsterDistanceChecker = new DistanceChecker<>(monsters);
        return monsterDistanceChecker.isEntityNearBy(angle);
    }
}
