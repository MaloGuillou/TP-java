package com.malog.esiea.monsters;

import com.malog.esiea.monsters.ui.ConsoleInterface;
import com.malog.esiea.monsters.ui.actions.GoToAction;
import com.malog.esiea.monsters.ui.actions.NoAction;
import com.malog.esiea.monsters.ui.actions.UIAction;
import com.malog.esiea.monsters.ui.UIState;
import com.malog.esiea.monsters.ui.UserInterface;
import com.malog.esiea.monsters.game.MatchesManager;
import com.malog.esiea.monsters.game.Player;
import com.malog.esiea.monsters.monsters.MonsterBuilder;
import com.malog.esiea.monsters.monsters.attacks.Attack;
import com.malog.esiea.monsters.ui.actions.team.GoToModifyMonsterAction;

import java.util.List;
import java.util.Map;

public class ClientApp {

    private final Player player;
    private final UserInterface ui;
    private final Map<Integer, MonsterBuilder> monsters;
    private final List<Attack> attacks;
    private final MatchesManager matchesManager;

    public ClientApp(Map<Integer, MonsterBuilder> monsters, List<Attack> attacks) {
        this.monsters = monsters;
        this.attacks = attacks;
        this.matchesManager = new MatchesManager();

        this.ui = new ConsoleInterface();

        String pseudo = ui.getPseudo();
        this.player = new Player(pseudo);
    }

    public void run(){
        UIAction user_action = ui.mainMenu();
        UIAction old_action = user_action;
        boolean exit = false;
        while (!exit) {
            switch(user_action.getUiActionEnum()){
                case EXIT -> exit = true;
                case NO_ACTION -> user_action = old_action;
                case GO_TO -> handle_go_to((GoToAction) user_action);
                default -> {
                    old_action =  user_action; // save previous action in case of NO_ACTION (= bugged input)
                    user_action = handle_action(user_action);
                }
            }
        }
    }

    UIAction handle_go_to(GoToAction user_action){
        return switch(user_action.getDestination()){
            case MAIN_MENU -> ui.mainMenu();
            case SETTINGS -> ui.settingsMenu();
            case PLAYER_MENU -> ui.playerMenu();
            case TEAM_MENU -> ui.teamMenu(player.get_team());
            case REPLACE_MONSTER_MENU -> null;
            case MODIFY_MONSTER_MENU -> ui.modifyMonsterMenu(player.get_team().getMonster(((GoToModifyMonsterAction) user_action).getMonsterId()));
            case MODIFY_ATTACKS_MENU -> null;
            case MATCH_CHOICE_MENU -> null;
            case MATCH_MENU -> null;
        };
    }

    UIAction handle_action(UIAction user_action) {
        return switch (user_action.getUiState()) {
            case MAIN_MENU -> handle_main_menu(user_action);
            case SETTINGS -> handle_settings(user_action);
            case PLAYER_MENU -> handle_player_menu(user_action);
            case TEAM_MENU -> handle_team_menu(user_action);
            case REPLACE_MONSTER_MENU -> null;
            case MODIFY_MONSTER_MENU -> handle_monster_menu(user_action);
            case MODIFY_ATTACKS_MENU -> null;
            case MATCH_CHOICE_MENU -> handle_match_choice_menu(user_action);
            case MATCH_MENU -> handle_match_menu(user_action);
        };
    }

    UIAction handle_main_menu(UIAction user_action) {
        return switch(user_action.getUiActionEnum()) {
            default -> new NoAction(UIState.TEAM_MENU);
        };
    }

    UIAction handle_settings(UIAction user_action) {
        return switch(user_action.getUiActionEnum()) {
            //TODO
            default -> new NoAction(UIState.TEAM_MENU);
        };
    }

    UIAction handle_player_menu(UIAction user_action) {
        return switch(user_action.getUiActionEnum()) {
            //TODO
            default -> new NoAction(UIState.TEAM_MENU);
        };
    }

    UIAction handle_team_menu(UIAction user_action) {
        return switch(user_action.getUiActionEnum()) {
            default -> new NoAction(UIState.TEAM_MENU);
        };
    }

    UIAction handle_monster_menu(UIAction user_action) {
        return switch(user_action.getUiActionEnum()) {
            //TODO
            default -> new NoAction(UIState.TEAM_MENU);
        };
    }

    UIAction handle_match_choice_menu(UIAction user_action) {
        return switch(user_action.getUiActionEnum()) {
            //TODO
            default -> new NoAction(UIState.TEAM_MENU);
        };
    }

    UIAction handle_match_menu(UIAction user_action) {
        return switch(user_action.getUiActionEnum()) {
            //TODO
            default -> new NoAction(UIState.TEAM_MENU);
        };
    }
}
