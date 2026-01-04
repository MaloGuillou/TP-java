package com.malog.esiea.monsters.view.console;

import com.malog.esiea.monsters.view.backend_link.BackendLink;
import com.malog.esiea.monsters.view.UI;
import com.malog.esiea.monsters.view.UIState;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
    }

    @Override
    protected void renderMainMenu() {
        System.out.println("---Main Menu---");
        List<ConsoleChoice> choices = new ArrayList<>();
        choices.add(new ConsoleChoice(1, "Edit team"));
        choices.add(new ConsoleChoice(2, "Edit player"));
        choices.add(new ConsoleChoice(3,"Play Match"));
        choices.add(new ConsoleChoice(4,"Settings"));
        switch(ConsoleHelper.selectAction(choices).getOptionNumber()){
            case 1:
                this.currentState = UIState.TEAM_MENU;
                break;
            case 2:
                this.currentState = UIState.PLAYER_MENU;
                break;
            case 3:
                this.currentState = UIState.MATCH_CHOICE_MENU;
                break;
            case 4:
                this.currentState = UIState.SETTINGS;
                break;
        }
    }

    @Override
    protected void renderSettingsMenu() {
        System.out.println("---Settings Menu---");
        List<ConsoleChoice>  choices = new ArrayList<>();
        choices.add(new ConsoleChoice(-1, "Go back"));
        switch(ConsoleHelper.selectAction(choices).getOptionNumber()){
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
        switch(ConsoleHelper.selectAction(choices).getOptionNumber()){
            case -1:
                this.currentState = UIState.MAIN_MENU;
                break;
        }
    }

    @Override
    protected void renderTeamMenu() {
        System.out.println("---Team Menu---");
        System.out.println("Current team:");
        System.out.println(team);
        System.out.println("What do you want to do?");
        List<ConsoleChoice>  choices = new ArrayList<>();
        choices.add(new ConsoleChoice(-1, "Go back"));
        choices.add(new ConsoleChoice(1, "Modify a monster"));
        choices.add(new ConsoleChoice(2, "Replace a monster"));
        choices.add(new ConsoleChoice(3, "Move a monster"));
        switch(ConsoleHelper.selectAction(choices).getOptionNumber()){
            case -1:
                this.currentState = UIState.MAIN_MENU;
                break;
            case 2:
                renderModifyMonster();
                break;
            case 3:
                renderReplaceMonster();
                break;
            case 4:
                renderMoveMonster();
                break;
        }
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
        int result2 = ConsoleHelper.getMonsterMapIdFromUser(scanner, message, monsters);
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
        System.out.println("---Monster Menu---");
        System.out.println(this.team.getMonster(this.current_monster_team_index));
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

    private void renderModifyAttacks() {
        System.out.println("---Monster Menu---");
        System.out.println(this.team.getMonster(this.current_monster_team_index));
        int attackPos = ConsoleHelper.getAttackIdFromUser(scanner, "Which attacks do you want to replace?", this.team.getMonster(this.current_monster_team_index));
        int newAttackId = ConsoleHelper.getAttackMapIdFromUser(scanner, "Which attack do you want instead?", attacks);
        team = backendLink.modifyMonsterAttacks(current_monster_team_index, attackPos, newAttackId);
    }

    @Override
    protected void renderMatchSelectionMenu() {

    }

    @Override
    protected void renderMatchMenu() {

    }
}
