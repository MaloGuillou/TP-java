package com.malog.esiea.monsters.view.console;

import com.malog.esiea.monsters.view.actions.UIAction;

public class ConsoleChoice {
    private final int option_number;
    private final String option;

    public ConsoleChoice(int option_number, String option) {
        this.option_number = option_number;
        this.option = option;
    }

    @Override
    public String toString() {
        return option_number + ". " + option;
    }

    public int getOptionNumber(){
        return this.option_number;
    }
}
