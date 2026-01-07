package com.malog.esiea.monsters.view;

import com.malog.esiea.monsters.game.Team;
import com.malog.esiea.monsters.game.event.Event;
import com.malog.esiea.monsters.items.Item;
import com.malog.esiea.monsters.view.backend_link.BackendLink;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class UI implements Runnable {
    protected UIState currentState;
    protected boolean exit = false;
    protected BackendLink backendLink;

    protected Team team;
    protected Item[] backpack;
    protected int current_monster_team_index;
    protected final Map<Integer, String> monsters;
    protected final Map<Integer, String> attacks;

    protected List<Event> events;

    public UI(BackendLink backendLink) {
        this.backendLink = backendLink;

        this.currentState = UIState.INIT_MENU;

        this.backpack = backendLink.getBackpack();

        this.team = backendLink.getTeam();
        current_monster_team_index = 1;
        this.monsters = backendLink.getMonsters();
        this.attacks = backendLink.getAttacks();

        this.events = new ArrayList<>();
    }

    @Override
    public void run() {
        while (!exit) {
            render();
        }
    }

    private void render() {
        switch (currentState){
            case INIT_MENU -> renderInitMenu();
            case MAIN_MENU -> renderMainMenu();
            case SETTINGS -> renderSettingsMenu();
            case PLAYER_MENU -> renderPlayerMenu();
            case TEAM_MENU -> renderTeamMenu();
            case BACKPACK_MENU -> renderBackpackMenu();
            case MONSTER_MENU -> renderMonsterMenu();
            case MATCH_TYPE_CHOICE_MENU -> renderMatchSelectionMenu();
            case MATCHMAKING_MENU ->  renderMatchMakingMenu();
            case MATCH_MENU -> renderMatchMenu();
            case MATCH_ATTACK_CHOICE_MENU -> renderMatchAttackChoiceMenu();
            case MATCH_ITEM_CHOICE_MENU -> renderMatchItemChoiceMenu();
            case MATCH_CHANGE_MONSTER_MENU -> renderMatchChangeMonsterMenu();
            case MATCH_EVENT_DISPLAY_MENU -> renderMatchEventDisplayMenu();
            case MATCH_CHANGE_MONSTER_FORCED_MENU -> renderMatchChangeMonsterForcedMenu();
            case MATCH_RESULT_MENU -> renderMatchResultMenu();
        }
    }


    protected abstract void renderInitMenu();
    protected abstract void renderMainMenu();
    protected abstract void renderSettingsMenu();

    protected abstract void renderPlayerMenu();
    protected abstract void renderTeamMenu();
    protected abstract void renderMonsterMenu();
    protected abstract void renderBackpackMenu();

    protected abstract void renderMatchSelectionMenu();
    protected abstract void renderMatchMakingMenu();

    protected abstract void renderMatchMenu();
    protected abstract void renderMatchAttackChoiceMenu();
    protected abstract void renderMatchItemChoiceMenu();
    protected abstract void renderMatchChangeMonsterMenu();
    protected abstract void renderMatchEventDisplayMenu();
    protected abstract void renderMatchChangeMonsterForcedMenu();
    protected abstract void renderMatchResultMenu();

}
