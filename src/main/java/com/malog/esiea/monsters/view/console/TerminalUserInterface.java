package com.malog.esiea.monsters.view.console;

import com.malog.esiea.monsters.game.Player;
import com.malog.esiea.monsters.game.event.Event;
import com.malog.esiea.monsters.game.event.MonsterKOEvent;
import com.malog.esiea.monsters.game.user_actions.AttackAction;
import com.malog.esiea.monsters.game.user_actions.ChangeMonsterAction;
import com.malog.esiea.monsters.game.user_actions.UseItemAction;
import com.malog.esiea.monsters.helpers.Randoms;
import com.malog.esiea.monsters.items.Item;
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
import java.util.UUID;

public class TerminalUserInterface extends UI {

    private final Scanner scanner = new Scanner(System.in);

    public TerminalUserInterface(BackendLink backendLink) {
        super(backendLink);
    }

    @Override
    protected void renderInitMenu() {
        System.out.println("Init...");

        String pseudo = "";
        boolean change_pseudo = true;
        while (change_pseudo){
            System.out.println("Enter your pseudo:");
            pseudo = scanner.nextLine();
            System.out.println("Is it the pseudo you want to use : " + pseudo + " ? [Y/n]");
            String input = scanner.nextLine();
            if(input.equalsIgnoreCase("Y") || input.isEmpty()){
                change_pseudo = false;
            }
        }

        System.out.println("Hi " + pseudo);

        backendLink.sendPseudo(pseudo);
        this.currentState = UIState.MAIN_MENU;
    }

