package com.malog.esiea.monsters.view.actions;

import com.malog.esiea.monsters.view.UIActionEnum;
import com.malog.esiea.monsters.view.UIState;

public abstract class UIAction {
    protected final UIActionEnum uiActionEnum;
    protected final UIState uiState;

    public UIAction(UIActionEnum uiActionEnum, UIState uiState) {
        this.uiActionEnum = uiActionEnum;
        this.uiState = uiState;
    }

    public UIActionEnum getUiActionEnum() {
        return uiActionEnum;
    }

    public UIState getUiState() {
        return uiState;
    }
}
