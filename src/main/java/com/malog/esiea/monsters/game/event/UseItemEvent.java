package com.malog.esiea.monsters.game.event;

import com.malog.esiea.monsters.game.Player;
import com.malog.esiea.monsters.items.Item;
import com.malog.esiea.monsters.monsters.Monster;

public class UseItemEvent extends Event{
    private final Player player;
    private final Monster monster;
    private final Item item;

    public UseItemEvent(Player player, Monster monster, Item item) {
        this.player = player;
        this.monster = monster;
        this.item = item;
    }

    @Override
    public String toString(){
        return this.player + " uses " + item + " on " + monster;
    }
}
