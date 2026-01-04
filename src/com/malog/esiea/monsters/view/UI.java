package com.malog.esiea.monsters.view;

import com.malog.esiea.monsters.game.Team;
import com.malog.esiea.monsters.view.backend_link.BackendLink;

import java.util.Map;

public abstract class UI implements Runnable {
    protected UIState currentState;
    protected boolean exit = false;
    protected BackendLink backendLink;

    protected Team team;
    protected int current_monster_team_index;
    protected final Map<Integer, String> monsters;
    protected final Map<Integer, String> attacks;

    public UI(BackendLink backendLink) {
        this.backendLink = backendLink;

        this.currentState = UIState.INIT_MENU;

        this.team = backendLink.getTeam();
        current_monster_team_index = 1;
        this.monsters = backendLink.getMonsters();
        this.attacks = backendLink.getAttacks();
    }

    @Override
    public void run() {
        while (!exit) {
            render();
        }
    }

    private void render(){
        switch (currentState){
            case INIT_MENU -> renderInitMenu();
            case MAIN_MENU -> renderMainMenu();
            case SETTINGS -> renderSettingsMenu();
            case PLAYER_MENU -> renderPlayerMenu();
            case TEAM_MENU -> renderTeamMenu();
            case MONSTER_MENU -> renderMonsterMenu();
            case MATCH_CHOICE_MENU -> renderMatchSelectionMenu();
            case MATCH_MENU -> renderMatchMenu();
        }
    }

    protected abstract void renderInitMenu();
    protected abstract void renderMainMenu();
    protected abstract void renderSettingsMenu();
    protected abstract void renderPlayerMenu();
    protected abstract void renderTeamMenu();
    protected abstract void renderMonsterMenu();
    protected abstract void renderMatchSelectionMenu();
    protected abstract void renderMatchMenu();
}
