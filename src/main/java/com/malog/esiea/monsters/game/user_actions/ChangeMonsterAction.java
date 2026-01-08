package com.malog.esiea.monsters.game.user_actions;

import com.malog.esiea.monsters.game.Player;
import com.malog.esiea.monsters.game.event.Event;
import com.malog.esiea.monsters.game.event.MonsterWithdrawEvent;
import com.malog.esiea.monsters.terrains.Terrain;

import java.util.ArrayList;
import java.util.List;

public class ChangeMonsterAction extends UserAction{
    private final int id;

    public ChangeMonsterAction(int id){
        this.id = id;
    }

    @Override
    public List<Event> execute(Player attacker, Player defender, Terrain terrain) {
        ArrayList<Event> events = new ArrayList<>();
        events.add(new MonsterWithdrawEvent(attacker, attacker.get_active_monster()));
        Event event = terrain.monsterRemovedFromTerrain(attacker.get_active_monster());
        if (event != null){
            events.add(event);
        }
        events.add(attacker.change_active_monster(id));
        return events;
    }
}
