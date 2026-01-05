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
}
