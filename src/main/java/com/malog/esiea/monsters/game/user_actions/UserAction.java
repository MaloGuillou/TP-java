package com.malog.esiea.monsters.game.user_actions;

import com.malog.esiea.monsters.game.event.Event;
import com.malog.esiea.monsters.game.Player;
import com.malog.esiea.monsters.terrains.Terrain;

import java.util.List;

public abstract class UserAction {
    public abstract List<Event> execute(Player attacker, Player defender, Terrain terrain);
}
