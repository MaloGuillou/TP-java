package com.malog.esiea.monsters.view.console;

import com.malog.esiea.monsters.game.Player;
import com.malog.esiea.monsters.game.event.*;
import com.malog.esiea.monsters.game.user_actions.AttackAction;
import com.malog.esiea.monsters.game.user_actions.ChangeMonsterAction;
import com.malog.esiea.monsters.game.user_actions.UseItemAction;
import com.malog.esiea.monsters.items.ItemType;
import com.malog.esiea.monsters.monsters.Monster;
import com.malog.esiea.monsters.monsters.attacks.Attack;
import com.malog.esiea.monsters.terrains.Terrain;
import com.malog.esiea.monsters.view.backend_link.BackendLink;
import com.malog.esiea.monsters.view.UI;
import com.malog.esiea.monsters.view.UIState;
import com.malog.esiea.monsters.view.backend_link.dto.MatchState;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TerminalUserInterface extends UI {

    private final Scanner scanner = new Scanner(System.in);

    private static final String RESET = "\033[0m";
    private static final String BLACK = "\033[0;30m";
    private static final String RED = "\033[0;31m";
    private static final String GREEN = "\033[0;32m";
    private static final String YELLOW = "\033[0;33m";
    private static final String BLUE = "\033[0;34m";
    private static final String PURPLE = "\033[0;35m";
    private static final String CYAN = "\033[0;36m";
    private static final String WHITE = "\033[0;37m";

    private static final String BLACK_BRIGHT = "\033[0;90m";
    private static final String RED_BRIGHT = "\033[0;91m";
    private static final String GREEN_BRIGHT = "\033[0;92m";
    private static final String YELLOW_BRIGHT = "\033[0;93m";
    private static final String BLUE_BRIGHT = "\033[0;94m";
    private static final String PURPLE_BRIGHT = "\033[0;95m";
    private static final String CYAN_BRIGHT = "\033[0;96m";
    private static final String WHITE_BRIGHT = "\033[0;97m";

    private static final String BOLD = "\033[1m";
    private static final String UNDERLINE = "\033[4m";

    private static final String BG_BLACK = "\033[40m";
    private static final String BG_RED = "\033[41m";
    private static final String BG_GREEN = "\033[42m";
    private static final String BG_YELLOW = "\033[43m";
    private static final String BG_BLUE = "\033[44m";
    private static final String BG_PURPLE = "\033[45m";
    private static final String BG_CYAN = "\033[46m";
    private static final String BG_WHITE = "\033[47m";

    public TerminalUserInterface(BackendLink backendLink) {
        super(backendLink);
    }

    private void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private void typeWrite(String text) {
        if (text == null) return;
        for (char c : text.toCharArray()) {
            System.out.print(c);
            try {
                Thread.sleep(15);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private void typeWriteln(String text) {
        if (text == null) return;
        for (char c : text.toCharArray()) {
            System.out.print(c);
            try {
                Thread.sleep(15);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println();
    }

    private void waitForInput() {
        System.out.println(System.lineSeparator() + "  " + BOLD + CYAN + ">> Press Enter to continue..." + RESET);
        scanner.nextLine();
    }

    @Override
    protected void quit() {
        clearConsole();
        typeWrite(BOLD + "Bye." + RESET);
        try {
            Thread.sleep(500);
        } catch (InterruptedException ignored) {}
    }

    @Override
    protected void renderInitMenu() {
        clearConsole();
        System.out.println(CYAN_BRIGHT + BOLD + "╔════════════════════════════════════════╗" + RESET);
        System.out.println(CYAN_BRIGHT + BOLD + "║       M O N S T E R   B A T T L E      ║" + RESET);
        System.out.println(CYAN_BRIGHT + BOLD + "╚════════════════════════════════════════╝" + RESET);
        typeWriteln(WHITE + "Initializing systems..." + RESET);

        String pseudo = "";
        boolean change_pseudo = true;
        while (change_pseudo){
            typeWrite(BOLD + "Enter your pseudo: " + RESET);
            pseudo = scanner.nextLine();
            if (pseudo.trim().isEmpty()) {
                typeWriteln(RED + "Pseudo cannot be empty!" + RESET);
                continue;
            }
            typeWrite("Is " + GREEN + BOLD + pseudo + RESET + " the pseudo you want to use? [Y/n]: ");
            String input = scanner.nextLine();
            if(input.equalsIgnoreCase("Y") || input.isEmpty()){
                change_pseudo = false;
            }
        }

        typeWriteln("\nWelcome, " + BLUE_BRIGHT + BOLD + pseudo + RESET + "!");
        waitForInput();

        backendLink.sendPseudo(pseudo);
        this.currentState = UIState.MAIN_MENU;
    }

    @Override
    protected void renderMainMenu() {
        clearConsole();
        System.out.println(CYAN_BRIGHT + BOLD + "╔════════════════════════════════════════╗" + RESET);
        System.out.println(CYAN_BRIGHT + BOLD + "║               MAIN MENU                ║" + RESET);
        System.out.println(CYAN_BRIGHT + BOLD + "╚════════════════════════════════════════╝" + RESET);
        List<ConsoleChoice> choices = new ArrayList<>();
        choices.add(new ConsoleChoice(1, "Edit Team"));
        choices.add(new ConsoleChoice(2, "Edit Backpack"));
        choices.add(new ConsoleChoice(3, "Edit Player"));
        choices.add(new ConsoleChoice(4, GREEN_BRIGHT + "Play Match" + RESET));
        choices.add(new ConsoleChoice(5, "Settings"));
        choices.add(new ConsoleChoice(6, RED_BRIGHT + "Exit" + RESET));
        
        System.out.println(WHITE + "Select an option:" + RESET);
        switch(ConsoleHelper.selectAction(scanner, choices).getOptionNumber()){
            case 1:
                this.currentState = UIState.TEAM_MENU;
                break;
            case 2:
                this.currentState = UIState.BACKPACK_MENU;
                break;
            case 3:
                this.currentState = UIState.PLAYER_MENU;
                break;
            case 4:
                this.currentState = UIState.MATCH_TYPE_CHOICE_MENU;
                break;
            case 5:
                this.currentState = UIState.SETTINGS;
                break;
            case 6:
                this.exit = true;
                break;
        }
    }

    @Override
    protected void renderSettingsMenu() {
        clearConsole();
        System.out.println(CYAN_BRIGHT + BOLD + "╔════════════════════════════════════════╗" + RESET);
        System.out.println(CYAN_BRIGHT + BOLD + "║             SETTINGS MENU              ║" + RESET);
        System.out.println(CYAN_BRIGHT + BOLD + "╚════════════════════════════════════════╝" + RESET);
        List<ConsoleChoice>  choices = new ArrayList<>();
        choices.add(new ConsoleChoice(-1, "Go back"));
        switch(ConsoleHelper.selectAction(scanner, choices).getOptionNumber()){
            case -1:
                this.currentState = UIState.MAIN_MENU;
                break;
        }
    }

    @Override
    protected void renderPlayerMenu() {
        clearConsole();
        System.out.println(CYAN_BRIGHT + BOLD + "╔════════════════════════════════════════╗" + RESET);
        System.out.println(CYAN_BRIGHT + BOLD + "║               PLAYER MENU              ║" + RESET);
        System.out.println(CYAN_BRIGHT + BOLD + "╚════════════════════════════════════════╝" + RESET);
        System.out.println("  Pseudo: " + BLUE_BRIGHT + BOLD + backendLink.getPseudo() + RESET);
        System.out.println(BLACK_BRIGHT + "  ────────────────────────────────────────" + RESET);
        List<ConsoleChoice>  choices = new ArrayList<>();
        choices.add(new ConsoleChoice(-1, "Go back"));
        choices.add(new ConsoleChoice(1, "Change Pseudo"));
        switch(ConsoleHelper.selectAction(scanner, choices).getOptionNumber()){
            case -1:
                this.currentState = UIState.MAIN_MENU;
                break;
            case 1:
                changePseudo();
                break;
        }
    }

    private void changePseudo() {
        String pseudo = "";
        boolean change_pseudo = true;
        while (change_pseudo){
            typeWrite(BOLD + "Enter your new pseudo: " + RESET);
            pseudo = scanner.nextLine();
            if (pseudo.trim().isEmpty()) {
                typeWriteln(RED + "Pseudo cannot be empty!" + RESET);
                continue;
            }
            change_pseudo = false;
        }
        backendLink.sendPseudo(pseudo);
    }

    @Override
    protected void renderTeamMenu() {
        clearConsole();
        displayTeamMenuHeader();
        System.out.println("What do you want to do?");
        List<ConsoleChoice>  choices = new ArrayList<>();
        choices.add(new ConsoleChoice(-1, "Go back"));
        choices.add(new ConsoleChoice(0, "Randomize team"));
        choices.add(new ConsoleChoice(1, "Modify a monster"));
        choices.add(new ConsoleChoice(2, "Replace a monster"));
        choices.add(new ConsoleChoice(3, "Move a monster"));
        switch(ConsoleHelper.selectAction(scanner, choices).getOptionNumber()){
            case -1:
                this.currentState = UIState.MAIN_MENU;
                break;
            case 0:
                this.team = this.backendLink.randomizeTeam();
                break;
            case 1:
                renderModifyMonster();
                break;
            case 2:
                renderReplaceMonster();
                break;
            case 3:
                renderMoveMonster();
                break;
        }
    }

    private void displayTeamMenuHeader(){
        System.out.println(CYAN_BRIGHT + BOLD + "╔════════════════════════════════════════╗" + RESET);
        System.out.println(CYAN_BRIGHT + BOLD + "║               TEAM MENU                ║" + RESET);
        System.out.println(CYAN_BRIGHT + BOLD + "╚════════════════════════════════════════╝" + RESET);
        System.out.println(WHITE + "Current team:" + RESET);
        for (int i = 0; i < team.get_team_size(); i++){
            Monster m = team.getMonster(i);
            String name = (m != null ? m.getName() : "...");
            String type = (m != null ? " [" + m.getType() + "]" : "");
            System.out.println("  " + BLUE_BRIGHT + "#" + i + RESET + " " + name + type);
        }
        System.out.println(BLACK_BRIGHT + "──────────────────────────────────────────" + RESET);
    }

    private void renderModifyMonster(){
        String message = "Which monster do you want to modify?";
        this.current_monster_team_index = ConsoleHelper.getMonsterTeamIdFromUser(scanner, message, team);
        this.currentState = UIState.MONSTER_MENU;
    }

    private void renderReplaceMonster(){
        String message = "Which monster do you want to replace?";
        int result1 = ConsoleHelper.getMonsterTeamIdFromUser(scanner, message, team);
        message = "Which monster do you want instead?";
        int result2 = ConsoleHelper.getItemMapIdFromUser(scanner, message, monsters);
        team = backendLink.replaceMonster(result1, result2);
    }

    private void renderMoveMonster(){
        String message = "Which monster do you want to move?";
        int result1 = ConsoleHelper.getMonsterTeamIdFromUser(scanner, message, team);
        message = "With which other?";
        int result2 = ConsoleHelper.getMonsterTeamIdFromUser(scanner, message, team);

        team = backendLink.moveMonster(result1, result2);
    }

    @Override
    protected void renderMonsterMenu() {
        clearConsole();
        displayMonsterMenuHeader(this.team.getMonster(this.current_monster_team_index));
        System.out.println("What do you want to do?");
        List<ConsoleChoice> choices = new ArrayList<>();
        choices.add(new ConsoleChoice(-1, "Go back"));
        choices.add(new ConsoleChoice(1, "Replace attacks"));
        switch (ConsoleHelper.selectAction(scanner, choices).getOptionNumber()) {
            case -1:
                this.currentState = UIState.TEAM_MENU;
                break;
            case 1:
                renderModifyAttacks();
                break;
        }
    }

    private void displayMonsterMenuHeader(Monster monster){
        System.out.println(CYAN_BRIGHT + BOLD + "╔════════════════════════════════════════╗" + RESET);
        System.out.println(CYAN_BRIGHT + BOLD + "║             MONSTER DETAILS            ║" + RESET);
        System.out.println(CYAN_BRIGHT + BOLD + "╚════════════════════════════════════════╝" + RESET);
        System.out.println("  " + BOLD + WHITE + monster.getName() + RESET + " [" + monster.getType().name() + "]");
        System.out.println("  HP: " + monster.getHP() + "/" + monster.getMaxHP());
        System.out.println("  Stats: ATK " + monster.getAttack() + " | DEF " + monster.getDefense() + " | SPD " + monster.getSpeed());
        System.out.println(BLACK_BRIGHT + "  ────────────────────────────────────────" + RESET);
        System.out.println("  Current Attacks:");
        Attack[] attacks = monster.getAttacks();
        for (int i = 0; i < attacks.length; i++){
            System.out.println("    " + BLUE_BRIGHT + "#" + i + RESET + " " + attacks[i].getName() + " (" + attacks[i].getType().name() + ") [" + attacks[i].getNb_use_max() + " PP]");
        }
        System.out.println(BLACK_BRIGHT + "──────────────────────────────────────────" + RESET);
    }

    private void renderModifyAttacks() {
        displayMonsterMenuHeader(this.team.getMonster(this.current_monster_team_index));
        int attackPos = ConsoleHelper.getAttackIdFromUser(scanner, "Which attacks do you want to replace?", this.team.getMonster(this.current_monster_team_index));
        int newAttackId = ConsoleHelper.getItemMapIdFromUser(scanner, "Which attack do you want instead?", attacks);
        team = backendLink.modifyMonsterAttacks(current_monster_team_index, attackPos, newAttackId);
    }

    @Override
    protected void renderBackpackMenu() {
        clearConsole();
        System.out.println(CYAN_BRIGHT + BOLD + "╔════════════════════════════════════════╗" + RESET);
        System.out.println(CYAN_BRIGHT + BOLD + "║             BACKPACK MENU              ║" + RESET);
        System.out.println(CYAN_BRIGHT + BOLD + "╚════════════════════════════════════════╝" + RESET);
        System.out.println(WHITE + "Current items:" + RESET);
        for (int i = 0; i < backpack.length; i++){
            System.out.println("  " + BLUE_BRIGHT + "#" + i + RESET + " " + (backpack[i] == null ? "Empty" : backpack[i].toString()));
        }
        System.out.println(BLACK_BRIGHT + "──────────────────────────────────────────" + RESET);
        System.out.println("What do you want to do?");
        List<ConsoleChoice> choices = new ArrayList<>();
        choices.add(new ConsoleChoice(-1, "Go back"));
        choices.add(new ConsoleChoice(1, "Change an item"));
        switch (ConsoleHelper.selectAction(scanner, choices).getOptionNumber()) {
            case -1:
                this.currentState = UIState.MAIN_MENU;
                break;
            case 1:
                renderChangeBackpackMenu();
                break;
        }
    }

    private void renderChangeBackpackMenu(){
        clearConsole();
        System.out.println(CYAN_BRIGHT + BOLD + "╔════════════════════════════════════════╗" + RESET);
        System.out.println(CYAN_BRIGHT + BOLD + "║             CHANGE ITEM                ║" + RESET);
        System.out.println(CYAN_BRIGHT + BOLD + "╚════════════════════════════════════════╝" + RESET);
        System.out.println(WHITE + "Which slot do you want to change?" + RESET);
        List<ConsoleChoice> choices = new ArrayList<>();
        choices.add(new ConsoleChoice(-1, "Cancel and go back"));
        for (int i = 0; i < backpack.length; i++){
            choices.add(new ConsoleChoice(i, "Slot #" + i + " [" + (backpack[i] == null ? "Empty" : backpack[i].toString()) + "]"));
        }
        int backpack_slot = ConsoleHelper.selectAction(scanner, choices).getOptionNumber();
        if(backpack_slot == -1){
            return;
        }

        System.out.println(WHITE + "Select a new item:" + RESET);
        choices = new ArrayList<>();
        choices.add(new ConsoleChoice(-1, "Cancel and go back"));

        ItemType[] types = ItemType.values();
        for (int i = 0; i < types.length; i++) {
            choices.add(new ConsoleChoice(i, types[i].name()));
        }
        int selected_item = ConsoleHelper.selectAction(scanner, choices).getOptionNumber();
        if(selected_item == -1){
            return;
        }

        this.backpack[backpack_slot] = types[selected_item].build();
        this.backpack = this.backendLink.sendUpdateBackpack(backpack);
    }

    @Override
    protected void renderMatchSelectionMenu() {
        clearConsole();
        System.out.println(CYAN_BRIGHT + BOLD + "╔════════════════════════════════════════╗" + RESET);
        System.out.println(CYAN_BRIGHT + BOLD + "║             MATCH SELECTION            ║" + RESET);
        System.out.println(CYAN_BRIGHT + BOLD + "╚════════════════════════════════════════╝" + RESET);
        System.out.println(WHITE + "What do you want to do?" + RESET);
        List<ConsoleChoice> choices = new ArrayList<>();
        choices.add(new ConsoleChoice(-1, "Go back"));
        choices.add(new ConsoleChoice(1, "Matchmaking"));
        choices.add(new ConsoleChoice(2, "Against AI"));

        switch (ConsoleHelper.selectAction(scanner, choices).getOptionNumber()) {
            case -1:
                this.currentState = UIState.MAIN_MENU;
                break;
            case 1:
                if(backendLink.startMatchMaking()){
                    this.currentState = UIState.MATCHMAKING_MENU;
                }else{
                    System.out.println(RED_BRIGHT + "!!! Matchmaking failed, check internet connection !!!" + RESET);
                    waitForInput();
                }
                break;
            case 2:
                backendLink.startAIMatch();
                this.currentState = UIState.MATCH_MENU;
                break;
        }
    }

    //TODO matchmaking
    @Override
    protected void renderMatchMakingMenu() {
        clearConsole();
        System.out.println(CYAN_BRIGHT + BOLD + "╔════════════════════════════════════════╗" + RESET);
        System.out.println(CYAN_BRIGHT + BOLD + "║               MATCHMAKING              ║" + RESET);
        System.out.println(CYAN_BRIGHT + BOLD + "╚════════════════════════════════════════╝" + RESET);

        int i = 0;
        while (!backendLink.isOpponentFound()) {
            clearConsole();
            System.out.println(CYAN_BRIGHT + BOLD + "╔════════════════════════════════════════╗" + RESET);
            System.out.println(CYAN_BRIGHT + BOLD + "║               MATCHMAKING              ║" + RESET);
            System.out.println(CYAN_BRIGHT + BOLD + "╚════════════════════════════════════════╝" + RESET);
            i = ++i % 4;
            System.out.print("\n  " + WHITE + "Searching for opponents" + RESET);
            for (int j = 0; j < i; j++){
                System.out.print(".");
            }
            System.out.println("\n\n  " + BLACK_BRIGHT + "Waiting for opponent..." + RESET);
            try {
                Thread.sleep(500);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        System.out.println(GREEN_BRIGHT + BOLD + "\n  Opponent found! Match starting..." + RESET);
        try { Thread.sleep(1000); } catch (InterruptedException ignored) {}
        this.currentState = UIState.MATCH_MENU;
    }

    @Override
    protected void renderMatchMenu() {
        clearConsole();
        display_match_menu_header();

        System.out.println(WHITE + "  What do you want to do?" + RESET);
        List<ConsoleChoice> choices = new ArrayList<>();
        choices.add(new ConsoleChoice(1, "Attack"));
        choices.add(new ConsoleChoice(2, "Change Monster"));
        choices.add(new ConsoleChoice(3, "Use Item"));

        switch (ConsoleHelper.selectAction(scanner, choices).getOptionNumber()) {
            case 1:
                this.currentState = UIState.MATCH_ATTACK_CHOICE_MENU;
                break;
            case 2:
                this.currentState = UIState.MATCH_CHANGE_MONSTER_MENU;
                break;
            case 3:
                this.currentState = UIState.MATCH_ITEM_CHOICE_MENU;
                break;
            default:
                break;
        }

    }
    private void display_match_menu_header() {
        MatchState state = backendLink.getMatchState();
        Player player_1 = state.getPlayer_1();

        //Update data
        this.team = player_1.get_team();
        this.current_monster_team_index = player_1.get_active_monster_index();
        this.backpack = player_1.getBackpack();

        Player player_2 = state.getPlayer_2();
        Terrain terrain = state.getTerrain();

        System.out.println(CYAN_BRIGHT + BOLD + "╔══════════════════════════════════════════════════════════════╗" + RESET);
        System.out.println(CYAN_BRIGHT + BOLD + "║                        BATTLE ARENA                          ║" + RESET);
        System.out.println(CYAN_BRIGHT + BOLD + "╚══════════════════════════════════════════════════════════════╝" + RESET);

        // Display Enemy (Player 2)
        display_player_state(player_2, true);

        System.out.println(BLACK_BRIGHT + "────────────────────────────────────────────────────────────────" + RESET);
        display_terrain_state(terrain);
        System.out.println(BLACK_BRIGHT + "────────────────────────────────────────────────────────────────" + RESET);

        // Display Self (Player 1)
        display_player_state(player_1, false);
    }

    private void display_player_state(Player player, boolean isOpponent) {
        Monster m = player.get_active_monster();
        String stateStr = m.get_current_state() == null ? "" : " " + PURPLE_BRIGHT + "[" + m.get_current_state().toString() + "]" + RESET;

        // Health Bar visualization (Dynamic Colors)
        int hp = m.getHP();
        int maxHp = m.getMaxHP();
        double ratio = (double) hp / maxHp;
        int healthPercent = (int) (ratio * 20); // 0 to 20 scale for more precision

        String color = GREEN_BRIGHT;
        if (ratio <= 0.5) color = YELLOW_BRIGHT;
        if (ratio <= 0.2) color = RED_BRIGHT;

        String bar = color + "█".repeat(Math.max(0, healthPercent)) + BLACK_BRIGHT + "░".repeat(Math.max(0, 20 - healthPercent)) + RESET;

        String nameColor = isOpponent ? RED_BRIGHT : BLUE_BRIGHT;
        String prefix = isOpponent ? "  [OPPONENT] " : "  [YOU]      ";

        System.out.println(prefix + nameColor + BOLD + player.getPseudo() + RESET + " sent " + WHITE_BRIGHT + BOLD + m.getName() + RESET + stateStr);
        System.out.println("             HP: " + bar + " " + BOLD + hp + RESET + "/" + maxHp);
    }

    private void display_terrain_state(Terrain terrain) {
        if(terrain.getState() != null){
            System.out.println("  " + YELLOW_BRIGHT + BOLD + " ! ENVIRONMENT: " + RESET + " " + PURPLE + " " + terrain.getState().toString().toUpperCase() + " " + RESET);
        } else {
            System.out.println("  " + WHITE + "  ENVIRONMENT: " + RESET + CYAN + "Clear" + RESET);
        }
    }

    @Override
    protected void renderMatchAttackChoiceMenu() {
        clearConsole();
        display_match_menu_header();

        System.out.println(WHITE + "  Choose an attack:" + RESET);
        List<ConsoleChoice> choices = new ArrayList<>();
        choices.add(new ConsoleChoice(-1, RED_BRIGHT + "Go back" + RESET));
        choices.add(new ConsoleChoice(5, YELLOW_BRIGHT + "Attack bare hands" + RESET));
        Attack[] attacks = team.getMonster(current_monster_team_index).getAttacks();
        for(int i = 0; i < attacks.length; i++){
            String attack_details = attacks[i].getName() + " [" + attacks[i].getType() + "] (" + attacks[i].getNb_use_remaining() + "/" + attacks[i].getNb_use_max() + " PP)";
            choices.add(new ConsoleChoice(i, attack_details));
        }
        int choice = ConsoleHelper.selectAction(scanner, choices).getOptionNumber();
        switch (choice) {
            case -1:
                this.currentState = UIState.MATCH_MENU;
                break;
            case 0,1,2,3:
                if(attacks[choice].getNb_use_remaining() <= 0){
                    System.out.println(RED_BRIGHT + "!!! No PP left for this attack !!!" + RESET);
                    waitForInput();
                    return;
                }else {
                    System.out.println(BLACK_BRIGHT + "--- Waiting for opponent ---" + RESET);
                    events = backendLink.sendUserAction(new AttackAction(choice));
                    this.currentState = UIState.MATCH_EVENT_DISPLAY_MENU;
                }
                break;
            case 5:
                System.out.println(BLACK_BRIGHT + "--- Waiting for opponent ---" + RESET);
                events = backendLink.sendUserAction(new AttackAction());
                this.currentState = UIState.MATCH_EVENT_DISPLAY_MENU;
                break;
        }
    }

    @Override
    protected void renderMatchItemChoiceMenu() {
        clearConsole();
        display_match_menu_header();

        System.out.println(WHITE + "  Which item would you like to use?" + RESET);
        List<ConsoleChoice> choices = new ArrayList<>();
        choices.add(new ConsoleChoice(-1, RED_BRIGHT + "Go back" + RESET));
        for (int i = 0; i < backpack.length; i++){
            if(backpack[i] != null){
                choices.add(new ConsoleChoice(i, backpack[i].toString()));
            }
        }
        int backpack_slot =  ConsoleHelper.selectAction(scanner, choices).getOptionNumber();
        if(backpack_slot == -1){
            this.currentState = UIState.MATCH_MENU;
            return;
        }

        int monster_position = ConsoleHelper.getAliveMonsterTeamIdWithGoBackFromUser(scanner, WHITE + "  On which monster?" + RESET, team);
        if(monster_position == -1){
            this.currentState = UIState.MATCH_MENU;
            return;
        }

        System.out.println(BLACK_BRIGHT + "--- Waiting for opponent ---" + RESET);
        events = this.backendLink.sendUserAction(new UseItemAction(backpack_slot, monster_position));

        this.currentState = UIState.MATCH_EVENT_DISPLAY_MENU;
    }

    @Override
    protected void renderMatchChangeMonsterMenu() {
        clearConsole();
        display_match_menu_header();

        int choice = ConsoleHelper.getAliveMonsterTeamIdWithoutActiveWithGoBackFromUser(scanner, WHITE + "  Select a monster to switch to:" + RESET, team, current_monster_team_index);
        if(choice == -1){
            this.currentState = UIState.MATCH_MENU;
            return;
        }

        System.out.println(BLACK_BRIGHT + "--- Waiting for opponent ---" + RESET);
        events = backendLink.sendUserAction(new ChangeMonsterAction(choice));

        this.currentState = UIState.MATCH_EVENT_DISPLAY_MENU;
    }

    @Override
    protected void renderMatchEventDisplayMenu() {
        clearConsole();
        display_match_menu_header();

        boolean monster_ko = false;

        System.out.println(BOLD + "\n  " + BG_WHITE + BLACK + " BATTLE LOG " + RESET);
        System.out.println(BLACK_BRIGHT + "  ────────────────────────────────────────────────────────────────" + RESET);
        for (Event e : events) {
            String prefix = "  > ";
            String color = WHITE;

            if(e instanceof MonsterKOEvent){
                monster_ko = true;
                prefix = "  /!\\ ";
                color = RED_BRIGHT;
            }

            typeWriteln(color + prefix + e.toString() + RESET);
            waitForInput();
        }
        System.out.println(BLACK_BRIGHT + "  ────────────────────────────────────────────────────────────────\n" + RESET);

        //check if match finito
        if (backendLink.isMatchFinished()){
            this.currentState = UIState.MATCH_RESULT_MENU;
            return;
        }

        MatchState state = backendLink.getMatchState();
        Player player_1 = state.getPlayer_1();
        this.team = player_1.get_team();
        this.current_monster_team_index = player_1.get_active_monster_index();

        if(monster_ko){
            //check if current monster is ko and should be changed
            if(team.getMonster(current_monster_team_index).getHP() <= 0){
                this.currentState = UIState.MATCH_CHANGE_MONSTER_FORCED_MENU;
                return;
            }
            this.backendLink.waitForAllKOReplacement();

            state = backendLink.getMatchState();
            player_1 = state.getPlayer_1();
            this.team = player_1.get_team();
            this.current_monster_team_index = player_1.get_active_monster_index();
        }

        this.currentState = UIState.MATCH_MENU;
    }

    @Override
    protected void renderMatchChangeMonsterForcedMenu() {
        clearConsole();
        System.out.println(CYAN_BRIGHT + BOLD + "╔════════════════════════════════════════╗" + RESET);
        System.out.println(RED_BRIGHT + BOLD + "║             MONSTER FAINTED!           ║" + RESET);
        System.out.println(CYAN_BRIGHT + BOLD + "╚════════════════════════════════════════╝" + RESET);

        int choice = ConsoleHelper.getAliveMonsterTeamIdFromUser(scanner, WHITE + "  Your current monster is KO, select another one:" + RESET, team);
        this.current_monster_team_index = choice;
        this.backendLink.changeActiveMonsterAfterKO(new ChangeMonsterAction(choice));

        this.currentState = UIState.MATCH_MENU;
    }

    @Override
    protected void renderMatchResultMenu() {
        clearConsole();
        System.out.println(CYAN_BRIGHT + BOLD + "╔════════════════════════════════════════╗" + RESET);
        System.out.println(CYAN_BRIGHT + BOLD + "║              MATCH RESULT              ║" + RESET);
        System.out.println(CYAN_BRIGHT + BOLD + "╚════════════════════════════════════════╝" + RESET);
        
        if(backendLink.getWinner()){
            typeWrite("\n" + GREEN_BRIGHT + BOLD + "    CONGRATULATIONS! YOU WON THE MATCH! " + RESET + "\n");
        }else{
            typeWrite("\n" + RED_BRIGHT + BOLD + "    GAME OVER. YOU LOST THE MATCH. " + RESET + "\n");
        }
        waitForInput();

        backendLink.endMatch();
        this.currentState = UIState.MAIN_MENU;
    }
}