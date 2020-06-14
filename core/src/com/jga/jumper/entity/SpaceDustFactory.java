package com.jga.jumper.entity;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.jga.jumper.assets.AssetDescriptors;

public class SpaceDustFactory {

    // == attributes ==
    private final AssetManager assetManager;
    private ParticleEffect dustEffect;

    public SpaceDustFactory(AssetManager assetManager) {
        this.assetManager = assetManager;
        dustEffect = assetManager.get(AssetDescriptors.DUST);
    }

    public ParticleEffect startDustEffect(float xPos, float yPos) {
        dustEffect.setPosition(xPos, yPos);
        return dustEffect;
    }

    public ParticleEffect getDustEffect() {
        return dustEffect;
    }
}
