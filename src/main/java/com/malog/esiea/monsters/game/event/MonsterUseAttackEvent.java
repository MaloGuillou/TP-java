package com.malog.esiea.monsters.game.event;

import com.malog.esiea.monsters.game.Player;
import com.malog.esiea.monsters.monsters.Monster;
import com.malog.esiea.monsters.monsters.attacks.Attack;

public class MonsterUseAttackEvent extends Event{
    private final Monster attacker;
    private final Attack attack;

    public MonsterUseAttackEvent(Monster attacker, Attack attack) {
        this.attacker = attacker;
        this.attack = attack;
    }

    public MonsterUseAttackEvent(Monster attacker) {
        this.attacker = attacker;
        this.attack = null;
    }

    @Override
    public String toString() {
        return attacker.getName() + " uses " + (attack == null ? "bare hands attack" : attack.getName());
    }
}
