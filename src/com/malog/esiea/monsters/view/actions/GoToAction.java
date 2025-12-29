package com.malog.esiea.monsters.view.actions;

import com.malog.esiea.monsters.view.UIActionEnum;
import com.malog.esiea.monsters.view.UIState;

public class GoToAction extends UIAction{
    protected final UIState destination;
    public GoToAction(UIState destination, UIState uiState) {
        super(UIActionEnum.GO_TO, uiState);
        this.destination = destination;
    }

    public UIState getDestination() {
        return destination;
    }

    @Override
    public String toString() {
        return "Going from " + this.uiState + " to " + this.destination;
    }
}
