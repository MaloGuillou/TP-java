package com.malog.esiea.monsters.view.actions.team.monster;

import com.malog.esiea.monsters.monsters.Monster;
import com.malog.esiea.monsters.monsters.attacks.Attack;
import com.malog.esiea.monsters.view.UIActionEnum;
import com.malog.esiea.monsters.view.UIState;
import com.malog.esiea.monsters.view.actions.UIAction;

public class ReplaceAttackAction extends UIAction {
    private final int attack_index;
    private final Attack new_attack;
    private final Monster monster;

    public ReplaceAttackAction(UIState uiState, int attack_index, Attack new_attack, Monster monster){
        super(UIActionEnum.REPLACE_ATTACK, uiState);

        this.attack_index = attack_index;
        this.new_attack = new_attack;
        this.monster = monster;
    }

    public int get_attack_index(){
        return attack_index;
    }

    public Attack getNew_attack() {
        return new_attack;
    }

    public Monster get_monster() {
        return monster;
    }
}
