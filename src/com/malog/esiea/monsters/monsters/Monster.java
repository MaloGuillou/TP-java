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
import java.util.List;

public class Monster {
    private final String name;
    private final TypeStats type;

    private final int hp_max;
    private final int attack;
    private final int defense;
    private final int speed;

    private int hp;
    private State state;

    private Attack[] attacks;
    private final int NB_ATTACKS = 4;

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

        this.attacks = new Attack[NB_ATTACKS];

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

        if(!is_pos_valid(attack_number)){
            throw new NullPointerException();
        }
        Attack attack = attacks[attack_number];

        //TODO attack miss cause of paralysis
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

    public List<Event> start_of_round(Monster opponent, Terrain terrain){
        ArrayList<Event> events = new ArrayList<>();
        if(this.state != null){
            events.add(this.state.update_state(this, opponent, terrain));
        }
        events.addAll(this.type.start_of_round_trigger(this,opponent,terrain));
        return events;
    }

    public List<Event> end_of_round(Monster opponent, Terrain terrain){
        return new ArrayList<>(this.type.end_of_round_trigger(this, opponent, terrain));
    }

    public boolean is_faster(Monster opponent){
        return this.speed > opponent.speed;
    }

    @Override
    public String toString(){
        return this.name;
    }

    public void change_attack(Attack new_attack, int old_attack_pos) {
        if(new_attack.getType() != this.type.getType()  && new_attack.getType() != Type.NORMAL){
            return;
        }
        if(is_pos_valid(old_attack_pos)) {
            attacks[old_attack_pos] = new_attack;
        }else {
            throw new AssertionError();
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

    public int getHP(){
        return this.hp;
    }

    public String getName(){
        return this.name;
    }

    public Attack getSpecialAttack(int pos){
        if(is_pos_valid(pos)){
            return attacks[pos];
        }
        throw new AssertionError();
    }

    public Attack[] getAttacks(){
        return this.attacks;
    }

    public int get_number_of_attacks(){
        return 4;
    }

    private boolean is_pos_valid(int pos){
        return pos >= 0 && pos < NB_ATTACKS;
    }
}
