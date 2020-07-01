package com.jga.jumper.Renderers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.jga.jumper.Renderers.Background.BackgroundGamePlayRenderer;
import com.jga.jumper.Renderers.Coin.CoinGamePlayRenderer;
import com.jga.jumper.Renderers.Coin.CoinDebugRenderer;
import com.jga.jumper.Renderers.Monster.MonsterDebugRenderer;
import com.jga.jumper.Renderers.Monster.MonsterGamePlayRenderer;
import com.jga.jumper.Renderers.Obstacle.ObstacleDebugRenderer;
import com.jga.jumper.Renderers.Obstacle.ObstacleGamePlayRenderer;
import com.jga.jumper.Renderers.Planet.PlanetDebugRenderer;
import com.jga.jumper.Renderers.Planet.PlanetGamePlayRenderer;
import com.jga.jumper.Renderers.Slug.SlugDebugRenderer;
import com.jga.jumper.Renderers.Slug.SlugGamePlayRenderer;
import com.jga.jumper.Renderers.bear.BearDebugRenderer;
import com.jga.jumper.Renderers.bear.BearGamePlayRenderer;

public class RendererRegister {

    // == attributes ==
    private BackgroundGamePlayRenderer backgroundGamePlayRenderer;
    private MonsterGamePlayRenderer monsterGamePlayRenderer;
    private PlanetGamePlayRenderer planetGamePlayRenderer;

    private SlugGamePlayRenderer slugGamePlayRenderer;
    private BearGamePlayRenderer bearGamePlayRenderer;

    private MonsterDebugRenderer monsterDebugRenderer;
    private PlanetDebugRenderer planetDebugRenderer;

    private SlugDebugRenderer slugDebugRenderer;
    private BearDebugRenderer bearDebugRenderer;

    private TextureAtlas gamePlayAtlas;

    public RendererRegister(TextureAtlas gamePlayAtlas) {
        this.gamePlayAtlas = gamePlayAtlas;
        init();
    }

    private void init() {
        backgroundGamePlayRenderer = new BackgroundGamePlayRenderer(gamePlayAtlas);
        monsterGamePlayRenderer = new MonsterGamePlayRenderer(gamePlayAtlas);
        planetGamePlayRenderer = new PlanetGamePlayRenderer(gamePlayAtlas);
        slugGamePlayRenderer = new SlugGamePlayRenderer(gamePlayAtlas);
        bearGamePlayRenderer = new BearGamePlayRenderer(gamePlayAtlas);

        monsterDebugRenderer = new MonsterDebugRenderer();
        planetDebugRenderer = new PlanetDebugRenderer();
        slugDebugRenderer = new SlugDebugRenderer();
        bearDebugRenderer = new BearDebugRenderer();
    }

    public BackgroundGamePlayRenderer getBackgroundGamePlayRenderer() {
        return backgroundGamePlayRenderer;
    }

    public MonsterGamePlayRenderer getMonsterGamePlayRenderer() {
        return monsterGamePlayRenderer;
    }

    public MonsterDebugRenderer getMonsterDebugRenderer() {
        return monsterDebugRenderer;
    }

    public PlanetDebugRenderer getPlanetDebugRenderer() {
        return planetDebugRenderer;
    }

    public PlanetGamePlayRenderer getPlanetGamePlayRenderer() {
        return planetGamePlayRenderer;
    }

    public SlugGamePlayRenderer getSlugGamePlayRenderer() {
        return  slugGamePlayRenderer;
    }

    public SlugDebugRenderer getSlugDebugRenderer() {
        return slugDebugRenderer;
    }

    public BearGamePlayRenderer getBearGamePlayRenderer() {
        return bearGamePlayRenderer;
    }

    public BearDebugRenderer getBearDebugRenderer() {
        return bearDebugRenderer;
    }
}
