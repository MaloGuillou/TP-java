package com.malog.esiea.monsters.game;

import com.malog.esiea.monsters.game.event.Event;
import com.malog.esiea.monsters.game.user_actions.UserAction;
import com.malog.esiea.monsters.monsters.MonsterBuilder;
import com.malog.esiea.monsters.monsters.attacks.Attack;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class MatchesManager {

    private HashMap<UUID, Match> matches;

    public MatchesManager(
    ){
        matches = new HashMap<>();
    }

    public UUID new_match(Player player_1, Player player_2){
        Match match = new Match(player_1, player_2);
        UUID match_id = UUID.randomUUID();
        this.matches.put(match_id, match);
        return match_id;
    }

    public List<Event> play_round(UUID match_id, UserAction player_1_action, UserAction player_2_action){
        return this.matches.get(match_id).play_round(player_1_action, player_2_action);
    }
}
