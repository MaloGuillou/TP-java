package com.malog.esiea.monsters.game.event;

import com.malog.esiea.monsters.monsters.Monster;

public class ParalysisCauseFailedAttackEvent extends Event{
    private final Monster stunned_monster;

    public ParalysisCauseFailedAttackEvent(Monster stunedMonster) {
        stunned_monster = stunedMonster;
    }

    @Override
    public String toString(){
        return stunned_monster.getName() + " is stunned and couldn't launch it's attack";
    }
}
