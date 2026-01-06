package com.malog.esiea.monsters.game;

import com.malog.esiea.monsters.helpers.Constants;
import com.malog.esiea.monsters.monsters.Monster;
import com.malog.esiea.monsters.monsters.attacks.Attack;


public class Team {
    private final Monster[] monsters; // Use an array instead of a List
    private static final int TEAM_SIZE = Constants.team_size;

    public Team(){
        monsters = new Monster[TEAM_SIZE];
    }

    public void setMonster(int pos, Monster monster) {
        if(is_pos_valid(pos)){
            this.monsters[pos] = monster;
        }
    }

    public Monster getMonster(int pos){
        if(is_pos_valid(pos)){
            return monsters[pos];
        }
        return null;
    }

    public int get_team_size(){
        return TEAM_SIZE;
    }

    public void switch_monsters(int pos_1, int pos_2){
        if(is_pos_valid(pos_1) && is_pos_valid(pos_2)){
            Monster monster_1 = this.getMonster(pos_1);
            Monster monster_2 = this.getMonster(pos_2);

            this.setMonster(pos_2, monster_1);
            this.setMonster(pos_1, monster_2);
        }
    }

    public boolean all_monsters_ko(){
        for(Monster monster : monsters) {
            if (monster != null && !monster.is_ko()) {
                return false;
            }
        }
        return true;
    }

    public void change_monster_attack(int monster_pos, Attack new_attack, int old_attack_pos){
        Monster monster = this.getMonster(monster_pos);
        if(monster == null){
            return;
        }
        monster.change_attack(new_attack, old_attack_pos);
    }

    private boolean is_pos_valid(int pos){
        return pos >= 0 && pos < TEAM_SIZE;
    }

}
