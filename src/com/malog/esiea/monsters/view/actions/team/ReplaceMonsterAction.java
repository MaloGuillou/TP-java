package com.malog.esiea.monsters.view.actions.team;

import com.malog.esiea.monsters.monsters.Monster;
import com.malog.esiea.monsters.view.UIActionEnum;
import com.malog.esiea.monsters.view.UIState;
import com.malog.esiea.monsters.view.actions.UIAction;

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
