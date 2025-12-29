package com.malog.esiea.monsters.view;

import java.util.HexFormat;

public class Selectable {
    private final String title;
    private final HexFormat color;
    private final int id;

    public Selectable(String title, HexFormat color, int id) {
        this.title = title;
        this.color = color;
        this.id = id;
    }
}
