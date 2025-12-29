package com.malog.esiea.monsters.view.console;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ConsoleHelper {

    public static ConsoleChoice selectAction(List<ConsoleChoice> choices){
        ConsoleChoice final_choice = null;
        Scanner scanner = new Scanner(System.in);
        String user_input;
        while(final_choice == null){
            for(ConsoleChoice choice : choices){
                System.out.println(choice.toString());
            }
            user_input = scanner.nextLine();
            if(isStringValidInt(user_input)){
                int input = Integer.parseInt(user_input);
                final_choice = getChoiceOfInput(choices, input);
            }
        }
        return final_choice;
    }

    private static boolean isStringValidInt(String str){
        if (str == null) {
            return false;
        }
        int length = str.length();
        if (length == 0) {
            return false;
        }
        int i = 0;
        if (str.charAt(0) == '-') {
            if (length == 1) {
                return false;
            }
            i = 1;
        }
        for (; i < length; i++) {
            char c = str.charAt(i);
            if (c < '0' || c > '9') {
                return false;
            }
        }
        return true;
    }

    private static ConsoleChoice getChoiceOfInput(List<ConsoleChoice> choices, int input){
        for (ConsoleChoice choice : choices){
            if(choice.getOptionNumber() == input){
                return choice;
            }
        }
        return null;
    }

    public static int getMonsterTeamIdFromUser(Scanner scanner, String message) {
        int choice;
        int number = -1;
        String user_input;
        while (number == -1) {
            System.out.println(message);
            user_input = scanner.nextLine();
            if(isStringValidInt(user_input)) {
                choice = Integer.parseInt(user_input);
                switch (choice) {
                    case 1, 2, 3, 4, 5, 6 -> number = choice;
                    default -> System.out.println("Invalid choice");
                }
            }else{
                System.out.println("Invalid choice");
            }
        }
        return number;
    }

    public static int getMonsterMapIdFromUser(Scanner scanner, String message, Map<Integer, String> monsters) {
        int choice;
        System.out.println(message);
        for (int i : monsters.keySet()) {
            System.out.println(i + ". " + monsters.get(i));
        }
        choice = scanner.nextInt();
        int number = -1;
        while (number == -1) {
            System.out.println(message);
            for (int i : monsters.keySet()) {
                System.out.println(i + ". " + monsters.get(i));
            }
            if(monsters.containsKey(choice)) {
                number = choice;
            }else{
                System.out.println("Invalid choice");
            }
        }
        return number;
    }
}
