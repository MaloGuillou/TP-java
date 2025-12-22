package com.malog.esiea.monsters.ui;

import com.malog.esiea.monsters.monsters.MonsterBuilder;
import com.malog.esiea.monsters.ui.actions.*;
import com.malog.esiea.monsters.game.Team;
import com.malog.esiea.monsters.game.event.*;
import com.malog.esiea.monsters.monsters.Monster;
import com.malog.esiea.monsters.monsters.attacks.Attack;
import com.malog.esiea.monsters.ui.actions.team.*;
import com.malog.esiea.monsters.ui.actions.team.monster.GoToModifyAttackMenu;
import com.malog.esiea.monsters.ui.actions.team.monster.GoToReplaceAttackAction;
import com.malog.esiea.monsters.ui.actions.team.monster.ReplaceAttackAction;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ConsoleInterface extends UserInterface {

    private final Scanner scanner = new Scanner(System.in);

    public ConsoleInterface() {
    }

    @Override
    public String getPseudo(){
        Scanner scanner = new Scanner(System.in);
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

        return pseudo;
    }

    @Override
    public UIAction mainMenu() {
        System.out.println("---Main Menu---");
        System.out.println("1.Edit team");
        System.out.println("2.Edit player");
        System.out.println("3.Play Match");
        System.out.println("4.Settings");
        int choice = scanner.nextInt();
        return switch (choice) {
            case 1 -> new GoToAction(UIState.TEAM_MENU, UIState.MAIN_MENU);
            case 2 -> new GoToAction(UIState.PLAYER_MENU, UIState.MAIN_MENU);
            case 3 -> new GoToAction(UIState.MATCH_CHOICE_MENU, UIState.MAIN_MENU);
            case 4 -> new GoToAction(UIState.SETTINGS, UIState.MAIN_MENU);
            default -> new NoAction(UIState.MAIN_MENU);
        };
    }

    @Override
    public UIAction settingsMenu() { //TODO real options
        System.out.println("---Settings Menu---");
        System.out.println("-1.Go back");
        int choice = scanner.nextInt();
        return switch (choice) {
            case -1 -> new GoBackAction(UIState.SETTINGS);
            default -> new NoAction(UIState.SETTINGS);
        };
    }

    @Override
    public UIAction playerMenu() {
        System.out.println("---Player Menu---");
        System.out.println("-1.Go back");
        int choice = scanner.nextInt();
        return switch (choice) {
            case -1 -> new GoBackAction(UIState.PLAYER_MENU);
            default -> new NoAction(UIState.PLAYER_MENU);
        };
    }

    @Override
    public UIAction teamMenu(Team team) {
        System.out.println("---Team Menu---");
        System.out.println("Current team:");
        System.out.println(team);
        System.out.println("What do you want to do?");
        System.out.println("-1.Go back");
        System.out.println("1.Modify a monster");
        System.out.println("2.Replace a monster");
        System.out.println("3.Move a monster");
        int choice = scanner.nextInt();
        return switch (choice) {
            case 1 -> modifyWhichMonster(UIState.TEAM_MENU);
            case 2 -> replaceWhichMonster(UIState.TEAM_MENU);
            case 3 -> moveWhichMonster(UIState.TEAM_MENU);
            case -1 -> new GoBackAction(UIState.TEAM_MENU);
            default -> new NoAction(UIState.TEAM_MENU);
        };
    }

    private UIAction modifyWhichMonster(UIState location){
        String message = "Which monster do you want to modify?";
        int result = getMonsterTeamIdFromUser(message);
        return new GoToModifyMonsterAction(location, result);
    }

    private UIAction replaceWhichMonster(UIState location){
        String message = "Which monster do you want to replace?";
        int result = getMonsterTeamIdFromUser(message);
        return new GoToReplaceMonsterAction(location, result);
    }

    private UIAction moveWhichMonster(UIState location){
        String message = "Which monster do you want to move?";
        int result1 = getMonsterTeamIdFromUser(message);
        message = "With which other?";
        int result2 = getMonsterTeamIdFromUser(message);

        return new MoveMonstersAction(location, result1, result2);
    }

    private int getMonsterTeamIdFromUser(String message) {
        int choice;
        int result;
        System.out.println(message);
        choice = scanner.nextInt();
        int number = -1;
        while (number == -1) {
            switch (choice) {
                case 1, 2, 3, 4, 5, 6 -> {
                    number = choice;
                }
                default -> System.out.println("Invalid choice");
            }
        }
        result = number;
        return result;
    }

    @Override
    public UIAction replaceMonsterMenu(int monster_id, Map<Integer, MonsterBuilder> monsters) {
        System.out.println("---Team Menu---");
        System.out.println("Which monster do you want instead?");
        System.out.println("-1.Cancel and go back");
        for(int i : monsters.keySet()){
            System.out.println(monsters.get(i));
        }
        int choice = scanner.nextInt();
        if(monsters.containsKey(choice)){
            return new ReplaceMonsterAction(UIState.REPLACE_MONSTER_MENU, monster_id, monsters.get(choice).build());
        }else if (choice == -1) {
            return new GoBackAction(UIState.REPLACE_MONSTER_MENU);
        }else{
            return new NoAction(UIState.REPLACE_MONSTER_MENU);
        }
    }

    @Override
    public UIAction modifyMonsterMenu(Monster monster) {
        System.out.println("---Monster Menu---");
        System.out.println(monster);
        System.out.println("What do you want to do?");
        System.out.println("-1.Go back");
        System.out.println("1.Replace attacks");
        int choice = scanner.nextInt();
        return switch (choice) {
            case 1 -> new GoToModifyAttackMenu(UIState.MODIFY_MONSTER_MENU, monster);
            case -1 -> new GoBackAction(UIState.MODIFY_MONSTER_MENU);
            default -> new NoAction(UIState.MODIFY_MONSTER_MENU);
        };
    }

    @Override
    public UIAction modifyAttacksMenu(Monster monster) {
        System.out.println("---Monster Menu---");
        System.out.println(monster);
        System.out.println("Which attacks do you want to replace?");
        System.out.println("-1.Go back");
        for(int i = 1; i <= 4; i++){
            System.out.println(i + "." + monster.getSpecialAttack(i));
        }
        int choice = scanner.nextInt();
        return switch (choice){
            case 1,2,3,4 -> new GoToReplaceAttackAction(UIState.MODIFY_ATTACK_MENU, monster, choice);
            case -1 -> new GoBackAction(UIState.MODIFY_ATTACK_MENU);
            default -> new NoAction(UIState.MODIFY_ATTACK_MENU);
        };
    }

    @Override
    public UIAction replaceAttacksMenu(List<Attack> attacks, Monster monster, int attack_id){
        System.out.println("---Monster Menu---");
        System.out.println(monster);
        System.out.println("Which attack do you want instead?");
        System.out.println("-1.Cancel and go back");
        for(int i = 0; i < attacks.size(); i++){
            System.out.println(i + "." + attacks.get(i));
        }
        int choice = scanner.nextInt();
        if(choice >= 0 && choice <= attacks.size()){
            return new ReplaceAttackAction(UIState.REPLACE_ATTACK_MENU, attack_id, attacks.get(choice), monster);
        }else if (choice == -1){
            return new GoBackAction(UIState.REPLACE_ATTACK_MENU);
        }else{
            return new NoAction(UIState.REPLACE_ATTACK_MENU);
        }
    }

    @Override
    public void handleEvent(Event event) {
        switch (event) {
            case AttackLandedEvent attackLandedEvent -> System.out.println("test");
            case AttackMissedEvent attackMissedEvent -> System.out.println("test2");
            case NewStateAppliedEvent newStateAppliedEvent -> System.out.println("test3");
            case StateEndedEvent stateEndedEvent -> System.out.println("test4");
            case TerrainStateAppliedEvent terrainStateAppliedEvent -> System.out.println("test5");
            case TerrainStateEndedEvent terrainStateEndedEvent -> System.out.println("test6");
            case null, default -> System.out.println("Something happened but no idea what");
        }
    }

    @Override
    public void handleEvents(List<Event> events) {
        for (Event event : events) {
            handleEvent(event);
        }
    }
}
