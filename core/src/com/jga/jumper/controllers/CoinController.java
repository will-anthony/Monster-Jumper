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
import com.jga.jumper.entity.EntityBase;
import com.jga.jumper.object_distance_checker.DistanceChecker;
import com.jga.jumper.screen.game.MasterController;

public class CoinController {

    // == attributes ==
    private final Array<Coin> coins = new Array<>();
    private final Pool<Coin> coinPool = Pools.get(Coin.class, 10);
    private float coinTimer;

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
        }

        spawnCoins(delta);
    }

    public Array<Coin> getCoins() {
        return coins;
    }

    public void restart() {
        coinPool.freeAll(coins);
        coins.clear();
    }

    public void spawnCoins(float delta) {
        coinTimer += delta;

        if (coinTimer < GameConfig.COIN_SPAWN_TIME) {
            return;
        }

        coinTimer = 0;

        if (coins.size == 0) {
            addCoins();
        }
    }

    private void addCoins() {

        int count = MathUtils.random(GameConfig.MAX_COINS);

        for (int i = 0; i < count; i++) {
            float randomAngle = MathUtils.random(360);

            boolean canSpawn = !isCoinNearby(randomAngle)
                    && !controllerRegister.getMonsterController().isMonsterNearBy(randomAngle);

            if (canSpawn) {
                Coin coin = coinPool.obtain();

                if (controllerRegister.getObstacleController().isObstacleNearby(randomAngle)) {
                    coin.setOffset(true);
                }

                coin.setAngleDegree(randomAngle);
                coins.add(coin);
            }
        }
    }

    public boolean isCoinNearby(float angle) {
        DistanceChecker<Coin> coinDistanceChecker = new DistanceChecker<>(this.coins);
        return coinDistanceChecker.isEntityNearBy(angle);
    }

    public void checkCollision(EntityBase otherEntity) {
//        // player <-> coins
//        for (int i = 0; i < coins.size; i++) {
//            Coin coin = coins.get(i);
//            if (Intersector.overlaps(otherEntity.getBounds(), coin.getBounds())) {
//                GameManager.INSTANCE.addScore(GameConfig.COIN_SCORE);
//                controllerRegister.getFloatingScoreController().addFloatingScore(GameConfig.COIN_SCORE);
//                coinPool.free(coin);
//                coins.removeIndex(i);
//                soundListener.hitCoin();
//            }
//        }
    }
}
