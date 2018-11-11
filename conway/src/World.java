import java.util.ArrayList;
import java.util.function.BiFunction;

/**
 * Implement rectangle worlds
 */
public class World {
    final private int width;
    final private int height;
    private final Cell[][] cells;

    /**
     *
     * @param width: cells across
     * @param height: cells down
     * @param creator: specify type of cell to create
     */
    public World(int width, int height, BiFunction<Integer,Integer,Cell> creator) {
        this.width = width;
        this.height = height;
        cells = new Cell[width][height];
        iterate( (x, y) -> cells[x][y] = creator.apply(x,y));
        iterate(this::setNeighbors);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Cell get(int x, int y) {
        return cells[x][y];
    }

    public void setAlive(int x, int y) {
        cells[x][y].setAlive();
    }

    /**
     * update all the next cycle cells values and then
     * increment the clock
     */
    public void step() {
        iterate((x, y) -> cells[x][y].calculateNext());
        Clock.getInstance().step();
    }

    /**
     * call method on each cell, sequentially
     * @param consumer
     */
    public void iterate(CellConsumer consumer) {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                consumer.apply(x, y);
            }
        }
    }

    /**
     * tell a cell who its neighbors are
     * @param x
     * @param y
     */
    private void setNeighbors(int x, int y) {
        int offsets[][] = {
                {-1, -1},
                {-1, 0},
                {-1, 1},
                {0, -1},
                {0, 1},
                {1, -1},
                {1, 0},
                {1, 1},
        };
        ArrayList<Cell> neighbors = new ArrayList(offsets.length);
        for (int i = 0; i < offsets.length; i++) {
            int xypair[] = offsets[i];
            int nx = adjacent(x, xypair[0], width);
            int ny = adjacent(y, xypair[1], height);
            neighbors.add(cells[nx][ny]);
        }

        cells[x][y].setNeighbors(neighbors);
    }

    /**
     * get x or y coordinate of adjacent cell, taking into account wrap-around at edges of world
     * @param value: starting point
     * @param offset: change from starting (-1,0, or 1)
     * @param size: limit of dimension (x or y)
     * @return value + offset modulo size
     */
    private int adjacent(int value, int offset, int size) {
        assert Math.abs(offset) <= 1;
        int returnVal = value + offset;
        if (returnVal < 0) {
            return returnVal + size;
        }
        if (returnVal >= size) {
            return returnVal % size;
        }
        return returnVal;
    }

    /**
     * functional API for iterate
     */
    public interface CellConsumer {
        //Use "apply" to be consistent with java.util.function API
        void apply(int x, int y);
    }
}
