package com.malog.esiea.monsters.view.backend_link;

import com.malog.esiea.monsters.ClientApp;
import com.malog.esiea.monsters.game.Team;

import java.util.Map;
import java.util.UUID;

public class ClientBackend extends BackendLink{

    ClientApp backend;

    public ClientBackend(ClientApp backend) {
        this.backend = backend;
    }

    @Override
    UUID getId() {
        return null;
    }

    @Override
    public void sendPseudo(String pseudo) {

    }

    @Override
    public Team getTeam() {
        return null;
    }

    @Override
    public Map<Integer, String> getMonsters() {
        return Map.of();
    }

    @Override
    public Map<Integer, String> getAttacks() {
        return Map.of();
    }

    @Override
    public Team replaceMonster(int current_monster_team_index, int new_monster_id) {
        return null;
    }

    @Override
    public Team modifyMonsterAttacks(int monster_team_index, int current_attack_index, int new_attack_id) {
        return null;
    }

    @Override
    public Team moveMonster(int first_monster_team_index, int second_monster_team_index) {
        return null;
    }
}
