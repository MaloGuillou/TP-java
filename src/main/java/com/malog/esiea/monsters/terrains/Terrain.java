package com.malog.esiea.monsters.terrains;

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
}
