package com.malog.esiea.monsters.view.console;

import com.malog.esiea.monsters.game.Team;
import com.malog.esiea.monsters.monsters.Monster;
import com.malog.esiea.monsters.view.UIState;
import com.malog.esiea.monsters.view.actions.team.monster.GoToReplaceAttackAction;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Utility class to manage user interactions via the standard console.
 * Provides methods for displaying menus and validating numeric inputs.
 */
public class ConsoleHelper {

    /**
     * Repeatedly prompts the user to select an option from a list until a valid choice is made.
     * @param scanner The Scanner instance used to read user input.
     * @param choices A list of {@link ConsoleChoice} objects available for selection.
     * @return The {@link ConsoleChoice} corresponding to the user's valid input.
     */
    public static ConsoleChoice selectAction(Scanner scanner, List<ConsoleChoice> choices) {
        ConsoleChoice final_choice = null;
        String user_input;

        // Loop until the user provides an input that matches a valid choice
        while (final_choice == null) {
            for (ConsoleChoice choice : choices) {
                System.out.println(choice.toString());
            }

            user_input = scanner.nextLine();

            // Validate that the input is a number before parsing
            if (isStringValidInt(user_input)) {
                int input = Integer.parseInt(user_input);
                final_choice = getChoiceOfInput(choices, input);
            }
        }
        return final_choice;
    }

    /**
     * Validates whether a string can be safely parsed into an integer.
     * Handles negative signs and empty/null strings.
     * @param str The string to evaluate.
     * @return true if the string represents a valid integer, false otherwise.
     */
    private static boolean isStringValidInt(String str) {
        if (str == null) {
            return false;
        }
        int length = str.length();
        if (length == 0) {
            return false;
        }
        int i = 0;
        // Check for a leading negative sign
        if (str.charAt(0) == '-') {
            if (length == 1) {
                return false;
            }
            i = 1;
        }
        // Check each character to ensure it is a digit
        for (; i < length; i++) {
            char c = str.charAt(i);
            if (c < '0' || c > '9') {
                return false;
            }
        }
        return true;
    }

    /**
     * Searches a list of choices for one that matches a specific ID/number.
     * @param choices The list of possible choices to search.
     * @param input The integer ID to look for.
     * @return The matching {@link ConsoleChoice}, or null if no match is found.
     */
    private static ConsoleChoice getChoiceOfInput(List<ConsoleChoice> choices, int input) {
        for (ConsoleChoice choice : choices) {
            if (choice.getOptionNumber() == input) {
                return choice;
            }
        }
        return null;
    }

    /**
     * Prompts the user to select a monster from their current team.
     * @param scanner The Scanner used for input.
     * @param message A message to display (e.g., "Select a monster to switch:").
     * @param team The player's current Team object.
     * @return The team index/ID of the selected monster.
     */
    public static int getMonsterTeamIdFromUser(Scanner scanner, String message, Team team) {
        System.out.println(message);
        List<ConsoleChoice> choices = new ArrayList<>();
        // Assumes a team size of 4
        for (int i = 1; i <= 4; i++) {
            choices.add(new ConsoleChoice(i, i + ". " + team.getMonster(i)));
        }
        return selectAction(scanner, choices).getOptionNumber();
    }

    /**
     * Prompts the user to select a monster from a generic map of monsters.
     * @param scanner The Scanner used for input.
     * @param message A custom header message.
     * @param monsters A Map where the key is the ID and the value is the monster's name.
     * @return The ID of the selected monster from the map.
     */
    public static int getMonsterMapIdFromUser(Scanner scanner, String message, Map<Integer, String> monsters) {
        List<ConsoleChoice> choices = new ArrayList<>();
        System.out.println(message);
        for (int i : monsters.keySet()) {
            choices.add(new ConsoleChoice(i, i + ". " + monsters.get(i)));
        }
        return selectAction(scanner, choices).getOptionNumber();
    }

    /**
     * Prompts the user to select a monster from a generic map of monsters.
     * @param scanner The Scanner used for input.
     * @param message A custom header message.
     * @param attacks A Map where the key is the ID and the value is the monster's name.
     * @return The ID of the selected monster from the map.
     */
    public static int getAttackMapIdFromUser(Scanner scanner, String message, Map<Integer, String> attacks) {
        List<ConsoleChoice> choices = new ArrayList<>();
        System.out.println(message);
        for (int i : attacks.keySet()) {
            choices.add(new ConsoleChoice(i, i + ". " + attacks.get(i)));
        }
        return selectAction(scanner, choices).getOptionNumber();
    }

    /**
     * Prompts the user to select an attack from a monster's available skill set.
     * @param scanner The Scanner used for input.
     * @param message A prompt message.
     * @param monster The Monster whose attacks are being listed.
     * @return The ID of the chosen attack.
     */
    public static int getAttackIdFromUser(Scanner scanner, String message, Monster monster) {
        List<ConsoleChoice> choices = new ArrayList<>();
        // Assumes monsters have 4 special attack slots
        for (int i = 1; i <= 4; i++) {
            choices.add(new ConsoleChoice(i, i + "." + monster.getSpecialAttack(i)));
        }
        System.out.println(message);
        return selectAction(scanner, choices).getOptionNumber();
    }
}
