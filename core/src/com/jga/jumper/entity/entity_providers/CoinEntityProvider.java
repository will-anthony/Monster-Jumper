package com.jga.jumper.entity.entity_providers;

import com.badlogic.gdx.utils.Array;
import com.jga.jumper.controllers.CoinController;
import com.jga.jumper.entity.Coin;

public class CoinEntityProvider implements EntityProvider<Coin> {

    private final CoinController coinController;

    public CoinEntityProvider(CoinController coinController) {
        this.coinController = coinController;
    }

    @Override
    public Array<Coin> getEntities() {
        return coinController.getCoins();
    }
}
