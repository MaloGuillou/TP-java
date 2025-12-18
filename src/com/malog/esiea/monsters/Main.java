import com.malog.esiea.monsters.UI.ConsoleInterface;
import com.malog.esiea.monsters.UI.UserInterface;
import com.malog.esiea.monsters.game.MatchesManager;
import com.malog.esiea.monsters.game.Player;
import com.malog.esiea.monsters.monsters.Monster;
import com.malog.esiea.monsters.monsters.MonsterBuilder;
import com.malog.esiea.monsters.monsters.attacks.Attack;
import com.malog.esiea.monsters.parsers.AttackParser;
import com.malog.esiea.monsters.parsers.MonsterParser;

void main(String[] args) throws FileNotFoundException {
    File monsters_file = new File("resources/monsters.txt");
    File attacks_file = new File("resources/attacks.txt");
    //Parse monsters
    Map<Integer, MonsterBuilder> monsters = MonsterParser.parseFile(monsters_file).stream().collect(Collectors.toMap(MonsterBuilder::getId, Function.identity()));
    //Parse attacks
    List<Attack> attacks = AttackParser.parseFile(attacks_file);
    //Init gameManager
    MatchesManager matchesManager = new MatchesManager();
    //Init UI
    UserInterface ui = new ConsoleInterface();

    //Init Player
    String pseudo = ui.getPseudo();
    Player player = new Player(pseudo);

    player.set_team(ui.chooseTeam(player, monsters, attacks));

    //Start main loop
    boolean exit = false;
    while (!exit) {
        //Wait for user input
        //Handle user input
        //Work
        //Update ui

        exit = true;
    }

}
