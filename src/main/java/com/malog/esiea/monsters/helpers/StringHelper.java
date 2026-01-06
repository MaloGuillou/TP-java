package com.malog.esiea.monsters.helpers;

import java.util.*;

public class StringHelper {

    public static String cleanString(String string) {
        return string.toLowerCase().replaceAll("\\s+", " ").trim();
    }

    public static ArrayList<String> cleanAllStrings(List<String> strings) {
        ArrayList<String> result = new ArrayList<>();
        for (String string : strings) {
            result.add(cleanString(string));
        }
        return result;
    }

    public static Map<String, String> searchInStrings(List<String> strings, List<String> keys) {
        Map<String, String> result = new HashMap<>();

        cleanAllStrings(strings);

        for (String string : strings) {
            for (String key : keys) {
                if(string.startsWith(key)) {
                    String[] split = string.split(" ");
                    if(split.length == 2) {
                        result.put(key, split[1]);
                    }else if(split.length == 3) {
                        result.put(key + "_min", split[1]);
                        result.put(key + "_max", split[2]);
                    }
                }
            }
        }
        return result;
    }
}
