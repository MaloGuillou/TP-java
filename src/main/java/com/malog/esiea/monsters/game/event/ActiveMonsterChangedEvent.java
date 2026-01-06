package com.malog.esiea.monsters.game.event;

import com.malog.esiea.monsters.monsters.Monster;

public class ActiveMonsterChangedEvent extends Event {
    private final Monster monster;

    public ActiveMonsterChangedEvent(Monster monster){
        this.monster = monster;
    }

    @Override
    public String toString() {
        return monster.getName() + " is out";
    }
}
