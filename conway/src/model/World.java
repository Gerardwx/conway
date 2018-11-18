package model;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Implement rectangle worlds
 */
public class World  implements Boundary {
    final private int width;
    final private int height;
    private final Cell[][] cells;

    /**
     * @param width:   cells across
     * @param height:  cells down
     * @param creator: specify type of cell to create
     */
    protected World(int width, int height, Function<Location, Cell> creator, int offsets[][]) {
        this.width = width;
        this.height = height;
        cells = new Cell[width][height];
        iterate((c) -> cells[c.getX()][c.getY()] = creator.apply(c));
        iterate((c) -> setNeighbors(c, offsets));
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

    public Cell get(Location location) {
        return cells[location.getX()][location.getY()];
    }

    /**
     * update all the next cycle cells values and then
     * increment the clock
     */
    public void step() {
        iterate((c) -> cells[c.getX()][c.getY()].calculateNext());
        Clock.getInstance().step();
    }

    /**
     * iterate model.World coordinates, sequentially
     *
     * @param consumer
     */
    public void iterate(Consumer<Location> consumer) {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                consumer.accept(new Location(x,y));
            }
        }
    }

    /**
     * tell a cell who its neighbors are
     *
     */
    private void setNeighbors(Location cellLocation, int offsets[][]) {
        List<Location> locations = Location.Locations(offsets);
        List<Cell> neighbors = new ArrayList<>(locations.size());
        for (Location delta: locations) {
            Location n = Location.add(this, cellLocation, delta);
            neighbors.add(get(n));
        }

        get(cellLocation).setNeighbors(neighbors);
    }

    /**
     * wrap x and/or y around to other edge if < 0
     * or greater than width, height, respectively
     * @param x
     * @param y
     * @return Location in bounds of World
     */
    @Override
    public Location bound(int x, int y) {
        int nx = normalize(x, width);
        int ny = normalize(y,  height);
        return new Location(nx,ny);
    }

    /**
     * @param value
     * @param size
     * @return: positive value modulo size
     */
    private int normalize(int value, int size) {
        assert value > -size;
        return (value + size) % size;


    }
}
