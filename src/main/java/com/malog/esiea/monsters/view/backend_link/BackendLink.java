package com.malog.esiea.monsters.view.backend_link;

import com.malog.esiea.monsters.game.Player;
import com.malog.esiea.monsters.game.Team;
import com.malog.esiea.monsters.game.event.Event;
import com.malog.esiea.monsters.game.user_actions.ChangeMonsterAction;
import com.malog.esiea.monsters.game.user_actions.UserAction;
import com.malog.esiea.monsters.items.Item;
import com.malog.esiea.monsters.view.backend_link.dto.MatchState;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public abstract class BackendLink {
    protected UUID currentMatch = null;
    protected UUID self_ID;

    //Init
    protected BackendLink() {}
    public void Init(){
        this.self_ID = getId();
    }

    protected abstract UUID getId();

    //Player
    public abstract void sendPseudo(String pseudo);
    public abstract Team getTeam();

    //Data
    public abstract Map<Integer, String> getMonsters();
    public abstract Map<Integer, String> getAttacks();

    //Team modification
    public abstract Team replaceMonster(int current_monster_team_index, int new_monster_id);
    public abstract Team modifyMonsterAttacks(int monster_team_index, int current_attack_index, int new_attack_id);
    public abstract Team moveMonster(int first_monster_team_index, int second_monster_team_index);

    //Backpack
    public abstract Item[] getBackpack();
    public abstract Item[] sendUpdateBackpack(Item[] backpack);

    //Match start
    public abstract void startAIMatch();
    public abstract boolean startMatchMaking();

    //In match
    public abstract MatchState getMatchState();
    public abstract List<Event> sendUserAction(UserAction action);
    public abstract boolean isMatchFinished();

    public abstract void changeActiveMonsterAfterKO(ChangeMonsterAction action);

    public abstract void waitForAllKOReplacement();
    protected abstract void sendEndMatch(UUID currentMatch);
    public abstract boolean getWinner();

    public void endMatch(){
        sendEndMatch(currentMatch);
        currentMatch = null;
    }

    //Online
    public boolean isOpponentFound(){
        return currentMatch != null;
    }

}
