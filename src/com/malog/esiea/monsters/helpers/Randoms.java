package com.malog.esiea.monsters.helpers;

import java.util.Random;

public class Randoms {
    public static int get_random_int_in_range(int min, int max){
        Random rand = new Random();
        int n = max - min + 1;
        return rand.nextInt() % n + min;
    }
}
