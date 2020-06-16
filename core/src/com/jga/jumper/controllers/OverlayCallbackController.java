package com.jga.jumper.controllers;

import com.jga.jumper.state_machines.GameState;
import com.jga.jumper.screen.menu.OverlayCallBack;

public class OverlayCallbackController {

    private OverlayCallBack callBack;
    private ControllerRegister controllerRegister;

    public OverlayCallbackController(ControllerRegister controllerRegister) {
        this.controllerRegister = controllerRegister;
        init();
    }

    private void init() {
        this.callBack = new OverlayCallBack() {
            @Override
            public void home() {
                controllerRegister.getMasterController().setGameState(GameState.MENU);
            }

            @Override
            public void ready() {
                controllerRegister.getMasterController().restart();
                controllerRegister.getMasterController().setGameState(GameState.READY);
            }
        };
    }

    public OverlayCallBack getCallBack() {
        return callBack;
    }
}
