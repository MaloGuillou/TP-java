package com.malog.esiea.monsters.ui.actions.team;

import com.malog.esiea.monsters.monsters.Monster;
import com.malog.esiea.monsters.ui.UIActionEnum;
import com.malog.esiea.monsters.ui.UIState;
import com.malog.esiea.monsters.ui.actions.UIAction;

public class ReplaceMonsterAction extends UIAction {
    private final int monster_index;
    private final Monster new_monster;

    public ReplaceMonsterAction(UIState uiState, int monsterIndex, Monster newMonster) {
        super(UIActionEnum.REPLACE_MONSTER, uiState);
        monster_index = monsterIndex;
        new_monster = newMonster;
    }

    public int  getMonsterIndex() {
        return monster_index;
    }

    public Monster getNewMonster() {
        return new_monster;
    }
}
