package com.jga.jumper.controllers;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;
import com.jga.jumper.common.GameManager;
import com.jga.jumper.common.SoundListener;
import com.jga.jumper.config.GameConfig;
import com.jga.jumper.entity.Coin;
import com.jga.jumper.entity.abstract_classes_and_interfaces.EntityBase;
import com.jga.jumper.entity.Monster;

public class CoinController<T extends EntityBase> {

    // == attributes ==
    private final Array<Coin> coins = new Array<>();
    private final Pool<Coin> coinPool = Pools.get(Coin.class, 10);

    private ControllerRegister controllerRegister;
    private SoundListener soundListener;

    // == constructors ==
    public CoinController(ControllerRegister controllerRegister, SoundListener soundListener) {
        this.controllerRegister = controllerRegister;
        this.soundListener = soundListener;
    }

    // == public methods ==
    public void update(float delta) {
        for (Coin coin : coins) {
            coin.update(delta);
            checkCollision(controllerRegister.getMonsterController().getMonsters().get(0), coin);
            if(coin.getCoinState() == GameConfig.COIN_DEAD) {
                coinPool.free(coin);
                coins.removeValue(coin, true);
            }
        }
    }

    public Array<Coin> getCoins() {
        return coins;
    }

    public void restart() {
        coinPool.freeAll(coins);
        coins.clear();
    }

    public void spawnCoins(T coinParent, int amount) {

        for (int i = 0; i < amount; i++) {
            Coin coin = coinPool.obtain();
            coin.setStartingPosition(coinParent);
            coin.setAccelerationY(MathUtils.random(1f, 10f));
            coin.setAccelerationX(MathUtils.random(-30f, 30f));
            coins.add(coin);
        }
    }


    public void checkCollision(Monster monster, Coin coin) {
//        // player <-> coins
        if (Intersector.overlapConvexPolygons(monster.getPolygonCollider(), coin.getPolygonCollider())) {
            if(!coin.isCoinCollected()) {
                coin.setCoinCollected(true);
                GameManager.INSTANCE.addScore(GameConfig.COIN_SCORE);
                controllerRegister.getFloatingScoreController().addFloatingScore(GameConfig.COIN_SCORE);
                coin.setCoinState(GameConfig.COIN_COLLECTED);

                soundListener.hitCoin();
            }
        }
    }
}

