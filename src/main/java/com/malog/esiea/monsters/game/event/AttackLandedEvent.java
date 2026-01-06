package com.malog.esiea.monsters.game.event;

import com.malog.esiea.monsters.monsters.Monster;
import com.malog.esiea.monsters.monsters.attacks.Attack;

public class AttackLandedEvent extends Event {
    private final Monster monster_that_took_damage;
    private final Attack attack_that_landed; // null = bare hands
    private final int damage;
    private final Boolean critical; // true = super effective, false = not effective, null = normal

    public AttackLandedEvent(
            Monster monster_that_took_damage,
            Attack attack_that_landed,
            int damage,
            Boolean critical
    ){
        this.monster_that_took_damage = monster_that_took_damage;
        this.attack_that_landed = attack_that_landed;
        this.damage = damage;
        this.critical = critical;
    }

    public int getDamage(){
        return damage;
    }

    @Override
    public String toString() {
        return monster_that_took_damage.toString() + " received " + damage + " damage points from " + (attack_that_landed == null ? "a bare hand attack" : attack_that_landed.toString()) + (critical !=null ? critical ? " it's super effective" : " it's not very effective" : "");
    }
}
