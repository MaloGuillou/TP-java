package com.malog.esiea.monsters.parsers;

import com.malog.esiea.monsters.helpers.StringHelper;
import com.malog.esiea.monsters.monsters.attacks.Attack;
import com.malog.esiea.monsters.monsters.types.Type;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class AttackParser {
    public static List<Attack> parse(InputStream file) throws IOException {

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
        List<String> search_fields = List.of("id", "name", "type", "power", "nbuse", "fail");

        Map<String, String> full_found = StringHelper.searchInStrings(lines, search_fields);

        if(
                full_found.isEmpty()
                        || !full_found.containsKey("id")
                        || !full_found.containsKey("name")
                        || !full_found.containsKey("type")
                        || !full_found.containsKey("power")
                        || !full_found.containsKey("nbuse")
                        || !full_found.containsKey("fail")
        ){
            throw new IOException("Missing required parameters in attack");
        }else{
            int id = Integer.parseInt(full_found.get("id"));
            String name =  full_found.get("name");
            int nb_use_max = Integer.parseInt(full_found.get("nbuse"));
            int attack_power;
            try{
                attack_power = Integer.parseInt(full_found.get("power"));
            }catch (NumberFormatException e ){
                attack_power = (int) Double.parseDouble(full_found.get("power"));
            }
            int miss_probability = (int) (Double.parseDouble(full_found.get("fail")) * 100);

            Type type = null;
            String typeRaw = full_found.get("type");

            try {
                type = Type.valueOf(typeRaw.toUpperCase());
            } catch (IllegalArgumentException e2) {
                throw new IOException("Unknown Type: " + typeRaw);
            }

            return new Attack(
                    id,
                    name,
                    type,
                    nb_use_max,
                    attack_power,
                    miss_probability
            );
        }
    }
}
