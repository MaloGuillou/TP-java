package com.malog.esiea.monsters.items;

import com.malog.esiea.monsters.game.event.Event;
import com.malog.esiea.monsters.monsters.Monster;

public abstract class Item {
    public abstract Event use(Monster target);
}
