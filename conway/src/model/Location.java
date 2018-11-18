package model;

import java.util.ArrayList;
import java.util.List;

/**
 * represents either a location
 * or an offset
 */
public class Location {
    private final int x;
    private final int y;

    public Location(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Location(int coords[]) {
        assert coords.length == 2;
        x = coords[0];
        y = coords[1];
    }

    public static List<Location> Locations(int offsets[][]) {
        ArrayList<Location> rval = new ArrayList<>(offsets.length);
        for (int pair[]  : offsets) {
            rval.add(new Location(pair));
        }
        return rval;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public static Location add(Boundary boundary, Location lhs, Location rhs) {
        return boundary.bound(lhs.x + rhs.x , lhs.y + rhs.y);
    }

    @Override
    public String toString() {
        return "(" + x + "," + y +")";
    }
}
