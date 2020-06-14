package com.jga.jumper;

import com.jga.jumper.screen.game.GameScreen;
import com.jga.jumper.screen.game.GameBase;
import com.jga.jumper.screen.loading.LoadingScreen;

public class CircleJumperGame extends GameBase {

	@Override
	public void postCreate() {
		setScreen(new LoadingScreen(this));
	}
}
