package com.malog.esiea.monsters.game.event;

import com.malog.esiea.monsters.monsters.Monster;
import com.malog.esiea.monsters.monsters.attacks.Attack;

public class AttackDodgeEvent extends Event {
    private final Monster monster_that_dodged;

    public AttackDodgeEvent(
            Monster monster_that_dodged
    ){
        this.monster_that_dodged = monster_that_dodged;
    }

    @Override
    public String toString() {
        return monster_that_dodged.getName() + " dodge the attack";
    }
}
