package com.malog.esiea.monsters.ui.actions;

import com.malog.esiea.monsters.ui.UIActionEnum;
import com.malog.esiea.monsters.ui.UIState;

public abstract class UIAction {
    private final UIActionEnum uiActionEnum;
    private final UIState uiState;

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
