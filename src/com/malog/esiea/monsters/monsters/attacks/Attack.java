package com.malog.esiea.monsters.monsters.attacks;

import com.malog.esiea.monsters.helpers.Randoms;
import com.malog.esiea.monsters.monsters.types.Type;

public class Attack {
    private final String name;
    private final Type type;
    private final int nb_use_max;
    private int nb_use_remaining;
    private final int attack_power;
    private final int miss_probability; // 0: never miss, 100: always miss

    public Attack(
            String name,
            Type type,
            int nb_use_max,
            int attack_power,
            int miss_probability
    ) {
        this.name = name;
        this.type = type;
        this.nb_use_max = nb_use_max;
        this.nb_use_remaining = nb_use_max;
        this.attack_power = attack_power;
        this.miss_probability = miss_probability;
    }

    public boolean is_same_type_as(Type type){
        return this.type.equals(type);
    }

    public void use_attack(){
        this.nb_use_remaining--;
    }

    public int getAttack_power(){
        return this.attack_power;
    }

    public int getNb_use_remaining(){
        return this.nb_use_remaining;
    }

    public int getNb_use_max(){
        return this.nb_use_max;
    }

    /**
     *
     * @return true = attack missed; false = attack did not miss
     */
    public boolean has_attack_miss(){
        if(this.miss_probability == 0){
            return false;
        }else if (this.miss_probability == 100) {
            return true;
        }
        int miss = Randoms.get_random_int_in_range(0, 100);
        return miss <= this.miss_probability;
        /*Example :
        this.miss_probability = 15, it will miss only when Randoms.get_random_int_in_range(0, 100) returns a number <= 15
        Assuming  Randoms.get_random_int_in_range() is truly random, it will happen 15 times out of 100
         */
    }

    public Type getType(){
        return this.type;
    }

    @Override
    public String toString(){
        return this.name;
    }
}
