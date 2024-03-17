public enum Direction {
    UP(new int[] {-1, 0}),
    RIGHT(new int[] {0, 1}),
    DOWN(new int[] {1, 0}),
    LEFT(new int[] {0, -1});

    public final int[] vector;

    private Direction(int[] vector) {
        this.vector = vector;
    }
}
