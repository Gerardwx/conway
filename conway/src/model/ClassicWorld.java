package model;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * model.World with classic definition of neighbor in rectanglar grid
 */
public class ClassicWorld extends World {

    /**
     * @param width: cells across
     * @param height: cells down
     * @param creator: specify type of cell to create
     */
    public ClassicWorld(int width, int height, Function<Location ,Cell> creator) {
        super(width, height, creator, classicOffsets());
    }

    private static int[][] classicOffsets() {
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
        return offsets;
    }

}
