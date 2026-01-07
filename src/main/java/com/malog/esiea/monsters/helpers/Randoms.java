package com.malog.esiea.monsters.helpers;

import java.util.List;
import java.util.Random;

public class Randoms {

    private static final Random INSTANCE = new Random();

    public static int get_random_int_in_range(int min, int max){
        return INSTANCE.nextInt(max - min + 1) + min;
    }

    public static int get_random_int_in_values(List<Integer> values){
        int n = INSTANCE.nextInt(values.size());
        return values.get(n);
    }
}
