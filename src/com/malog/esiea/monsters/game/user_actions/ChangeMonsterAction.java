package com.malog.esiea.monsters.game.user_actions;

import com.malog.esiea.monsters.game.Player;
import com.malog.esiea.monsters.game.event.Event;
import com.malog.esiea.monsters.terrains.Terrain;

import java.util.ArrayList;
import java.util.List;

public class ChangeMonsterAction extends UserAction{
    private final int id;

    public ChangeMonsterAction(int id){
        this.id = id;
    }

    @Override
    public List<Event> execute(Player player_1, Player player_2, Terrain terrain) {
        ArrayList<Event> events = new ArrayList<>();
        events.add(player_1.change_active_monster(id));
        return events;
    }
}
