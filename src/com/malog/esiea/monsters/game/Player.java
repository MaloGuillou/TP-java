package com.malog.esiea.monsters.game;

import com.malog.esiea.monsters.monsters.Monster;
import com.malog.esiea.monsters.monsters.attacks.Attack;

public class Player {
    private int active_monster; // 1-6
    private final String pseudo;
    private Team team;

    public Player(String pseudo){
        this.pseudo = pseudo;
        this.active_monster = 1;
        this.team = new Team();
    }

    public void set_team(Team team){
        this.team = team;
    }

    public Team get_team(){
        return this.team;
    }

    public void change_active_monster(int new_active){
        if(new_active >= 1 && new_active <= 6){
            this.active_monster = new_active;
        }
    }

    public boolean all_monsters_ko(){
        return this.team.all_monsters_ko();
    }

    public Monster get_active_monster(){
        return this.team.getMonster(this.active_monster);
    }

    @Override
    public String toString(){
        return this.pseudo;
    }

    public void change_monster_attack(int monster_pos, Attack new_attack, int old_attack_pos){
        this.team.change_monster_attack(monster_pos, new_attack, old_attack_pos);
    }

}
