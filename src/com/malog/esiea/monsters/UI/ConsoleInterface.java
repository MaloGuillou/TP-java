package com.malog.esiea.monsters.UI;

import com.malog.esiea.monsters.game.Player;
import com.malog.esiea.monsters.game.Team;
import com.malog.esiea.monsters.game.event.*;
import com.malog.esiea.monsters.monsters.Monster;
import com.malog.esiea.monsters.monsters.MonsterBuilder;
import com.malog.esiea.monsters.monsters.attacks.Attack;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ConsoleInterface extends UserInterface {

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
    public Team chooseTeam(Player player, Map<Integer, MonsterBuilder> monsters, List<Attack> attacks){
        Team team = new Team();
        int team_index = 1;
        Scanner scanner = new Scanner(System.in);
        boolean building_team = true;
        while (building_team){
            System.out.println("Build your team:");
            System.out.println("Choose your monster n°" + team_index + ":");

            for(Integer id : monsters.keySet()){
                System.out.println(monsters.get(id).toString());
            }
            String input = scanner.nextLine();
            if(!input.isEmpty() && monsters.containsKey(Integer.parseInt(input))){
                team.setMonster(team_index, monsters.get(Integer.parseInt(input)).build());
                team_index++;
                if(team_index >=7){
                    System.out.println("Is this team the one you want?");
                    for(int i = 1; i <= 6; i++){
                        System.out.println(i + ". " + team.getMonster(i).getName());
                    }
                    System.out.println("[Y/n]");
                    input = scanner.nextLine();
                    if(input.equalsIgnoreCase("Y") || input.isEmpty()){
                        building_team = false;
                    }else{
                        team_index = 1;
                    }
                }
            }else{
                System.out.println("It doesn't seem to be a valid monster, please try again.");
            }
        }
        System.out.println("Team created");
        System.out.println("Would you like to modify your monsters? [y/N]");
        String input = scanner.nextLine();
        if(input.equalsIgnoreCase("Y")){
            boolean modify_team = true;
            while(modify_team){
                System.out.println("Which monster do you want to modify?");
                for(int i = 1; i <= 6; i++){
                    System.out.println(i + ". " + team.getMonster(i).getName());
                }
                input = scanner.nextLine();
                if(!input.isEmpty() && Integer.parseInt(input) >= 1 && Integer.parseInt(input) <= 7){
                    int monster_index = Integer.parseInt(input);
                    Monster monster = team.getMonster(monster_index);
                    boolean modify_monster = true;
                    while(modify_monster) {
                        System.out.println("You've selected " + monster.getName());
                        System.out.println("What do you want to modify?");
                        for (int i = 1; i <= 4; i++) {
                            Attack attack = monster.getSpecialAttack(i);
                            if(attack == null){
                                System.out.println(i + ". none");
                            }else{
                                System.out.println(i + ". " + attack);
                            }
                        }
                        System.out.println("5. Do nothing and return to monster selection");
                        input = scanner.nextLine();
                        if(!input.isEmpty()){
                            int choice = Integer.parseInt(input);
                            switch (choice) {
                                case 1,2,3,4 : {
                                    System.out.println("Select the new attack");
                                    for(int i = 0; i < attacks.size(); i++){
                                        System.out.println(i + ". " + attacks.get(i));
                                    }
                                    input = scanner.nextLine();
                                    if(!input.isEmpty()){
                                        //TODO handle inputs and clean this shit
                                    }
                                }
                                case 5 :
                                    modify_monster = false;
                                    break;
                            }
                        }
                    }

                }
            }
        }
        return team;
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
