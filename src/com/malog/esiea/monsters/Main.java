import com.malog.esiea.monsters.ClientApp;
import com.malog.esiea.monsters.monsters.MonsterBuilder;
import com.malog.esiea.monsters.monsters.attacks.Attack;
import com.malog.esiea.monsters.parsers.AttackParser;
import com.malog.esiea.monsters.parsers.MonsterParser;
import com.malog.esiea.monsters.view.backend_link.ClientBackend;
import com.malog.esiea.monsters.view.console.TerminalUserInterface;

void main(String[] args) throws FileNotFoundException {
    File monsters_file = new File("resources/monsters.txt");
    File attacks_file = new File("resources/attacks.txt");

    Map<Integer, MonsterBuilder> monsters;
    Map<Integer, Attack> attacks;

    try {
        monsters = MonsterParser.parseFile(monsters_file).stream().collect(Collectors.toMap(MonsterBuilder::getId, Function.identity()));
        attacks = AttackParser.parseFile(attacks_file).stream().collect(Collectors.toMap(Attack::getId, Function.identity()));
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