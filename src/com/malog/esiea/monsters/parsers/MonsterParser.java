package com.malog.esiea.monsters.parsers;

import com.malog.esiea.monsters.exceptions.UnparsableFile;
import com.malog.esiea.monsters.helpers.StringHelper;
import com.malog.esiea.monsters.monsters.MonsterBuilder;
import com.malog.esiea.monsters.monsters.types.SubType;
import com.malog.esiea.monsters.monsters.types.Type;
import com.malog.esiea.monsters.monsters.types.stats.TypeStats;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class MonsterParser {

    public static List<MonsterBuilder> parseFile(File file) throws FileNotFoundException {

        List<MonsterBuilder> monsters = new ArrayList<>();
        Scanner scanner = new Scanner(file);

        List<String> monster_lines  = new ArrayList<>();
        boolean parsing_monsters = false;

        int line_index = 0;

        while (scanner.hasNextLine()) {
            String line = StringHelper.cleanString(scanner.nextLine());
            if(line.startsWith("monster")){
                if(parsing_monsters){
                    throw new UnparsableFile("File wrongly formatted at line " + line_index);
                }
                monster_lines  = new ArrayList<>();
                parsing_monsters = true;
            }else if(line.startsWith("endmonster")){
                monsters.add(parseMonster(monster_lines));
                parsing_monsters = false;
            }else if(!line.trim().isEmpty()){
                monster_lines.add(line);
            }
            line_index++;
        }

        return monsters;
    }

    private static MonsterBuilder parseMonster(List<String> lines) {
        List<String> search_fields = List.of("name", "number", "type", "hp", "speed", "attack", "defense");

        ArrayList<String> unknown = new ArrayList<>();

        Map<String, String> full_found = new HashMap<>();

        for (String line : lines) {
            Map<String, String> found = StringHelper.searchInStrings(List.of(line), search_fields);
            if(!found.isEmpty()){
                full_found.putAll(found);
            }else{
                unknown.add(line);
            }
        }

        if(
                full_found.isEmpty()
                || !full_found.containsKey("name")
                || !full_found.containsKey("number")
                || !full_found.containsKey("type")
                || !full_found.containsKey("hp_max")
                || !full_found.containsKey("speed_max")
                || !full_found.containsKey("attack_max")
                || !full_found.containsKey("defense_max")
        ){
            throw new UnparsableFile("Missing required fields in monster");
        }else{
            String name =  full_found.get("name");
            int id =  Integer.parseInt(full_found.get("number"));

            int hp_max = Integer.parseInt(full_found.get("hp_max"));
            int hp_min = Integer.parseInt(full_found.get("hp_min"));

            int speed_max = Integer.parseInt(full_found.get("speed_max"));
            int speed_min = Integer.parseInt(full_found.get("speed_min"));

            int attack_max = Integer.parseInt(full_found.get("attack_max"));
            int attack_min = Integer.parseInt(full_found.get("attack_min"));

            int defense_max = Integer.parseInt(full_found.get("defense_max"));
            int defense_min = Integer.parseInt(full_found.get("defense_min"));

            Type type = null;
            SubType subType = null;
            switch (Type.valueOf(full_found.get("type").toUpperCase())) {
                case EARTH -> {
                    type = Type.EARTH;
                }
                case ELECTRIC -> {
                    type = Type.ELECTRIC;
                }
                case FIRE -> {
                    type = Type.FIRE;
                }
                case NATURE -> {
                    type = Type.NATURE;
                }
                case WATER -> {
                    type = Type.WATER;
                }
                default -> {
                    switch(SubType.valueOf(full_found.get("type"))) {
                        case Grass -> {
                            type = Type.NATURE;
                            subType = SubType.Grass;
                        }
                        case Insect -> {
                            type = Type.NATURE;
                            subType = SubType.Insect;
                        }
                    }
                }
            }

            if (type == null && subType == null) {
                throw new UnparsableFile("Type or SubType not found");
            }

            TypeStats type_stats = TypeParser.parseType(unknown, type, subType);

            return new MonsterBuilder(
                    id,
                    name,
                    type_stats,
                    hp_max,
                    hp_min,
                    attack_max,
                    attack_min,
                    defense_max,
                    defense_min,
                    speed_max,
                    speed_min
            );
        }
    }
}
