package com.malog.esiea.monsters.game.event;

import com.malog.esiea.monsters.monsters.Monster;

public class MonsterHealedEvent extends Event{
    private final int heal_amount;
    private final Monster healed_monster;

    public MonsterHealedEvent(Monster healed_monster, int heal_amount) {
        this.heal_amount = heal_amount;
        this.healed_monster = healed_monster;
    }

    @Override
    public String toString() {
        return healed_monster.getName() + " healed " + heal_amount + " PV";
    }
}
