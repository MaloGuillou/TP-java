package com.malog.esiea.monsters.game;

import com.malog.esiea.monsters.game.event.Event;
import com.malog.esiea.monsters.game.user_actions.UserAction;
import com.malog.esiea.monsters.view.backend_link.dto.MatchState;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class MatchesManager {

    private final HashMap<UUID, Match> matches;

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

    public void finishMatch(UUID matchId){
        System.out.println("Match finished");
        this.matches.remove(matchId);
    }

    public MatchState getMatchState(UUID matchId) {
        Match match = matches.get(matchId);
        return match.toMatchState();
    }

    public List<Event> setPlayerAction(UUID matchId, Player player, UserAction action){
        Match match = matches.get(matchId);
        match.set_player_action(action, player);
        try {
            while(!match.has_round_played()){
                    wait(10);
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return match.getLast_round_events();
    }

    public void setAiAction(UUID matchId, Player player, UserAction action){
        Match match = matches.get(matchId);
        match.set_player_action(action, player);
    }

    public boolean isMatchFinished(UUID match_id){
        Match match = matches.get(match_id);
        System.out.println("Is match finito? " + match.isMatchFinished());
        return match.isMatchFinished();
    }
}
