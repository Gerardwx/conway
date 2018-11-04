import java.util.ArrayList;
import java.util.function.BiConsumer;

public class World {
    final int width;
    final int height;
    private final Cell[][] cells;
    private final Display display;

    public World(int width, int height, Display display) {
        this.width = width;
        this.height = height;
        this.display = display;
        cells = new Cell[width][height];
        //iterate(this::creation);
        iterate( (x, y) -> cells[x][y] = new DebugCell(x,y));
        iterate(this::setNeighbors);
    }

    public void setAlive(int x, int y) {
        cells[x][y].setAlive();

    }

    public void show() {
        iterate((x, y) -> display.show(cells[x][y], x, y));
        display.complete();
    }

    public void step() {
        iterate((x, y) -> cells[x][y].calculateNext());
        Clock.getInstance().step();
    }

    private void iterate(BiIntConsumer consumer) {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                consumer.apply(x, y);
            }
        }
    }

    private void creation(int x, int y) {
        cells[x][y] = create();
    }


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

    protected Cell create() {
        return new ClassicCell();
    }

    private interface BiIntConsumer {
        void apply(int x, int y);
    }
}
