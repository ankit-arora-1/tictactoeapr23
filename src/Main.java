import controllers.GameController;

public class Main {
    public static void main(String[] args) {
        GameController gameController = new GameController();

       Game game = gameController.startGame();
       Game game2 = gameController.startGame();
        while(gameController.checkStatus(game2) == IN_PROGRESS) {
            gameController.printBoard();
            gameController.makeMove();
        }

        System.out.println("Print winner or draw");

        System.out.println("Hello world!");
    }
}