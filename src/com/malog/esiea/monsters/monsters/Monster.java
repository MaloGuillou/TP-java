package com.malog.esiea.monsters.monsters;

import com.malog.esiea.monsters.game.event.AttackLandedEvent;
import com.malog.esiea.monsters.game.event.AttackMissedEvent;
import com.malog.esiea.monsters.game.event.Event;
import com.malog.esiea.monsters.helpers.AttackHelper;
import com.malog.esiea.monsters.states.State;
import com.malog.esiea.monsters.monsters.attacks.Attack;
import com.malog.esiea.monsters.monsters.types.Type;
import com.malog.esiea.monsters.monsters.types.stats.TypeStats;
import com.malog.esiea.monsters.terrains.Terrain;

import java.util.ArrayList;

public class Monster {
    private final String name;
    private final TypeStats type;

    private final int hp_max;
    private final int attack;
    private final int defense;
    private final int speed;

    private int hp;
    private State state;

    private Attack attack_1;
    private Attack attack_2;
    private Attack attack_3;
    private Attack attack_4;

    private ArrayList<Class<? extends State>> previous_states;

    public Monster(
            String name,
            TypeStats type,
            int hp_max,
            int attack,
            int defense,
            int speed) {
        this.name = name;
        this.type = type;

        this.hp_max = hp_max;
        this.attack = attack;
        this.defense = defense;
        this.speed = speed;

        this.hp = hp_max;

        this.attack_1 = null;
        this.attack_2 = null;
        this.attack_3 = null;
        this.attack_4 = null;

        this.previous_states = new ArrayList<>();
    }

    public void apply_damage(int damage){
        this.hp -= damage;
    }

    public void apply_state(State state){
        this.state = state;
        this.previous_states.add(state.getClass());
    }

    public State get_current_state(){
        return this.state;
    }

    public boolean previously_had_state(Class<? extends State> state){
        return this.previous_states.contains(state);
    }

    public void remove_state(){
        this.state = null;
    }

    public boolean is_ko(){
        return this.hp <= 0;
    }

    public Event attack_bare_hands(Monster opponent){
        AttackLandedEvent attackLandedEvent = AttackHelper.get_bare_hands_damage(this, opponent);
        opponent.apply_damage(attackLandedEvent.getDamage());
        return attackLandedEvent;
    }

    public ArrayList<Event> use_attack(Monster opponent, Terrain terrain, int attack_number){
        ArrayList<Event> events = new ArrayList<>();
        Attack attack = switch (attack_number) {
            case 1 -> attack_1;
            case 2 -> attack_2;
            case 3 -> attack_3;
            case 4 -> attack_4;
            default -> throw new AssertionError();
        };
        if(attack.has_attack_miss()){
            events.add(new AttackMissedEvent(
                    opponent,
                    attack
            ));
            return events;
        }
        attack.use_attack();
        AttackLandedEvent attackLandedEvent = AttackHelper.get_attack_damage(
                this,
                attack,
                opponent
        );
        opponent.apply_damage(attackLandedEvent.getDamage());
        events.add(attackLandedEvent);
        if(attack.is_same_type_as(this.type.getType())){
            ArrayList<Event> special_events = this.type.special_trigger(this, opponent, terrain);
            events.addAll(special_events);
        }

        return events;
    }

    public int getAttack(){
        return this.attack;
    }

    public int getDefense(){
        return this.defense;
    }

    public Type getType(){
        return this.type.getType();
    }

    public Event update_state(Monster opponent, Terrain terrain){
        return this.state.update_state(this, opponent, terrain);
    }

    public boolean is_faster(Monster opponent){
        return this.speed > opponent.speed;
    }

    @Override
    public String toString(){
        return this.name;
    }

    public void change_attack(Attack new_attack, int old_attack_pos) {
        switch(old_attack_pos){
            case 1:
                this.attack_1 = new_attack;
                break;
            case 2:
                this.attack_2 = new_attack;
                break;
            case 3:
                this.attack_3 = new_attack;
                break;
            case 4:
                this.attack_4 = new_attack;
                break;
            default:
                break;
        }
    }

    public void heal(int heal){
        this.hp += heal;
        if(this.hp >  this.hp_max){
            this.hp = this.hp_max;
        }
    }

    public int getMaxHP(){
        return this.hp_max;
    }

    public String getName(){
        return this.name;
    }

    public Attack getSpecialAttack(int pos){
        return switch (pos) {
            case 1 -> attack_1;
            case 2 -> attack_2;
            case 3 -> attack_3;
            case 4 -> attack_4;
            default -> throw new AssertionError();
        };
    }
}
