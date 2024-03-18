import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BoardTest {
    private static String readFile(File f) {
        StringBuilder sb = new StringBuilder();

        try (Scanner s = new Scanner(f)) {
            while (s.hasNextLine()) {
                sb.append(s.nextLine()).append(System.lineSeparator());
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e.getMessage());
        }
        return sb.toString();
    }

    private Board board1;
    private Board board1RightShift;
    private Board board1LeftShift;
    private Board board1UpShift;
    private Board board1DownShift;

    @Before
    public void resetBoards() {
        String board1Text = readFile(new File("src/test/resources/test_board_1/test_board_1.txt"));
        board1 = new Board(board1Text);

        String board1RightShiftText = readFile(new File("src/test/resources" +
                "/test_board_1/test_board_1_right_shift.txt"));
        board1RightShift = new Board(board1RightShiftText);

        String board1LeftShiftText = readFile(new File("src/test/resources" +
                "/test_board_1/test_board_1_left_shift.txt"));
        board1LeftShift = new Board(board1LeftShiftText);

        String board1UpShiftText = readFile(new File("src/test/resources" +
                "/test_board_1/test_board_1_up_shift.txt"));
        board1UpShift = new Board(board1UpShiftText);

        String board1DownShiftText = readFile(new File("src/test/resources" +
                "/test_board_1/test_board_1_down_shift.txt"));
        board1DownShift = new Board(board1DownShiftText);
    }

    @Test
    public void rightShiftWorks() {
        board1.shiftTiles(Direction.RIGHT);
        Assert.assertEquals(board1RightShift, board1);
    }

    @Test
    public void leftShiftWorks() {
        board1.shiftTiles(Direction.LEFT);
        Assert.assertEquals(board1LeftShift, board1);
    }

    @Test
    public void upShiftWorks() {
        board1.shiftTiles(Direction.UP);
        Assert.assertEquals(board1UpShift, board1);
    }

    @Test
    public void downShiftWorks() {
        board1.shiftTiles(Direction.DOWN);
        Assert.assertEquals(board1DownShift, board1);
    }
}
