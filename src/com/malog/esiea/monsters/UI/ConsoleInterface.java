package com.malog.esiea.monsters.UI;

import com.malog.esiea.monsters.game.Player;
import com.malog.esiea.monsters.game.Team;
import com.malog.esiea.monsters.game.event.*;
import com.malog.esiea.monsters.monsters.MonsterBuilder;

import java.util.List;
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
    public Team chooseTeam(Player player, List<MonsterBuilder> monsters){
        Team team = new Team();
        int team_index = 1;
        Scanner scanner = new Scanner(System.in);
        boolean building_team = true;
        while (building_team){
            System.out.println("Build your team:");
            System.out.println("Choose your monster n°" + team_index + ":");

            for(MonsterBuilder monster : monsters){
                System.out.println(monsters.indexOf(monster) + ". " + monster.toString());
            }
            String input = scanner.nextLine();
            if(Integer.parseInt(input) < monsters.size() && Integer.parseInt(input) >= 0){
                team.setMonster(team_index, monsters.get(Integer.parseInt(input)).build());
                team_index++;
                if(team_index >=7){
                    building_team = false;
                }
            }else{
                System.out.println("It doesn't seem to be a valid monster, please try again.");
            }
        }
        return null;
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
