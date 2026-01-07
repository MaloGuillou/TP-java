package com.malog.esiea.monsters.terrains;

import com.malog.esiea.monsters.game.event.Event;
import com.malog.esiea.monsters.game.event.StateEndedEvent;
import com.malog.esiea.monsters.game.event.TerrainStateEndedEvent;
import com.malog.esiea.monsters.monsters.Monster;
import com.malog.esiea.monsters.states.terrain.FloodedState;
import com.malog.esiea.monsters.states.terrain.TerrainState;

public class Terrain {
    private TerrainState state;

    public Terrain(){
        state = null;
    }

    public TerrainState getState(){
        return state;
    }

    public void setState(TerrainState state){
        this.state = state;
    }

    public void removeState(){
        this.state = null;
    }

    public Event monsterRemovedFromTerrain(Monster monster){
        Event event = null;
        if(this.state instanceof FloodedState floodedState){
            if(monster.equals(floodedState.getMonsterThatStartedIt())){
                event = new TerrainStateEndedEvent(floodedState, this);
                this.removeState();
            }
        }
        return event;
    }
}
