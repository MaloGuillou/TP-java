package com.malog.esiea.monsters.view.backend_link;

import com.malog.esiea.monsters.game.Team;

import java.util.Map;
import java.util.UUID;

public abstract class BackendLink {
    protected final UUID id;

    protected BackendLink() {
        this.id = getId();
    }

    abstract UUID getId();
    public abstract void sendPseudo(String pseudo);
    public abstract Team getTeam();
    public abstract Map<Integer, String> getMonsters();
    public abstract Map<Integer, String> getAttacks();
    public abstract Team replaceMonster(int current_monster_team_index, int new_monster_id);
    public abstract Team modifyMonsterAttacks(int monster_team_index, int current_attack_index, int new_attack_id);
    public abstract Team moveMonster(int first_monster_team_index, int second_monster_team_index);
}
