import java.util.Map;
import java.util.Scanner;

public class Game {
    public static final String QUIT_COMMAND = "QUIT";
    public static final String RESTART_COMMAND = "RESTART";
    public static final Map<String, Direction> KEY_MAP = Map.of(
            "W", Direction.UP,
            "A", Direction.LEFT,
            "S", Direction.DOWN,
            "D", Direction.RIGHT
    );

    private Board board;

    public Game() {
        this.board = new Board(Board.DEFAULT_ROWS, Board.DEFAULT_COLUMNS);
    }

    public void play() {
        System.out.println("Welcome to 2048!");
        System.out.println(System.lineSeparator());

        try (Scanner sc = new Scanner(System.in)) {
            while (board.hasLegalMoves()) {
                Board.AsciiBoardDrawer.print(board);

                showOptions();

                System.out.print("Enter command: ");
                String input = sc.nextLine().toUpperCase();

                while (!validCommand(input)) {
                    System.out.println("Invalid input. Enter one of the " +
                            "given commands.");

                    System.out.print("Enter command: ");
                    input = sc.nextLine().toUpperCase();
                }

                if (input.equals(QUIT_COMMAND)) {
                    System.out.println("Thanks for playing!");
                    System.exit(0);
                } else if (input.equals(RESTART_COMMAND)) {
                    board = new Board(Board.DEFAULT_ROWS,
                            Board.DEFAULT_COLUMNS);
                } else {
                    board.shiftTiles(KEY_MAP.get(input));
                }
            }
        }
    }

    private boolean validCommand(String command) {
        return command.equals(QUIT_COMMAND)
                || command.equals(RESTART_COMMAND)
                || KEY_MAP.containsKey(command);
    }

    private void showOptions() {
        System.out.println("(" + QUIT_COMMAND
                + ") Quit game  ("
                + RESTART_COMMAND
                + ") Restart game");

        String options = "WASD";

        for (int i = 0; i < options.length(); i++) {
            char option = options.charAt(i);

            String description = switch (option) {
                case 'W' -> "Move up";
                case 'A' -> "Move left";
                case 'S' -> "Move down";
                case 'D' -> "Move right";
                default -> throw new IllegalStateException();
            };

            System.out.print("(" + option + ") " + description + "  ");
        }
        System.out.println(System.lineSeparator());
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.play();
    }
}
