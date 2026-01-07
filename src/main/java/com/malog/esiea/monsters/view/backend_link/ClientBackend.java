package com.malog.esiea.monsters.view.backend_link;

import com.malog.esiea.monsters.ClientApp;
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

public class ClientBackend extends BackendLink{

    ClientApp backend;

    public ClientBackend(ClientApp backend) {
        this.backend = backend;
    }

    @Override
    protected UUID getId() {
        return backend.getId();
    }

    @Override
    public void sendPseudo(String pseudo) {
        backend.setPlayerPseudo(pseudo);
    }

    @Override
    public Team getTeam() {
        return backend.getTeam();
    }

    @Override
    public Map<Integer, String> getMonsters() {
        return backend.getAllMonsters();
    }

    @Override
    public Map<Integer, String> getAttacks() {
        return backend.getAllAttacks();
    }

    @Override
    public Team replaceMonster(int current_monster_team_index, int new_monster_id) {
        return backend.replaceMonster(current_monster_team_index, new_monster_id);
    }

    @Override
    public Team modifyMonsterAttacks(int monster_team_index, int current_attack_index, int new_attack_id) {
        return backend.modifyMonsterAttacks(monster_team_index, current_attack_index, new_attack_id);
    }

    @Override
    public Team moveMonster(int first_monster_team_index, int second_monster_team_index) {
        return backend.moveMonster(first_monster_team_index, second_monster_team_index);
    }

    @Override
    public Item[] getBackpack() {
        return backend.getBackpack();
    }

    @Override
    public Item[] sendUpdateBackpack(Item[] backpack) {
        return backend.updateBackpack(backpack);
    }

    @Override
    public void startAIMatch() {
        this.currentMatch = backend.startAIMatch();
    }

    @Override
    public boolean startMatchMaking() {
        return backend.startMatchMaking();
    }

    @Override
    public MatchState getMatchState() {
        return backend.getMatchState(currentMatch);
    }

    @Override
    public List<Event> sendUserAction(UserAction action) {
        return backend.handleUserAction(currentMatch, action);
    }

    @Override
    public boolean isMatchFinished() {
        return backend.isMatchFinished(currentMatch);
    }

    @Override
    public void changeActiveMonsterAfterKO(ChangeMonsterAction action){
        backend.changeActiveMonsterAfterKO(currentMatch, action);
    }

    @Override
    public void waitForAllKOReplacement() {
        backend.waitForAllKOReplacement(currentMatch);
    }

    @Override
    public void sendEndMatch(UUID currentMatch) {
        backend.endMatch(currentMatch);
    }

    @Override
    public boolean getWinner() {
        return backend.getWinner(currentMatch).equals(self_ID);
    }
}
