package com.malog.esiea.monsters.view.actions.team;

import com.malog.esiea.monsters.view.UIActionEnum;
import com.malog.esiea.monsters.view.UIState;
import com.malog.esiea.monsters.view.actions.UIAction;

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
