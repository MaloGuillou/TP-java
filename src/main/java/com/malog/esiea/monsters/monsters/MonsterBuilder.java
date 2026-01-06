package com.malog.esiea.monsters.monsters;

import com.malog.esiea.monsters.helpers.Randoms;
import com.malog.esiea.monsters.monsters.types.stats.TypeStats;
import com.malog.esiea.monsters.states.State;

public class MonsterBuilder {
    private final int id;
    private final String name;
    private final TypeStats type;

    private final int hp_max;
    private final int hp_min;
    private final int attack_max;
    private final int attack_min;
    private final int defense_max;
    private final int defense_min;
    private final int speed_max;
    private final int speed_min;

    public MonsterBuilder(
            int id,
            String name,
            TypeStats type,
            int hp_max,
            int hp_min,
            int attack_max,
            int attack_min,
            int defense_max,
            int defense_min,
            int speed_max,
            int speed_min
    ) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.hp_max = hp_max;
        this.hp_min = hp_min;
        this.attack_max = attack_max;
        this.attack_min = attack_min;
        this.defense_max = defense_max;
        this.defense_min = defense_min;
        this.speed_max = speed_max;
        this.speed_min = speed_min;
    }

    public Monster build(){
        int hp = Randoms.get_random_int_in_range(this.hp_min, this.hp_max);
        int attack = Randoms.get_random_int_in_range(this.attack_min, this.attack_max);
        int defense =  Randoms.get_random_int_in_range(this.defense_min, this.defense_max);
        int speed = Randoms.get_random_int_in_range(this.speed_min, this.speed_max);

        return new Monster(
                this.name,
                this.type,
                hp,
                attack,
                defense,
                speed
        );
    }

    @Override
    public String toString() {
        return String.format("%d. %s", this.id, this.name);
    }

    public int getId() {
        return this.id;
    }
}
