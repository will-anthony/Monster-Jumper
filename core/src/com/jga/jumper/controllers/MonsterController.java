package com.jga.jumper.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;
import com.jga.jumper.common.SoundListener;
import com.jga.jumper.config.GameConfig;
import com.jga.jumper.entity.Monster;
import com.jga.jumper.entity.MonsterState;
import com.jga.jumper.object_distance_checker.DistanceChecker;
import com.jga.jumper.screen.game.MasterController;

public class MonsterController {

    // == attributes ==
    private Monster monster;
    private final Array<Monster> monsters = new Array<>();
    private final Pool<Monster> monsterPool = Pools.get(Monster.class, 4);

    private float monsterStartX;
    private float monsterStartY;

    private SoundListener soundListener;

    // == constructors ==
    public MonsterController(SoundListener soundListener) {
        this.soundListener = soundListener;
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
            checkInput();
            monster.update(delta);
        }
    }

    private void checkInput(){
        if ((Gdx.input.isTouched() || Gdx.input.isKeyPressed(Input.Keys.SPACE))
                && monster.getState() == MonsterState.WALKING) {
            soundListener.jump();
            monster.jump();
        }

        if (Gdx.input.isKeyPressed(Input.Keys.ALT_LEFT) && monster.getDashInterval() <= 0) {
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
