/**
 * show Life grid to console
 */
public class ConsoleDisplay {
    final World world;
    int lastY;

    public ConsoleDisplay(World world) {
        this.world = world;
        lastY = 0;
    }

    /**
     * show cells, then a couple blank lines
     */
    public void show( ) {
        world.iterate(this::show);
        lastY = 0;
        System.out.println();
        System.out.println();
    }

    /**
     * show individual cell; add new line if Y has
     * incremented since last call
     * @param x
     * @param y
     */
    private void show(int x, int y) {
        Cell cell = world.get(x, y);
        char mark = cell.isAlive() ? 'X' : '.';
        if (y > lastY) {
            System.out.println();
            lastY = y;
        }
        System.out.print(mark);
    }
}
