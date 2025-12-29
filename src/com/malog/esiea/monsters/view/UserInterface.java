package com.malog.esiea.monsters.view;

import com.malog.esiea.monsters.monsters.Monster;
import com.malog.esiea.monsters.monsters.MonsterBuilder;
import com.malog.esiea.monsters.monsters.attacks.Attack;
import com.malog.esiea.monsters.view.actions.UIAction;
import com.malog.esiea.monsters.game.Team;
import com.malog.esiea.monsters.game.event.Event;

import java.util.List;
import java.util.Map;

public abstract class UserInterface {

    public abstract void handleEvent(Event event);
    public abstract void handleEvents(List<Event> events);
    public abstract String getPseudo();
    public abstract UIAction mainMenu();
    public abstract UIAction settingsMenu();
    public abstract UIAction playerMenu();
    public abstract UIAction teamMenu(Team team);
    public abstract UIAction modifyMonsterMenu(Monster monster);
    public abstract UIAction replaceMonsterMenu(int monster_id, Map<Integer, MonsterBuilder> monsters);
    public abstract UIAction modifyAttacksMenu(Monster monster);
    public abstract UIAction replaceAttacksMenu(List<Attack> attacks, Monster monster, int attack_id);
}
