import model.Difficulty;
//import model.Minesweeper;
import model.Minesweeper;
import model.PlayableMinesweeper;
import view.MinesweeperView;

public class App {
    public static void main(String[] args) throws Exception {

        MinesweeperView view = new MinesweeperView();
        PlayableMinesweeper model = new Minesweeper();
        view.setGameModel(model);
        model.setGameStateNotifier(view);

    }
}
