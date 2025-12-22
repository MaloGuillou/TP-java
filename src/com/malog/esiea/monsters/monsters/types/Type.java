package com.malog.esiea.monsters.monsters.types;

public enum Type {
    EARTH {
        @Override
        public boolean is_strong_against(Type type) {
            return type.equals(ELECTRIC);
        }
        @Override
        public boolean is_weak_against(Type type) {
            return type.equals(NATURE);
        }
    },
    ELECTRIC {
        @Override
        public boolean is_strong_against(Type type) {
            return type.equals(WATER);
        }
        @Override
        public boolean is_weak_against(Type type) {
            return type.equals(EARTH);
        }
    },
    FIRE {
        @Override
        public boolean is_strong_against(Type type) {
            return type.equals(NATURE);
        }
        @Override
        public boolean is_weak_against(Type type) {
            return type.equals(WATER);
        }
    },
    NATURE {
        @Override
        public boolean is_strong_against(Type type) {
            return type.equals(EARTH);
        }
        @Override
        public boolean is_weak_against(Type type) {
            return type.equals(FIRE);
        }
    },
    WATER {
        @Override
        public boolean is_strong_against(Type type) {
            return type.equals(FIRE);
        }
        @Override
        public boolean is_weak_against(Type type) {
            return type.equals(ELECTRIC);
        }
    },
    NORMAL;

    public boolean is_strong_against(Type type) {
        return false;
    }
    public boolean is_weak_against(Type type) {
        return false;
    }
}
