package com.malog.esiea.monsters.ui.actions;

import com.malog.esiea.monsters.ui.UIActionEnum;
import com.malog.esiea.monsters.ui.UIState;

public class GoToAction extends UIAction{
    private final UIState destination;
    public GoToAction(UIState destination, UIState uiState) {
        super(UIActionEnum.GO_TO, uiState);
        this.destination = destination;
    }

    public UIState getDestination() {
        return destination;
    }
}
