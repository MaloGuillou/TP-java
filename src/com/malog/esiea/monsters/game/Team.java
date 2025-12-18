package com.malog.esiea.monsters.game;

import com.malog.esiea.monsters.monsters.Monster;
import com.malog.esiea.monsters.monsters.attacks.Attack;

public class Team {
    private Monster monster_1;
    private Monster monster_2;
    private Monster monster_3;
    private Monster monster_4;
    private Monster monster_5;
    private Monster monster_6;

    public Team(){
        monster_1 = null;
        monster_2 = null;
        monster_3 = null;
        monster_4 = null;
        monster_5 = null;
        monster_6 = null;
    }


    public void setMonster(int pos, Monster monster) {
        switch (pos){
            case 1:
                this.monster_1 = monster;
                break;
            case 2:
                this.monster_2 = monster;
                break;
            case 3:
                this.monster_3 = monster;
                break;
            case 4:
                this.monster_4 = monster;
                break;
            case 5:
                this.monster_5 = monster;
                break;
            case 6:
                this.monster_6 = monster;
                break;
            default:
                break;
        }
    }

    public Monster getMonster(int pos){
        return switch (pos) {
            case 1 -> monster_1;
            case 2 -> monster_2;
            case 3 -> monster_3;
            case 4 -> monster_4;
            case 5 -> monster_5;
            case 6 -> monster_6;
            default -> null;
        };
    }

    public void switch_monsters(int pos_1, int pos_2){
        Monster monster_1 = this.getMonster(pos_1);
        Monster monster_2 = this.getMonster(pos_2);

        if(monster_1 == null || monster_2 == null){
            return;
        }

        this.setMonster(pos_2, monster_1);
        this.setMonster(pos_1, monster_2);
    }

    public boolean all_monsters_ko(){
        return (this.monster_1 == null || this.monster_1.is_ko())
                && (this.monster_2 == null || this.monster_2.is_ko())
                && (this.monster_3 == null || this.monster_3.is_ko())
                && (this.monster_4 == null || this.monster_4.is_ko())
                && (this.monster_5 == null || this.monster_5.is_ko())
                && (this.monster_6 == null || this.monster_6.is_ko());
    }

    public void change_monster_attack(int monster_pos, Attack new_attack, int old_attack_pos){
        Monster monster = this.getMonster(monster_pos);
        if(monster == null){
            return;
        }
        monster.change_attack(new_attack, old_attack_pos);
    }

}
