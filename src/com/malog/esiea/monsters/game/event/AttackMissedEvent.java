package com.malog.esiea.monsters.game.event;

import com.malog.esiea.monsters.monsters.Monster;
import com.malog.esiea.monsters.monsters.attacks.Attack;

public class AttackMissedEvent extends Event {
    private Monster monster_that_dodged;
    private Attack attack_that_missed;

    public AttackMissedEvent(
            Monster monster_that_dodged,
            Attack attack_that_missed
    ){
        this.monster_that_dodged = monster_that_dodged;
        this.attack_that_missed = attack_that_missed;
    }
}
