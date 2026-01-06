package com.malog.esiea.monsters.game.user_actions;

import com.malog.esiea.monsters.game.event.Event;
import com.malog.esiea.monsters.game.Player;
import com.malog.esiea.monsters.terrains.Terrain;

import java.util.ArrayList;
import java.util.List;

public class AttackAction extends UserAction {
    private final boolean barehanded;
    private final Integer attack_id;

    public AttackAction(){
        barehanded = true;
        attack_id = null;
    }

    public AttackAction(Integer attack_id){
        this.barehanded = false;
        this.attack_id = attack_id;
    }

    public List<Event> execute(Player attacker, Player defender, Terrain terrain) {
        ArrayList<Event> events = new ArrayList<>();
        if(barehanded){
            events.add(attacker.get_active_monster().attack_bare_hands(defender.get_active_monster()));
        }else{
            events.addAll(attacker.get_active_monster().use_attack(defender.get_active_monster(), terrain, this.attack_id));
        }
        return events;
    }
}
