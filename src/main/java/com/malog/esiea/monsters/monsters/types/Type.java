package com.malog.esiea.monsters.monsters.types;

public enum Type {
    EARTH,
    ELECTRIC,
    FIRE,
    NATURE,
    WATER,
    NORMAL,
    // --- Sub types
    GRASS(NATURE),
    INSECT(NATURE);

    private final Type parentType;

    Type() { this.parentType = this; }
    Type(Type parent) { this.parentType = parent; }

    public Type getEffectiveType() {
        return parentType;
    }

    public boolean is_strong_against(Type other) {
        Type self = this.getEffectiveType();
        Type them = other.getEffectiveType();

        switch (self){
            case EARTH -> {
                return them.equals(ELECTRIC);
            }
            case ELECTRIC -> {
                return them.equals(WATER);
            }
            case FIRE -> {
                return them.equals(NATURE);
            }
            case NATURE -> {
                return them.equals(EARTH);
            }
            case WATER -> {
                return them.equals(FIRE);
            }
            default -> {
                return false;
            }
        }
    }

    public boolean is_weak_against(Type other) {
        Type self = this.getEffectiveType();
        Type them = other.getEffectiveType();

        switch (self){
            case EARTH -> {
                return them.equals(NATURE);
            }
            case ELECTRIC -> {
                return them.equals(EARTH);
            }
            case FIRE -> {
                return them.equals(WATER);
            }
            case NATURE -> {
                return them.equals(FIRE);
            }
            case WATER -> {
                return them.equals(ELECTRIC);
            }
            default -> {
                return false;
            }
        }
    }
}
