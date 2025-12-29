package com.malog.esiea.monsters.parsers;

import com.malog.esiea.monsters.helpers.StringHelper;
import com.malog.esiea.monsters.monsters.attacks.Attack;
import com.malog.esiea.monsters.monsters.types.SubType;
import com.malog.esiea.monsters.monsters.types.Type;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class AttackParser {
    public static List<Attack> parseFile(File file) throws IOException {

        List<Attack> attacks = new ArrayList<>();
        Scanner scanner = new Scanner(file);

        List<String> attack_lines  = new ArrayList<>();
        boolean parsing_attacks = false;
        while (scanner.hasNextLine()) {
            String line = StringHelper.cleanString(scanner.nextLine());
            if(line.startsWith("attack")){
                if(parsing_attacks){
                    throw new IOException("File wrongly formatted");
                }
                attack_lines  = new ArrayList<>();
                parsing_attacks = true;
            }else if(line.startsWith("endattack")){
                attacks.add(parseAttack(attack_lines));
                parsing_attacks = false;
            }else if(!line.trim().isEmpty()){
                attack_lines.add(line);
            }
        }

        return attacks;
    }

    private static Attack parseAttack(List<String> lines) throws IOException {
        List<String> search_fields = List.of("name", "type", "power", "nbuse", "fail");

        ArrayList<String> unknown = new ArrayList<>();

        Map<String, String> full_found = StringHelper.searchInStrings(lines, search_fields);

        if(
                full_found.isEmpty()
                        || !full_found.containsKey("name")
                        || !full_found.containsKey("type")
                        || !full_found.containsKey("power")
                        || !full_found.containsKey("nbuse")
                        || !full_found.containsKey("fail")
        ){
            throw new IOException("Missing required parameters in attack");
        }else{
            String name =  full_found.get("name");
            int nb_use_max = Integer.parseInt(full_found.get("nbuse"));
            int attack_power = Integer.parseInt(full_found.get("power"));
            int miss_probability = (int) (Double.parseDouble(full_found.get("fail")) * 100);

            Type type = null;
            switch (Type.valueOf(full_found.get("type").toUpperCase())) {
                case EARTH -> type = Type.EARTH;
                case ELECTRIC -> type = Type.ELECTRIC;
                case FIRE -> type = Type.FIRE;
                case NATURE -> type = Type.NATURE;
                case WATER -> type = Type.WATER;
                case NORMAL -> type = Type.NORMAL;
                default -> {
                    switch(SubType.valueOf(full_found.get("type"))) {
                        case Grass, Insect -> {
                            type = Type.NATURE;
                        }
                    }
                }
            }

            if (type == null) {
                throw new IOException("Missing required parameters in attack");
            }

            return new Attack(
                    name,
                    type,
                    nb_use_max,
                    attack_power,
                    miss_probability
            );
        }
    }
}
