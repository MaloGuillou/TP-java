package com.malog.esiea.monsters.game;

import com.malog.esiea.monsters.game.event.Event;
import com.malog.esiea.monsters.game.user_actions.AttackAction;
import com.malog.esiea.monsters.game.user_actions.UserAction;
import com.malog.esiea.monsters.terrains.Terrain;

import java.util.ArrayList;

public class Match {
    private final Player player_1;
    private final Player player_2;
    private final Terrain terrain;

    public Match(
            Player player1,
            Player player2
    ){
        this.player_1 = player1;
        this.player_2 = player2;
        terrain = new Terrain();
    }

    public boolean is_match_over(){
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

    public ArrayList<Event> play_round(
            UserAction player_1_action,
            UserAction player_2_action
    ){

        ArrayList<Event> events = new ArrayList<>();

        //TODO implement other user actions

        if(player_1_action instanceof AttackAction){
            if(player_2_action instanceof AttackAction){
                if(this.player_1.get_active_monster().is_faster(player_2.get_active_monster())){
                    events.addAll(this.attack(this.player_1, this.player_2, (AttackAction) player_1_action, (AttackAction) player_2_action));
                }else{
                    events.addAll(this.attack(this.player_2, this.player_1, (AttackAction) player_2_action, (AttackAction) player_1_action));
                }
            }else{

            }
        }

        return events;

    }

    private ArrayList<Event> attack(Player first, Player second, AttackAction first_action, AttackAction second_action){
        ArrayList<Event> events = new ArrayList<>();

        events.addAll(first_action.execute(first, second, terrain));

        if(! player_1.get_active_monster().is_ko()){
            events.addAll(second_action.execute(second, first, terrain));
        }

        return events;
    }
}
