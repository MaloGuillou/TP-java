package com.malog.esiea.monsters.ui.actions.team;

import com.malog.esiea.monsters.ui.UIActionEnum;
import com.malog.esiea.monsters.ui.UIState;
import com.malog.esiea.monsters.ui.actions.UIAction;

public class MoveMonstersAction extends UIAction {
    private final int monster1Id;
    private final int monster2Id;

    public MoveMonstersAction(UIState uiState, int monster1Id, int monster2Id) {
        super(UIActionEnum.MOVE_MONSTER, uiState);
        this.monster1Id = monster1Id;
        this.monster2Id = monster2Id;
    }

    public int getMonster1Id() {
        return monster1Id;
    }
    public int getMonster2Id() {
        return monster2Id;
    }
}
