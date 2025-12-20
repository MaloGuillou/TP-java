import com.malog.esiea.monsters.ClientApp;
import com.malog.esiea.monsters.monsters.MonsterBuilder;
import com.malog.esiea.monsters.monsters.attacks.Attack;
import com.malog.esiea.monsters.parsers.AttackParser;
import com.malog.esiea.monsters.parsers.MonsterParser;

void main(String[] args) throws FileNotFoundException {
    File monsters_file = new File("resources/monsters.txt");
    File attacks_file = new File("resources/attacks.txt");

    Map<Integer, MonsterBuilder> monsters = MonsterParser.parseFile(monsters_file).stream().collect(Collectors.toMap(MonsterBuilder::getId, Function.identity()));
    List<Attack> attacks = AttackParser.parseFile(attacks_file);

    ClientApp clientApp = new ClientApp(monsters, attacks);
    clientApp.run();
}