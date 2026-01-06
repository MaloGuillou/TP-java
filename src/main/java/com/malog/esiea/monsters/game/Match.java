package com.malog.esiea.monsters.game;

import com.malog.esiea.monsters.game.event.Event;
import com.malog.esiea.monsters.game.user_actions.AttackAction;
import com.malog.esiea.monsters.game.user_actions.ChangeMonsterAction;
import com.malog.esiea.monsters.game.user_actions.UserAction;
import com.malog.esiea.monsters.monsters.Monster;
import com.malog.esiea.monsters.terrains.Terrain;
import com.malog.esiea.monsters.view.backend_link.dto.MatchState;

import java.util.ArrayList;
import java.util.List;

public class Match {
    private final Player player_1;
    private final Player player_2;
    private final Terrain terrain;

    private List<Event> last_round_events;
    private UserAction player_1_action;
    private UserAction player_2_action;

    public Match(
            Player player1,
            Player player2
    ){
        this.player_1 = player1;
        this.player_2 = player2;
        terrain = new Terrain();
        last_round_events = new ArrayList<>();
        player_1_action = null;
        player_2_action = null;
    }

    public synchronized void set_player_action(UserAction action, Player player){
        if(player_1 == player){
            player_1_action = action;
        } else if (player_2 == player) {
            player_2_action = action;
        }else{
            throw new NullPointerException();
        }
        if(is_ready_to_play_round()){
            play_round();
            this.notifyAll();
        }
    }

    public synchronized List<Event> waitForRoundEvents() throws InterruptedException {
        while (!has_round_played()) {
            this.wait();
        }
        return getLast_round_events();
    }

    public boolean isMatchFinished(){
        return this.player_1.all_monsters_ko() || this.player_2.all_monsters_ko();
    }

    public Player winner(){
        if(this.player_1.all_monsters_ko()){
            if(this.player_2.all_monsters_ko()){
                return null;
            }else{
                return player_2;
            }
        }else{
            return player_1;
        }
    }

    private boolean is_ready_to_play_round(){
        return this.player_1_action != null && this.player_2_action != null;
    }

    /**
     * Check if the current round has played, only call this AFTER setting the player action with set_player_action(UserAction, Player);
     * @return true if the round has been played, false otherwise
     */
    public boolean has_round_played(){
        return this.player_1_action == null && this.player_2_action == null;
    }

    private void play_round(){

        last_round_events = new ArrayList<>();

        //Change active monster
        if(player_1_action instanceof ChangeMonsterAction){
           this.change_monster(player_1, (ChangeMonsterAction) player_1_action);
        }
        if(player_2_action instanceof ChangeMonsterAction){
            this.change_monster(player_2, (ChangeMonsterAction) player_2_action);
        }

        //Use object
        //TODO implement objects

        //Update states start
        Monster monster_1 = player_1.get_team().getMonster(player_1.get_active_monster_index());
        Monster monster_2 = player_2.get_team().getMonster(player_2.get_active_monster_index());
        monster_1.start_of_round(monster_2, terrain);
        monster_2.start_of_round(monster_1, terrain);

        //Attacks
        if(player_1_action instanceof AttackAction){
            if(player_2_action instanceof AttackAction){
                mutual_attack(player_1, player_2, (AttackAction) player_1_action, (AttackAction) player_2_action);
            }else{
                one_sided_attack(player_1, player_2, (AttackAction)  player_1_action);
            }
        }else if(player_2_action instanceof AttackAction){
            one_sided_attack(player_2, player_1, (AttackAction)  player_2_action);
        }
        player_1_action = null;
        player_2_action = null;


        //Update states end
        monster_1 = player_1.get_team().getMonster(player_1.get_active_monster_index());
        monster_2 = player_2.get_team().getMonster(player_2.get_active_monster_index());
        monster_1.end_of_round(monster_2, terrain);
        monster_2.end_of_round(monster_1, terrain);
    }

    private void one_sided_attack(Player attacker, Player defender, AttackAction attackAction){
        last_round_events.addAll(attackAction.execute(attacker, defender, terrain));
    }

    private void mutual_attack(Player player_1, Player player_2, AttackAction player_1_action, AttackAction player_2_action){
        if(player_1.get_active_monster().is_faster(player_2.get_active_monster())){
            attack(player_1, player_2, player_1_action, player_2_action);
        }else{
            attack(player_2, player_1, player_2_action, player_1_action);
        }
    }

    private void attack(Player first, Player second, AttackAction first_action, AttackAction second_action){

        last_round_events.addAll(first_action.execute(first, second, terrain));

        if(!second.get_active_monster().is_ko()){
            last_round_events.addAll(second_action.execute(second, first, terrain));
        }
    }

    private void change_monster(Player player, ChangeMonsterAction action){
        action.execute(player, null,  terrain);
    }

    public MatchState toMatchState(){
        return new MatchState(this.player_1, this.player_2, this.terrain);
    }

    public List<Event> getLast_round_events(){
        return last_round_events;
    }
}