    @Override
    protected void renderMainMenu() {
        System.out.println("---Main Menu---");
        List<ConsoleChoice> choices = new ArrayList<>();
        choices.add(new ConsoleChoice(1, "Edit team"));
        choices.add(new ConsoleChoice(2, "Edit backpack"));
        choices.add(new ConsoleChoice(3, "Edit player"));
        choices.add(new ConsoleChoice(4,"Play Match"));
        choices.add(new ConsoleChoice(5,"Settings"));
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
        }
    }

    @Override
    protected void renderSettingsMenu() {
        System.out.println("---Settings Menu---");
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
        System.out.println("---Player Menu---");
        List<ConsoleChoice>  choices = new ArrayList<>();
        choices.add(new ConsoleChoice(-1, "Go back"));
        switch(ConsoleHelper.selectAction(scanner, choices).getOptionNumber()){
            case -1:
                this.currentState = UIState.MAIN_MENU;
                break;
        }
    }

    @Override
    protected void renderTeamMenu() {
        displayTeamMenuHeader();
        System.out.println("What do you want to do?");
        List<ConsoleChoice>  choices = new ArrayList<>();
        choices.add(new ConsoleChoice(-1, "Go back"));
        choices.add(new ConsoleChoice(1, "Modify a monster"));
        choices.add(new ConsoleChoice(2, "Replace a monster"));
        choices.add(new ConsoleChoice(3, "Move a monster"));
        switch(ConsoleHelper.selectAction(scanner, choices).getOptionNumber()){
            case -1:
                this.currentState = UIState.MAIN_MENU;
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
        System.out.println("---Team Menu---");
        System.out.println("----------");
        System.out.println("Current team:");
        for (int i = 0; i < team.get_team_size(); i++){
            System.out.println("[#" + i + "] "  + (team.getMonster(i) != null ? team.getMonster(i).getName(): "..."));
        }
        System.out.println("----------");
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
        System.out.println("---Monster Menu---");
        System.out.println("Name: " + monster.getName());
        System.out.println("Type: " + monster.getType().name());
        System.out.println("----------");
        System.out.println("Current attacks: ");
        Attack[] attacks = monster.getAttacks();
        for (int i = 0; i < attacks.length; i++){
            System.out.println("[#" + i + "] " + attacks[i].getName() + " (" + attacks[i].getType().name() + ") - " + attacks[i].getNb_use_max() + " PP");
        }
        System.out.println("----------");
        System.out.println("Stats: ");
        System.out.println("HP: " + monster.getMaxHP());
        System.out.println("attack: " + monster.getAttack());
        System.out.println("defense: " + monster.getDefense());
        System.out.println("speed: " + monster.getSpeed());
        System.out.println("----------");
    }

    private void renderModifyAttacks() {
        displayMonsterMenuHeader(this.team.getMonster(this.current_monster_team_index));
        int attackPos = ConsoleHelper.getAttackIdFromUser(scanner, "Which attacks do you want to replace?", this.team.getMonster(this.current_monster_team_index));
        int newAttackId = ConsoleHelper.getItemMapIdFromUser(scanner, "Which attack do you want instead?", attacks);
        team = backendLink.modifyMonsterAttacks(current_monster_team_index, attackPos, newAttackId);
    }

    @Override
    protected void renderBackpackMenu() {
        System.out.println("---Backpack Menu---");
        System.out.println("----------");
        System.out.println("Current items: ");
        for (int i = 0; i < backpack.length; i++){
            System.out.println("[#" + i + "] " + (backpack[i] == null ? "..." : backpack[i].toString()));
        }
        System.out.println("----------");
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
        System.out.println("Which one do you want to change? ");
        List<ConsoleChoice> choices = new ArrayList<>();
        choices.add(new ConsoleChoice(-1, "Cancel and go back"));
        for (int i = 0; i < backpack.length; i++){
            choices.add(new ConsoleChoice(i, backpack[i] == null ? "..." : backpack[i].toString()));
        }
        int backpack_slot = ConsoleHelper.selectAction(scanner, choices).getOptionNumber();
        if(backpack_slot == -1){
            return;
        }

        System.out.println("Which one do you want instead? ");
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
        System.out.println("---Match Selection Menu---");
        System.out.println("What do you want to do?");
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
                    System.out.println("!!!Matchmaking failed, check internet connection!!!");
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
        System.out.println("---Match Making Menu---");

        int i = 0;
        while (!backendLink.isOpponentFound()) {
            i = ++i %4;
            System.out.print("Searching for opponents");
            for (int j =0; j < i; j++){
                System.out.print(".");
            }
            System.out.println();
            System.out.println("Press Esc to cancel");
            try {
                Thread.sleep(500);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void renderMatchMenu() {
        display_match_menu_header();

        List<ConsoleChoice> choices = new ArrayList<>();
        choices.add(new ConsoleChoice(1, "Attack"));
        choices.add(new ConsoleChoice(2, "Change monster"));
        choices.add(new ConsoleChoice(3, "Use object"));

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
        System.out.println("---Match Menu---");
        MatchState state = backendLink.getMatchState();
        Player player_1 = state.getPlayer_1();

        //Update data
        this.team = player_1.get_team();
        this.current_monster_team_index = player_1.get_active_monster_index();
        this.backpack = player_1.getBackpack();

        Player player_2 = state.getPlayer_2();
        Terrain terrain = state.getTerrain();

        display_player_state(player_1);
        System.out.println("\n---\n");
        display_player_state(player_2);
        System.out.println("\n---\n");
        display_terrain_state(terrain);
        System.out.println("\n---\n");
    }

    private void display_player_state(Player player) {
        System.out.println(player.toString());
        Monster active_monster_player = player.get_active_monster();
        System.out.println(active_monster_player);
        System.out.println(active_monster_player.getHP());
        System.out.println(active_monster_player.get_current_state() == null ? "" : active_monster_player.get_current_state().toString());
    }

    private void display_terrain_state(Terrain terrain) {
        if(terrain.getState() != null){
            System.out.println("Terrain is under state : " + terrain.getState().toString());
        }
    }

    @Override
    protected void renderMatchAttackChoiceMenu() {
        display_match_menu_header();

        List<ConsoleChoice> choices = new ArrayList<>();
        choices.add(new ConsoleChoice(-1, "Go back"));
        choices.add(new ConsoleChoice(5, "Attack bare hands"));
        Attack[] attacks = team.getMonster(current_monster_team_index).getAttacks();
        for(int i = 0; i < attacks.length; i++){
            String attack_details = attacks[i].toString() + " " + attacks[i].getType() + " " + attacks[i].getNb_use_remaining() + "/" + attacks[i].getNb_use_max();
            choices.add(new ConsoleChoice(i, attack_details));
        }
        int choice = ConsoleHelper.selectAction(scanner, choices).getOptionNumber();
        switch (choice) {
            case -1:
                this.currentState = UIState.MATCH_MENU;
                break;
            case 0,1,2,3:
                if(attacks[choice].getNb_use_remaining() <= 0){
                    System.out.println("!!! Can't use this attack anymore !!!");
                    return;
                }else {
                    System.out.println("---Waiting for opponent---");
                    events = backendLink.sendUserAction(new AttackAction(choice));
                    this.currentState = UIState.MATCH_EVENT_DISPLAY_MENU;
                }
                break;
            case 5:
                System.out.println("---Waiting for opponent---");
                events = backendLink.sendUserAction(new AttackAction());
                this.currentState = UIState.MATCH_EVENT_DISPLAY_MENU;
                break;
        }
    }

    @Override
    protected void renderMatchItemChoiceMenu() {
        display_match_menu_header();

        System.out.println("----------");
        System.out.println("Which item would you like to use?");
        List<ConsoleChoice> choices = new ArrayList<>();
        choices.add(new ConsoleChoice(-1, "Go back"));
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


        int monster_position = ConsoleHelper.getAliveMonsterTeamIdWithGoBackFromUser(scanner, "On which monster do you want to use it? ", team);
        if(monster_position == -1){
            this.currentState = UIState.MATCH_MENU;
            return;
        }

        System.out.println("---Waiting for opponent---");
        events = this.backendLink.sendUserAction(new UseItemAction(backpack_slot, monster_position));

        this.currentState = UIState.MATCH_EVENT_DISPLAY_MENU;
    }

    @Override
    protected void renderMatchChangeMonsterMenu() {
        display_match_menu_header();

        int choice = ConsoleHelper.getAliveMonsterTeamIdWithoutActiveWithGoBackFromUser(scanner, "Select the monster you want", team, current_monster_team_index);
        if(choice == -1){
            this.currentState = UIState.MATCH_MENU;
            return;
        }

        this.current_monster_team_index = choice;
        System.out.println("---Waiting for opponent---");
        events = backendLink.sendUserAction(new ChangeMonsterAction(choice));

        this.currentState = UIState.MATCH_EVENT_DISPLAY_MENU;
    }

    @Override
    protected void renderMatchEventDisplayMenu() {
        boolean monster_ko = false;
        for (Event e : events) {
            System.out.println(e.toString());
            if(e instanceof MonsterKOEvent){
                monster_ko = true;
            }
        }
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
        System.out.println("---Change Monster Menu---");

        int choice = ConsoleHelper.getAliveMonsterTeamIdFromUser(scanner, "Your current monster is ko, select another one: ", team);
        this.current_monster_team_index = choice;
        this.backendLink.changeActiveMonsterAfterKO(new ChangeMonsterAction(choice));

        this.currentState = UIState.MATCH_MENU;
    }

    @Override
    protected void renderMatchResultMenu() {
        System.out.println("---Match Result---");
        if(backendLink.getWinner()){
            System.out.println("You won!");
        }else{
            System.out.println("You lost!");
        }
        System.out.println("\n\n\n\npress enter to continue");
        scanner.nextLine();

        backendLink.endMatch();
        this.currentState = UIState.MAIN_MENU;
    }
}