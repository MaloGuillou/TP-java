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
}
