package com.malog.esiea.monsters.UI;

import com.malog.esiea.monsters.game.Player;
import com.malog.esiea.monsters.game.Team;
import com.malog.esiea.monsters.game.event.Event;
import com.malog.esiea.monsters.monsters.MonsterBuilder;
import com.malog.esiea.monsters.monsters.attacks.Attack;

import java.util.List;
import java.util.Map;

public abstract class UserInterface {

    public abstract void handleEvent(Event event);
    public abstract void handleEvents(List<Event> events);
    public abstract String getPseudo();
    public abstract Team chooseTeam(Player player, Map<Integer, MonsterBuilder> monsters, List<Attack> attacks);

}
