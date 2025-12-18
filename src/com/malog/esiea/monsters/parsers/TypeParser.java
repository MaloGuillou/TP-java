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
                    yield null;
                }
                int paralysis_probability = Integer.parseInt(found.get("paralysis"));
                yield new ElectricStats(type, paralysis_probability);
            }
            case FIRE -> {
                HashMap<String, String> found = (HashMap<String, String>) StringHelper.searchInStrings(lines, List.of("burn"));
                if(!found.containsKey("burn")){
                    yield null;
                }
                int fire_probability = Integer.parseInt(found.get("burn"));
                yield new FireStats(type, fire_probability);
            }
            case NATURE -> switch (sub_type) {
                case Grass -> {
                    HashMap<String, String> found = (HashMap<String, String>) StringHelper.searchInStrings(lines, List.of("heal"));
                    if(!found.containsKey("heal")){
                        yield null;
                    }
                    int heal_probability = Integer.parseInt(found.get("heal"));
                    yield new GrassStats(type, heal_probability);
                }
                case Insect -> new InsectStats(type);
            };
            case WATER -> {
                HashMap<String, String> found = (HashMap<String, String>) StringHelper.searchInStrings(lines, List.of("heal", "fall"));
                if(!found.containsKey("flood") || !found.containsKey("falling")){
                    yield null;
                }
                int flooding_probability = Integer.parseInt(found.get("flood"));
                int falling_probability = Integer.parseInt(found.get("fall"));
                yield new WaterStats(type, flooding_probability, falling_probability);
            }
        };
    }
}
