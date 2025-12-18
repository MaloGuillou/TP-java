package com.malog.esiea.monsters.game.user_actions;

import com.malog.esiea.monsters.game.event.Event;
import com.malog.esiea.monsters.game.Player;
import com.malog.esiea.monsters.terrains.Terrain;

import java.util.ArrayList;
import java.util.List;

public class AttackAction extends UserAction {
    private final boolean barehands;
    private final Integer attack_id;

    public AttackAction(){
        barehands = true;
        attack_id = null;
    }

    public AttackAction(Integer attack_id){
        this.barehands = false;
        this.attack_id = attack_id;
    }

    public List<Event> execute(Player player_1, Player player_2, Terrain terrain) {
        ArrayList<Event> events = new ArrayList<>();
        if(barehands){
            events.add(player_1.get_active_monster().attack_bare_hands(player_2.get_active_monster()));
        }else{
            events.addAll(player_1.get_active_monster().use_attack(player_2.get_active_monster(), terrain, this.attack_id));
        }
        return events;
    }
}
