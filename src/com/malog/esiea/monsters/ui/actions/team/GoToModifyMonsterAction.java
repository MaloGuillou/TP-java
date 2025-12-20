package com.malog.esiea.monsters.ui.actions.team;

import com.malog.esiea.monsters.ui.UIState;
import com.malog.esiea.monsters.ui.actions.GoToAction;

public class GoToModifyMonsterAction extends GoToAction {
    private final int monsterId;

    public GoToModifyMonsterAction(UIState uiState, int monsterId) {
        super(UIState.MODIFY_MONSTER_MENU, uiState);
        this.monsterId = monsterId;
    }

    public int getMonsterId() {
        return monsterId;
    }
}
