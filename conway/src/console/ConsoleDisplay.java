package console;

import model.Cell;
import model.Location;
import model.World;

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
     */
    private void show(Location location) {
        Cell cell = world.get(location);
        char mark = cell.isAlive() ? 'X' : '.';
        if (location.getY() > lastY) {
            System.out.println();
            lastY = location.getY();
        }
        System.out.print(mark);
    }
}
