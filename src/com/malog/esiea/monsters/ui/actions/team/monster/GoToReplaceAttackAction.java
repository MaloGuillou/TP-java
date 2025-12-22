package com.malog.esiea.monsters.ui.actions.team.monster;

import com.malog.esiea.monsters.monsters.Monster;
import com.malog.esiea.monsters.ui.UIState;
import com.malog.esiea.monsters.ui.actions.GoToAction;

public class GoToReplaceAttackAction extends GoToAction {
    private final Monster monster;
    private final int attack_id;
    public GoToReplaceAttackAction(UIState uiState, Monster monster, int attack_id) {
        super(UIState.REPLACE_ATTACK_MENU, uiState);
        this.monster = monster;
        this.attack_id = attack_id;
    }

    public Monster getMonster() {
        return monster;
    }
    public int getAttackId(){
        return attack_id;
    }
}
