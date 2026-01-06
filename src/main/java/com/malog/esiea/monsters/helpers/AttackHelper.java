package com.malog.esiea.monsters.helpers;

import com.malog.esiea.monsters.game.event.AttackLandedEvent;
import com.malog.esiea.monsters.monsters.Monster;
import com.malog.esiea.monsters.monsters.attacks.Attack;

public class AttackHelper {
    public static AttackLandedEvent get_bare_hands_damage(Monster striker, Monster defender) {
        int coef = Randoms.get_random_int_in_range(Constants.min_attack_coef, Constants.max_attack_coef);

        int damage = (Constants.bare_hands_attack_multiplier * striker.getAttack() * coef) / (defender.getDefense() * 100);

        AttackLandedEvent attackLandedEvent;
        if(striker.getType().is_strong_against(defender.getType())){
            damage *= Constants.strong_type_multiplier;
            attackLandedEvent = new AttackLandedEvent(
                    defender,
                    null,
                    damage,
                    true
            );
        }else if (striker.getType().is_weak_against(defender.getType())){
            damage /= Constants.weak_type_divider;
            attackLandedEvent = new AttackLandedEvent(
                    defender,
                    null,
                    damage,
                    false
            );
        }else{
            attackLandedEvent = new AttackLandedEvent(
                    defender,
                    null,
                    damage,
                    null
            );
        }

        return attackLandedEvent;
    }

    public static AttackLandedEvent get_attack_damage(Monster striker, Attack attack, Monster defender) {
        int coef = Randoms.get_random_int_in_range(Constants.min_attack_coef, Constants.max_attack_coef);

        int damage = (((Constants.typed_attack_attack_multiplier * striker.getAttack() * attack.getAttack_power()) / (Constants.typed_attack_defense_multiplier * defender.getDefense())) + 2) * coef / 100;

        AttackLandedEvent attackLandedEvent;
        if(attack.getType().is_strong_against(defender.getType())){
            damage *= Constants.strong_type_multiplier;
            attackLandedEvent = new AttackLandedEvent(
                    defender,
                    attack,
                    damage,
                    true
            );
        }else if (attack.getType().is_weak_against(defender.getType())){
            damage /= Constants.weak_type_divider;
            attackLandedEvent = new AttackLandedEvent(
                    defender,
                    attack,
                    damage,
                    false
            );
        }else{
            attackLandedEvent = new AttackLandedEvent(
                    defender,
                    attack,
                    damage,
                    null
            );
        }

        return attackLandedEvent;
    }
}
