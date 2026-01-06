package com.malog.esiea.monsters.game.event;

import com.malog.esiea.monsters.monsters.Monster;
import com.malog.esiea.monsters.monsters.attacks.Attack;

public class AttackMissedEvent extends Event {
    private final Monster monster_that_dodged;
    private final Attack attack_that_missed;

    public AttackMissedEvent(
            Monster monster_that_dodged,
            Attack attack_that_missed
    ){
        this.monster_that_dodged = monster_that_dodged;
        this.attack_that_missed = attack_that_missed;
    }

    @Override
    public String toString() {
        return attack_that_missed.toString() + " missed " + monster_that_dodged.toString();
    }
}
