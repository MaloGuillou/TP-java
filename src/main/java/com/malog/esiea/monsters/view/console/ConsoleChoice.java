package com.malog.esiea.monsters.view.console;

public class ConsoleChoice {
    private final int option_number;
    private final String option;

    public ConsoleChoice(int option_number, String option) {
        this.option_number = option_number;
        this.option = option;
    }

    @Override
    public String toString() {
        return "  " + "\033[1;34m" + option_number + ".\033[0m " + option;
    }

    public int getOptionNumber(){
        return this.option_number;
    }
}
