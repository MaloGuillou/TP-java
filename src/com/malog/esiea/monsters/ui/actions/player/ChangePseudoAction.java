package com.malog.esiea.monsters.ui.actions.player;

import com.malog.esiea.monsters.ui.UIActionEnum;
import com.malog.esiea.monsters.ui.UIState;
import com.malog.esiea.monsters.ui.actions.UIAction;

public class ChangePseudoAction extends UIAction {
    public ChangePseudoAction(UIState uiState) {
        super(UIActionEnum.CHANGE_PSEUDO, uiState);
    }
}
