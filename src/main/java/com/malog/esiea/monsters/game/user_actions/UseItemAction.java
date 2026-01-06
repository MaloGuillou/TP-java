package com.malog.esiea.monsters.game.user_actions;

import com.malog.esiea.monsters.game.Player;
import com.malog.esiea.monsters.game.event.Event;
import com.malog.esiea.monsters.terrains.Terrain;

import java.util.ArrayList;
import java.util.List;

public class UseItemAction extends UserAction{
    private final int backpack_item_id;
    private final int monster_target_id;

    public UseItemAction(int backpack_item_id, int monster_target_id){
        this.backpack_item_id = backpack_item_id;
        this.monster_target_id = monster_target_id;
    }

    @Override
    public List<Event> execute(Player attacker, Player defender, Terrain terrain) {
        List<Event> events = new ArrayList<>();
        events.add(attacker.getBackpack()[backpack_item_id].use(attacker.get_team().getMonster(monster_target_id)));
        attacker.removeFromBackpack(backpack_item_id);
        return List.of();
    }
}
