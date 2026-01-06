package com.malog.esiea.monsters.parsers;

import com.malog.esiea.monsters.helpers.StringHelper;
import com.malog.esiea.monsters.monsters.types.SubType;
import com.malog.esiea.monsters.monsters.types.Type;
import com.malog.esiea.monsters.monsters.types.stats.*;

import java.util.*;

public class TypeParser {
    protected static TypeStats parseType(List<String> lines, Type type, SubType sub_type) {
        lines = StringHelper.cleanAllStrings(lines);
        return switch (type) {
            case EARTH -> new EarthStats(type);
            case ELECTRIC -> {
                HashMap<String, String> found = (HashMap<String, String>) StringHelper.searchInStrings(lines, List.of("paralysis"));
                if(!found.containsKey("paralysis")){
                    throw new IllegalArgumentException("Missing paralysis");
                }
                int paralysis_probability = (int) (Float.parseFloat(found.get("paralysis")) * 100);
                yield new ElectricStats(type, paralysis_probability);
            }
            case FIRE -> {
                HashMap<String, String> found = (HashMap<String, String>) StringHelper.searchInStrings(lines, List.of("burn"));
                if(!found.containsKey("burn")){
                    throw new IllegalArgumentException("Missing burn");
                }
                int fire_probability = (int) (Float.parseFloat(found.get("burn")) * 100);
                yield new FireStats(type, fire_probability);
            }
            case NATURE -> switch (sub_type) {
                case Grass -> {
                    HashMap<String, String> found = (HashMap<String, String>) StringHelper.searchInStrings(lines, List.of("heal"));
                    if(!found.containsKey("heal")){
                        throw new IllegalArgumentException("Missing heal");
                    }
                    int heal_probability = (int) (Float.parseFloat(found.get("heal")) * 100);
                    yield new GrassStats(type, heal_probability);
                }
                case Insect -> new InsectStats(type);
            };
            case WATER -> {
                HashMap<String, String> found = (HashMap<String, String>) StringHelper.searchInStrings(lines, List.of("flood", "fall"));
                if(!found.containsKey("flood") || !found.containsKey("fall")){
                    throw new IllegalArgumentException("Missing flood and/or fall");
                }
                int flooding_probability = (int) (Float.parseFloat(found.get("flood")) * 100);
                int falling_probability = (int) (Float.parseFloat(found.get("fall")) * 100);
                yield new WaterStats(type, flooding_probability, falling_probability);
            }
            case NORMAL -> new NormalStats();
        };
    }
}
