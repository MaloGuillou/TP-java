package com.malog.esiea.monsters;

import com.malog.esiea.monsters.view.console.ConsoleInterface;
import com.malog.esiea.monsters.view.actions.GoBackAction;
import com.malog.esiea.monsters.view.actions.GoToAction;
import com.malog.esiea.monsters.view.actions.NoAction;
import com.malog.esiea.monsters.view.actions.UIAction;
import com.malog.esiea.monsters.view.UIState;
import com.malog.esiea.monsters.view.UserInterface;
import com.malog.esiea.monsters.game.MatchesManager;
import com.malog.esiea.monsters.game.Player;
import com.malog.esiea.monsters.monsters.MonsterBuilder;
import com.malog.esiea.monsters.monsters.attacks.Attack;
import com.malog.esiea.monsters.view.actions.team.GoToModifyMonsterAction;
import com.malog.esiea.monsters.view.actions.team.GoToReplaceMonsterAction;
import com.malog.esiea.monsters.view.actions.team.ReplaceMonsterAction;
import com.malog.esiea.monsters.view.actions.team.monster.GoToModifyAttackMenu;
import com.malog.esiea.monsters.view.actions.team.monster.GoToReplaceAttackAction;
import com.malog.esiea.monsters.view.actions.team.monster.ReplaceAttackAction;

import java.util.List;
import java.util.Map;
import java.util.Stack;

public class ClientApp {

    private final Player player;
    private final UserInterface ui;
    private final Map<Integer, MonsterBuilder> monsters;
    private final List<Attack> attacks;
    private final MatchesManager matchesManager;

    private final Stack<GoToAction> GoBackActionStack;

    public ClientApp(Map<Integer, MonsterBuilder> monsters, List<Attack> attacks) {
        this.monsters = monsters;
        this.attacks = attacks;
        this.matchesManager = new MatchesManager();

        this.ui = new ConsoleInterface();

        String pseudo = ui.getPseudo();
        this.player = new Player(pseudo);

        this.GoBackActionStack = new Stack<>();
    }

    public void run(){
        UIAction user_action = new GoToAction(UIState.MAIN_MENU, UIState.MAIN_MENU);
        UIAction previousUIAction = user_action;
        boolean exit = false;
        while (!exit) {
            switch(user_action.getUiActionEnum()){
                case EXIT -> exit = true;
                case NO_ACTION -> user_action = previousUIAction;
                case GO_TO -> user_action = handle_go_to((GoToAction) user_action);
                case GO_BACK -> user_action= handle_go_back((GoBackAction) user_action);
                default -> {
                    previousUIAction =  user_action; // save previous action in case of NO_ACTION (= bugged input)
                    user_action = handle_action(user_action);
                }
            }
        }
    }

    UIAction handle_go_to(GoToAction user_action){
        GoBackActionStack.push(user_action);
        System.out.println(GoBackActionStack.stream().map(GoToAction::toString).toList());
        return go_to(user_action);
    }

    UIAction handle_go_back(GoBackAction user_action){
        GoToAction action = GoBackActionStack.peek();
        while(user_action.getUiState().equals(action.getDestination())){
            GoBackActionStack.pop();
            action = GoBackActionStack.peek();
        }
        return go_to(action);
    }

    UIAction go_to(GoToAction user_action){
        return switch(user_action.getDestination()){
            case MAIN_MENU -> ui.mainMenu();
            case SETTINGS -> ui.settingsMenu();
            case PLAYER_MENU -> ui.playerMenu();
            case TEAM_MENU -> ui.teamMenu(player.get_team());
            case REPLACE_MONSTER_MENU -> ui.replaceMonsterMenu(((GoToReplaceMonsterAction) user_action).getMonsterId(), monsters);
            case MODIFY_MONSTER_MENU -> ui.modifyMonsterMenu(player.get_team().getMonster(((GoToModifyMonsterAction) user_action).getMonsterId()));
            case MODIFY_ATTACK_MENU -> ui.modifyAttacksMenu(((GoToModifyAttackMenu) user_action).getMonster());
            case REPLACE_ATTACK_MENU -> ui.replaceAttacksMenu(attacks, ((GoToReplaceAttackAction) user_action).getMonster(), ((GoToReplaceAttackAction) user_action).getAttackId());
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
            case REPLACE_MONSTER_MENU -> handle_replace_monster_menu(user_action);
            case MODIFY_MONSTER_MENU -> handle_monster_menu(user_action);
            case MODIFY_ATTACK_MENU -> null;
            case REPLACE_ATTACK_MENU -> handle_replace_attack_menu(user_action);
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
            default -> new NoAction(UIState.TEAM_MENU);
        };
    }

    UIAction handle_team_menu(UIAction user_action) {
        return switch(user_action.getUiActionEnum()) {
            default -> new NoAction(UIState.TEAM_MENU);
        };
    }

    UIAction handle_replace_monster_menu(UIAction user_action) {
        return switch (user_action.getUiActionEnum()){
            case REPLACE_MONSTER -> {
                ReplaceMonsterAction replaceMonsterAction = (ReplaceMonsterAction) user_action;
                player.get_team().setMonster(replaceMonsterAction.getMonsterIndex(), replaceMonsterAction.getNewMonster());
                yield new GoBackAction(UIState.REPLACE_MONSTER_MENU);
            }
            default -> new NoAction(UIState.TEAM_MENU);
        };
    }

    UIAction handle_monster_menu(UIAction user_action) {
        return switch(user_action.getUiActionEnum()) {
            //TODO
            default -> new NoAction(UIState.TEAM_MENU);
        };
    }

    UIAction handle_replace_attack_menu(UIAction user_action) {
        return switch(user_action.getUiActionEnum()) {
            case REPLACE_ATTACK -> {
                ReplaceAttackAction action = (ReplaceAttackAction) user_action;
                action.get_monster().change_attack(action.getNew_attack(), action.get_attack_index());
                yield new GoBackAction(UIState.REPLACE_ATTACK_MENU);
            }
            default -> new NoAction(UIState.REPLACE_ATTACK_MENU);
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
