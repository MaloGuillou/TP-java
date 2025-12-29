package com.malog.esiea.monsters.view.actions.team;

import com.malog.esiea.monsters.view.UIState;
import com.malog.esiea.monsters.view.actions.GoToAction;

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
