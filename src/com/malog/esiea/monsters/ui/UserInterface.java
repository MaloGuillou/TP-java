package com.malog.esiea.monsters.ui;

import com.malog.esiea.monsters.monsters.Monster;
import com.malog.esiea.monsters.ui.actions.UIAction;
import com.malog.esiea.monsters.game.Team;
import com.malog.esiea.monsters.game.event.Event;

import java.util.List;

public abstract class UserInterface {

    public abstract void handleEvent(Event event);
    public abstract void handleEvents(List<Event> events);
    public abstract String getPseudo();
    public abstract UIAction mainMenu();
    public abstract UIAction settingsMenu();
    public abstract UIAction playerMenu();
    public abstract UIAction teamMenu(Team team);
    public abstract UIAction modifyMonsterMenu(Monster monster);
}
