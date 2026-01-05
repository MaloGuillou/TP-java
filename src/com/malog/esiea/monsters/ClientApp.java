package com.malog.esiea.monsters;

import com.malog.esiea.monsters.game.MatchesManager;
import com.malog.esiea.monsters.game.Player;
import com.malog.esiea.monsters.game.Team;
import com.malog.esiea.monsters.monsters.Monster;
import com.malog.esiea.monsters.monsters.MonsterBuilder;
import com.malog.esiea.monsters.monsters.attacks.Attack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

public class ClientApp {

    private final Player player;
    private final Map<Integer, MonsterBuilder> monsters;
    private final Map<Integer, Attack> attacks;
    private final MatchesManager matchesManager;


    public ClientApp(Map<Integer, MonsterBuilder> monsters, Map<Integer, Attack> attacks) {
        this.monsters = monsters;
        this.attacks = attacks;
        this.matchesManager = new MatchesManager();
        this.player = new Player("");
    }

    //Global
    public Map<Integer, String> getAllMonsters(){
        return this.monsters.entrySet().stream()
                .collect(toMap(Map.Entry::getKey, e -> String.valueOf(e.getValue())));
    }

    public Map<Integer, String> getAllAttacks(){
        return this.attacks.entrySet().stream()
                .collect(toMap(Map.Entry::getKey, e -> String.valueOf(e.getValue())));
    }


    //Player related
    public void setPlayerPseudo(String pseudo){
        this.player.setPseudo(pseudo);
    }

    public Team getTeam(){
        return this.player.get_team();
    }

    public Team replaceMonster(int current_monster_team_index, int new_monster_id){
        Monster new_monster = monsters.get(new_monster_id).build();
        this.player.get_team().setMonster(current_monster_team_index, new_monster);
        return this.player.get_team();
    }

    public Team modifyMonsterAttacks(int monster_team_index, int current_attack_index, int new_attack_id){
        Attack new_attack = attacks.get(new_attack_id);
        this.player.get_team().change_monster_attack(monster_team_index, new_attack, current_attack_index);
        return this.player.get_team();
    }

    public Team moveMonster(int first_monster_team_index, int second_monster_team_index) {
        this.player.get_team().switch_monsters(first_monster_team_index, second_monster_team_index);
        return this.player.get_team();
    }
}
