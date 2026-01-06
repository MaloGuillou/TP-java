package com.malog.esiea.monsters.game;

import com.malog.esiea.monsters.game.event.ActiveMonsterChangedEvent;
import com.malog.esiea.monsters.game.event.Event;
import com.malog.esiea.monsters.helpers.Randoms;
import com.malog.esiea.monsters.monsters.Monster;
import com.malog.esiea.monsters.monsters.MonsterBuilder;
import com.malog.esiea.monsters.monsters.attacks.Attack;
import com.malog.esiea.monsters.monsters.types.Type;

import java.util.Map;
import java.util.UUID;

public class Player {
    private UUID id;
    private int active_monster; // 1-6
    private String pseudo;
    private Team team;

    public Player(String pseudo){
        this.id = UUID.randomUUID();
        this.pseudo = pseudo;
        this.active_monster = 0;
        this.team = new Team();
    }

    public void setPseudo(String pseudo){
        this.pseudo = pseudo;
    }

    public void set_team(Team team){
        this.team = team;
    }

    public Team get_team(){
        return this.team;
    }

    public Event change_active_monster(int new_active){
        if(new_active >= 0 && new_active < team.get_team_size()){
            this.active_monster = new_active;
        }
        return new ActiveMonsterChangedEvent(team.getMonster(new_active));
    }

    public boolean all_monsters_ko(){
        return this.team.all_monsters_ko();
    }

    public Monster get_active_monster(){
        return this.team.getMonster(this.active_monster);
    }

    public int  get_active_monster_index(){
        return this.active_monster;
    }

    public void randomTeam(Map<Integer, MonsterBuilder> monsters, Map<Integer, Attack> attacks) {
        for (int i = 0; i< 6; i++){
            Monster random_monster = monsters.get(Randoms.get_random_int_in_values(monsters.keySet().stream().toList())).build();
            for (int j = 0; j < 4; j++){
                Attack random_attack = attacks.get(Randoms.get_random_int_in_values(attacks.keySet().stream().toList()));
                while(random_attack.getType() != random_monster.getType() && random_attack.getType() != Type.NORMAL){
                    random_attack = attacks.get(Randoms.get_random_int_in_values(attacks.keySet().stream().toList()));
                }
                random_monster.change_attack(random_attack, j);
            }
            team.setMonster(i, random_monster);
        }
    }

    @Override
    public String toString(){
        return this.pseudo;
    }

    @Override
    public boolean equals(Object o){
        if(o instanceof Player){
            return this.id.equals(((Player) o).id);
        } else {
            return false;
        }
    }
}
