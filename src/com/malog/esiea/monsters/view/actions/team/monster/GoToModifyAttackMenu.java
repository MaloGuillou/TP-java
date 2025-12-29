package com.malog.esiea.monsters.view.actions.team.monster;

import com.malog.esiea.monsters.monsters.Monster;
import com.malog.esiea.monsters.view.UIState;
import com.malog.esiea.monsters.view.actions.GoToAction;

public class GoToModifyAttackMenu extends GoToAction {
    private final Monster monster;

    public GoToModifyAttackMenu(UIState uiState, Monster monster) {
        super(UIState.MODIFY_ATTACK_MENU, uiState);
        this.monster = monster;
    }

    public Monster getMonster() {
        return monster;
    }
}
