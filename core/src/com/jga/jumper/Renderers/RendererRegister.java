package com.jga.jumper.Renderers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.jga.jumper.Renderers.Background.BackgroundGamePlayRenderer;
import com.jga.jumper.Renderers.Coin.CoinGamePlayRenderer;
import com.jga.jumper.Renderers.Monster.MonsterDebugRenderer;
import com.jga.jumper.Renderers.Monster.MonsterGamePlayRenderer;
import com.jga.jumper.Renderers.Obstacle.ObstacleDebugRenderer;
import com.jga.jumper.Renderers.Obstacle.ObstacleGamePlayRenderer;
import com.jga.jumper.Renderers.Planet.PlanetDebugRenderer;
import com.jga.jumper.Renderers.Planet.PlanetGamePlayRenderer;

public class RendererRegister {

    // == attributes ==
    private BackgroundGamePlayRenderer backgroundGamePlayRenderer;
    private MonsterGamePlayRenderer monsterGamePlayRenderer;
    private CoinGamePlayRenderer coinGamePlayRenderer;
    private ObstacleGamePlayRenderer obstacleGamePlayRenderer;
    private PlanetGamePlayRenderer planetGamePlayRenderer;

    private MonsterDebugRenderer monsterDebugRenderer;
    private ObstacleDebugRenderer obstacleDebugRenderer;
    private com.jga.jumper.Renderers.CoinDebugRenderer coinDebugRenderer;
    private PlanetDebugRenderer planetDebugRenderer;

    private TextureAtlas gamePlayAtlas;

    public RendererRegister(TextureAtlas gamePlayAtlas) {
        this.gamePlayAtlas = gamePlayAtlas;
        init();
    }

    private void init() {
        backgroundGamePlayRenderer = new BackgroundGamePlayRenderer(gamePlayAtlas);
        monsterGamePlayRenderer = new MonsterGamePlayRenderer(gamePlayAtlas);
        obstacleGamePlayRenderer = new ObstacleGamePlayRenderer(gamePlayAtlas);
        coinGamePlayRenderer = new CoinGamePlayRenderer(gamePlayAtlas);
        planetGamePlayRenderer = new PlanetGamePlayRenderer(gamePlayAtlas);

        monsterDebugRenderer = new MonsterDebugRenderer();
        obstacleDebugRenderer = new ObstacleDebugRenderer();
        coinDebugRenderer = new com.jga.jumper.Renderers.CoinDebugRenderer();
        planetDebugRenderer = new PlanetDebugRenderer();
    }

    public BackgroundGamePlayRenderer getBackgroundGamePlayRenderer() {
        return backgroundGamePlayRenderer;
    }

    public MonsterGamePlayRenderer getMonsterGamePlayRenderer() {
        return monsterGamePlayRenderer;
    }

    public CoinGamePlayRenderer getCoinGamePlayRenderer() {
        return coinGamePlayRenderer;
    }

    public ObstacleGamePlayRenderer getObstacleGamePlayRenderer() {
        return obstacleGamePlayRenderer;
    }

    public PlanetGamePlayRenderer getPlanetGamePlayRenderer() {
        return planetGamePlayRenderer;
    }

    public MonsterDebugRenderer getMonsterDebugRenderer() {
        return monsterDebugRenderer;
    }

    public ObstacleDebugRenderer getObstacleDebugRenderer() {
        return obstacleDebugRenderer;
    }

    public com.jga.jumper.Renderers.CoinDebugRenderer getCoinDebugRenderer() {
        return coinDebugRenderer;
    }

    public PlanetDebugRenderer getPlanetDebugRenderer() {
        return planetDebugRenderer;
    }
}
