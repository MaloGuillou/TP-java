package com.malog.esiea.monsters.ui.actions.team;

import com.malog.esiea.monsters.ui.UIState;
import com.malog.esiea.monsters.ui.actions.GoToAction;

public class GoToReplaceMonsterAction extends GoToAction {
    private final int monsterId;

    public GoToReplaceMonsterAction(UIState uiState, int monsterId) {
        super(UIState.REPLACE_MONSTER_MENU, uiState);
        this.monsterId = monsterId;
    }

    public int getMonsterId() {
        return monsterId;
    }

}
