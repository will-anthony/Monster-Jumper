package com.jga.jumper.controllers;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.utils.Array;
import com.jga.jumper.common.SoundListener;
import com.jga.jumper.config.GameConfig;
import com.jga.jumper.entity.Mage;
import com.jga.jumper.entity.Monster;
import com.jga.jumper.entity.Shield;
import com.jga.jumper.entity.abstract_classes_and_interfaces.SmallEnemyBase;
import com.jga.jumper.state_machines.GameState;
import com.jga.jumper.state_machines.MonsterState;

public class ShieldController<T extends SmallEnemyBase> {

    private final Array<Shield> shields = new Array<>();
    private ControllerRegister controllerRegister;
    private MonsterController monsterController;

    private SoundListener soundListener;

    // == constructors ==
    public ShieldController(ControllerRegister controllerRegister, SoundListener soundListener) {
        this.controllerRegister = controllerRegister;
        this.monsterController = controllerRegister.getMonsterController();
        this.soundListener = soundListener;
    }

    public void update(float delta) {
        Shield shield;

        for (int i = 0; i < shields.size; i++) {

            shield = shields.get(i);
            switch (shield.getCurrentState()) {
                case 0:
                    // spawning
                    spawnLogic(shield, delta);
                    break;

                case 1:
                    // idle
                    idleLogic(shield, delta);
                    break;

                case 2:
                    withdrawLogic(shield, delta);
                    break;

                case 3:
                    // death
                    deathLogic(shield);
                    break;
            }
        }
    }

    private void spawnLogic(Shield shield, float delta) {

        float currentAlpha = shield.getShieldAlphaPercentage();
        float increasedAlpha = currentAlpha + (0.4f * delta);
        shield.setShieldAlphaPercentage(increasedAlpha);

        if (increasedAlpha >= 1) {
            shield.setShieldAlphaPercentage(1);
            shield.setCurrentState(GameConfig.SHIELD_IDLE_STATE);
        }

        moveLogic(shield, delta);
        shield.checkForDirectionChange();
    }

    private void idleLogic(Shield shield, float delta) {

        checkMonsterCollision(shield, monsterController.getMonsters().get(0));
        moveLogic(shield, delta);
        shield.checkForDirectionChange();
    }

    private void withdrawLogic(Shield shield, float delta) {

        float currentAlpha = shield.getShieldAlphaPercentage();
        float decreasedAlpha = currentAlpha + (2f * delta);
        shield.setShieldAlphaPercentage(decreasedAlpha);

        if (decreasedAlpha <= 0) {
            shield.setShieldAlphaPercentage(0);
            shield.setCurrentState(GameConfig.SHIELD_DEAD_STATE);
        }

        moveLogic(shield, delta);
        shield.checkForDirectionChange();
    }

    private void moveLogic(Shield shield, float delta) {
        if(shield.getShieldParent().isMoving()) {
            shield.move(delta);
        }
    }

    private void deathLogic(Shield shield) {
        shield.getShieldParent().setShielded(false);
        shields.removeValue(shield, true);
    }

    public void spawnShield(Mage mage, T shieldParent) {

        Shield shield = new Shield(shieldParent);


        shields.add(shield);
        shieldParent.setShielded(true);
    }

    public void restart() {
        shields.clear();
    }

    public Array<Shield> getShields() {
        return shields;
    }

    public void checkMonsterCollision(Shield shield, Monster monster) {

        if (Intersector.overlapConvexPolygons(monster.getPolygonCollider(), shield.getKillCollider()) && monster.getState() == MonsterState.FALLING) {
            shield.setCurrentState(GameConfig.SHIELD_DEAD_STATE);
            shield.getShieldParent().setShielded(false);
            monster.setAcceleration(GameConfig.MONSTER_BOUNCE_ACCELERATION);
            monster.jump();

        } else if (monster.getState() == MonsterState.DASHING && Intersector.overlapConvexPolygons(monster.getPolygonCollider(), shield.getPolygonCollider())) {
            shield.getShieldParent().setShielded(false);
            shield.setCurrentState(GameConfig.SHIELD_DEAD_STATE);

//            // shield kills monster
        } else if (Intersector.overlapConvexPolygons(monster.getPolygonCollider(), shield.getPolygonCollider()) &&
                monster.getState() != MonsterState.DASHING) {
            soundListener.lose();

            monster.dead();
            controllerRegister.getMasterController().setGameState(GameState.GAME_OVER);
        }
    }
}
