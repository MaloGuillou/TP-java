package com.malog.esiea.monsters.items;

public enum ItemType {
    POTION{
        @Override
        public Item build(){
            return new Potion();
        }
    },
    MEDICINE{
        @Override
        public Item build(){
            return new Medicine();
        }
    };

    public Item build(){
        return null;
    }
}
