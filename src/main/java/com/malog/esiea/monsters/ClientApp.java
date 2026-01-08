package com.malog.esiea.monsters;

import com.malog.esiea.monsters.game.MatchesManager;
import com.malog.esiea.monsters.game.Player;
import com.malog.esiea.monsters.game.Team;
import com.malog.esiea.monsters.game.event.Event;
import com.malog.esiea.monsters.game.user_actions.AttackAction;
import com.malog.esiea.monsters.game.user_actions.ChangeMonsterAction;
import com.malog.esiea.monsters.game.user_actions.UserAction;
import com.malog.esiea.monsters.helpers.Randoms;
import com.malog.esiea.monsters.items.Item;
import com.malog.esiea.monsters.monsters.Monster;
import com.malog.esiea.monsters.monsters.MonsterBuilder;
import com.malog.esiea.monsters.monsters.attacks.Attack;
import com.malog.esiea.monsters.view.backend_link.dto.MatchState;

import java.util.*;

import static java.util.stream.Collectors.toMap;

public class ClientApp {

    private final Player player;
    private Player AI;
    private final Map<Integer, MonsterBuilder> monsters;
    private final Map<Integer, Attack> attacks;
    private final MatchesManager matchesManager;


    public ClientApp(Map<Integer, MonsterBuilder> monsters, Map<Integer, Attack> attacks) {
        this.monsters = monsters;
        this.attacks = attacks;
        this.matchesManager = new MatchesManager();
        this.player = new Player("");
        this.player.randomTeam(monsters, attacks);
    }

    public UUID getId(){
        return this.player.getId();
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

    public String getPlayerPseudo(){
        return this.player.getPseudo();
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


    //Match related
    public UUID startAIMatch() {
        AI = new Player("AI");
        AI.randomTeam(monsters, attacks);
        return matchesManager.new_match(player, AI);
    }

    public boolean startMatchMaking() {
        return false; // TODO;
    }

    public void endMatch(UUID matchId) {
        this.player.afterMatchReset();
        if(this.AI != null) {
            this.AI = null;
        }
        matchesManager.finishMatch(matchId);
    }

    public boolean isMatchFinished(UUID match_id) {
        return matchesManager.isMatchFinished(match_id);
    }

    public MatchState getMatchState(UUID match_id) {
        return matchesManager.getMatchState(match_id);
    }

    public List<Event> handleUserAction(UUID match_id, UserAction action) {
        if(AI != null) {
            matchesManager.setAiAction(match_id, AI, AIChooseAction());
        }
        List<Event> events =  matchesManager.setPlayerAction(match_id, player, action);
        if(AI != null) {
            if(!matchesManager.isMatchFinished(match_id) && AI.get_active_monster().getHP() <= 0) {
                for(int i = 0; i < AI.get_team().get_team_size(); i++ ) {
                    if(AI.get_team().getMonster(i) != null && AI.get_team().getMonster(i).getHP() > 0) {
                        this.changeAIActiveMonsterAfterKO(match_id, new ChangeMonsterAction(i));
                        return events;
                    }
                }
            }
        }
        return events;
    }

    private UserAction AIChooseAction() {
        UserAction action = null;
        while(action == null){
            int choice = Randoms.get_random_int_in_range(0, 5);
            switch(choice) {
                //Special attack
                case 0,1,2,3:
                    Attack attack = AI.get_active_monster().getSpecialAttack(choice);
                    if (attack != null && attack.getNb_use_remaining() > 0) {
                        action = new AttackAction(choice);
                    }
                    break;
                //Bare hands
                case 4:
                    action = new AttackAction();
                    break;
                //Change active
                case 5:
                    List<Integer> possible_monsters = new ArrayList<>();
                    for(int i = 0; i < AI.get_team().get_team_size(); i++){
                        if(AI.get_team().getMonster(i) != null && AI.get_team().getMonster(i).getHP() > 0 && i != AI.get_active_monster_index()){
                            possible_monsters.add(i);
                        }
                    }
                    if(!possible_monsters.isEmpty()){
                        int new_active = Randoms.get_random_int_in_values(possible_monsters);
                        action = new ChangeMonsterAction(new_active);
                    }
            }
        }
        return action;
    }

    public Item[] getBackpack() {
        return this.player.getBackpack();
    }

    public Item[] updateBackpack(Item[] backpack) {
        for(int i = 0; i < backpack.length; i++){
            player.setBackpackItem(i, backpack[i]);
        }
        return player.getBackpack();
    }

    public UUID getWinner(UUID matchId) {
        return matchesManager.getWinner(matchId);
    }

    public void changeActiveMonsterAfterKO(UUID matchId, ChangeMonsterAction action){
        matchesManager.changeActiveMonsterAfterKO(matchId, action, player);
    }

    public void changeAIActiveMonsterAfterKO(UUID matchId, ChangeMonsterAction action){
        matchesManager.changeActiveMonsterAfterKO(matchId, action, AI);
    }

    public void waitForAllKOReplacement(UUID matchId) {
        matchesManager.waitForAllKOReplacement(matchId);
    }

    public Team randomizeTeam() {
        this.player.randomTeam(monsters, attacks);
        return this.player.get_team();
    }
}
