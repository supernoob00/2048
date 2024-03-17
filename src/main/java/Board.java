import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;

public class Board {
    private static List<Map.Entry<Character, Integer>> characterList() {
        List<Map.Entry<Character, Integer>> list = new ArrayList<>();

        int j = 0;

        list.add(Map.entry('0', 0));

        for (int i = 1; i <= 2048; i *= 2) {
            if (i <= 8) {
                list.add(Map.entry((char) (i + '0'), i));
            } else {
                list.add(Map.entry((char) (j + 'A'), i));
                j++;
            }
        }

        return list;
    }

    private static BiMap<Character, Integer> biMap = ImmutableBiMap.copyOf(characterList());

    private final int rows;
    private final int cols;

    private final int[][] board;
    private int n;

    public Board(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;

        this.board = new int[rows][cols];
    }

    public Board(String text) {
        // TODO: upgrade to JDK22 to fix this
        this(text.split(System.lineSeparator()).length, text.split(System.lineSeparator())[0].length());

        String[] rows = text.split(System.lineSeparator());

        for (int i = 0; i < rows.length; i++) {
            for (int j = 0; j < rows[0].length(); j++) {
                char c = rows[i].charAt(j);

                if (!biMap.containsKey(c)) {
                    throw new IllegalArgumentException("String contains " +
                            "invalid character: " + c);
                }

                int val = biMap.get(rows[i].charAt(j));
                if (val != 0) {
                    addTile(val, i, j);
                }
            }
        }
    }

    public void shiftTiles(Direction direction) {
        switch (direction) {
            case UP -> shiftUp();
            case RIGHT -> shiftRight();
            case DOWN -> shiftDown();
            case LEFT -> shiftLeft();
        }
    }

    public boolean isFull() {
        return n == board.length * board[0].length;
    }

    public boolean legalMoves() {
        // check if not full first to avoid unnecessary calculation
        if (!isFull()) {
            return true;
        }

        // check if any adjacent tiles can be merged
        for (int i = 0; i < rows; i++) {
            for (int j = 1; j < cols; j++) {
                if (board[i][j - 1] != board[i][j]
                        || (i != 0 && board[i - 1][j] != board[i][j])) {
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Board otherBoard = (Board) o;
        return rows == otherBoard.rows
                && cols == otherBoard.cols
                && n == otherBoard.n
                && Arrays.deepEquals(board, otherBoard.board);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(rows, cols, n);
        result = 31 * result + Arrays.deepHashCode(board);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Tile count: ")
          .append(n)
          .append(System.lineSeparator());

        for (int[] row : board) {
            sb.append(Arrays.toString(row))
              .append(System.lineSeparator());
        }
        return sb.toString();
    }

    private void shiftRight() {
        for (int i = 0; i < rows; i++) {
            int rj = cols - 1;

            for (int j = cols - 2; j >= 0; j--) {
                if (board[i][j] != 0) {
                    if (board[i][rj] == 0) {
                        board[i][rj] = board[i][j];
                        board[i][j] = 0;
                    } else if (board[i][rj] == board[i][j]) {
                        mergeTiles(i, j, i, rj);
                    } else if (rj - 1 != j){
                        board[i][rj - 1] = board[i][j];
                        board[i][j] = 0;
                    }
                    rj--;
                }
            }
        }
    }

    private void shiftLeft() {
        for (int i = 0; i < rows; i++) {
            int lj = 0;

            for (int j = 1; j < cols; j++) {
                if (board[i][j] != 0) {
                    if (board[i][lj] == 0) {
                        board[i][lj] = board[i][j];
                        board[i][j] = 0;
                    } else if (board[i][lj] == board[i][j]) {
                        mergeTiles(i, j, i, lj);
                    } else if (lj + 1 != j){
                        board[i][lj + 1] = board[i][j];
                        board[i][j] = 0;
                    }
                    lj++;
                }
            }
        }
    }

    private void shiftUp() {

    }

    private void shiftDown() {

    }

    private void addTile(int val, int i, int j) {
        assert val > 0;
        assert !isFull();
        assert board[i][j] == 0;

        board[i][j] = val;
        n++;
    }

    private void mergeTiles(int srcI, int srcJ, int dstI, int dstJ) {
        assert(board[srcI][srcJ] != 0 && board[srcI][srcJ] == board[dstI][dstJ]);
        assert(srcI != srcJ || dstI != dstJ);

        board[dstI][dstJ] += board[srcI][srcJ];
        board[srcI][srcJ] = 0;
        n--;
    }
}
