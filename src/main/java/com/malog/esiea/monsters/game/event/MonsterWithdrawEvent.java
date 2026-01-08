package com.malog.esiea.monsters.game.event;

import com.malog.esiea.monsters.game.Player;
import com.malog.esiea.monsters.monsters.Monster;

public class MonsterWithdrawEvent extends Event{
    private final Player player;
    private final Monster monster;

    public MonsterWithdrawEvent(Player player, Monster monster) {
        this.player = player;
        this.monster = monster;
    }

    @Override
    public String toString(){
        return this.player + " is calling back " + monster.getName();
    }
}
