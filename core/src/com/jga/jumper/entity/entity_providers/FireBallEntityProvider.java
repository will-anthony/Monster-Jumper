package com.jga.jumper.entity.entity_providers;

import com.badlogic.gdx.utils.Array;
import com.jga.jumper.controllers.SlugController;
import com.jga.jumper.controllers.projectiles.FireBallController;
import com.jga.jumper.entity.Slug;
import com.jga.jumper.entity.projectiles.FireBall;

public class FireBallEntityProvider implements EntityProvider<FireBall> {

    private final FireBallController fireBallController;

    public FireBallEntityProvider(FireBallController fireBallController) {
        this.fireBallController = fireBallController;
    }

    @Override
    public Array<FireBall> getEntities() {
        return fireBallController.getFireBalls();
    }
}
