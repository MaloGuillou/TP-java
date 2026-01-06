package com.malog.esiea.monsters;

import com.malog.esiea.monsters.monsters.MonsterBuilder;
import com.malog.esiea.monsters.monsters.attacks.Attack;
import com.malog.esiea.monsters.parsers.AttackParser;
import com.malog.esiea.monsters.parsers.MonsterParser;
import com.malog.esiea.monsters.view.backend_link.ClientBackend;
import com.malog.esiea.monsters.view.console.TerminalUserInterface;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        InputStream monsterStream = Main.class.getResourceAsStream("/monsters.txt");
        InputStream attackStream = Main.class.getResourceAsStream("/attacks.txt");

        Map<Integer, MonsterBuilder> monsters;
        Map<Integer, Attack> attacks;

        try {
            monsters = MonsterParser.parse(monsterStream).stream().collect(Collectors.toMap(MonsterBuilder::getId, Function.identity()));
            attacks = AttackParser.parse(attackStream).stream().collect(Collectors.toMap(Attack::getId, Function.identity()));
        } catch (IOException e) {
            System.out.println("Error reading file");
            System.out.println(e.getMessage());
            return;
        }

        ClientApp clientApp = new ClientApp(monsters, attacks);
        ClientBackend clientBackend = new ClientBackend(clientApp);
        TerminalUserInterface ui = new TerminalUserInterface(clientBackend);

        ui.run();
    }
}