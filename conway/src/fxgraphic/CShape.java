package fxgraphic;

import model.Location;

import java.util.Collections;
import java.util.List;

/**
 * model standard Conway shapes
 */
public class CShape {
    public static final CShape DOT = makeDot();
    public static final CShape RIGHT_GLIDER = makeRightGlider();
    public static final CShape LEFT_GLIDER = makeLeftGlider();
    private final List<Location> locations;
    private final String name;

    public CShape(String name,List<Location> locations) {
        this.name = name;
        this.locations = Collections.unmodifiableList(locations);
    }

    public List<Location> getLocations() {
        return locations;
    }

    @Override
    public String toString( ) {
        return name;
    }

    private static CShape makeDot( )  {
        int offsets[][] = { {0,0}};
        return new CShape("Dot", Location.Locations(offsets));
    }

    private static CShape makeRightGlider( )  {
        int offsets[][] = {
                {0,0},
                {1,1},
                {-1,2},
                {0,2},
                {1,2}};
        return new CShape("Right Glider", Location.Locations(offsets));
    }

    private static CShape makeLeftGlider( )  {
        int offsets[][] = {
                {0,0},
                {-1,1},
                {-1,2},
                {0,2},
                {1,2}};
        return new CShape("Left Glider", Location.Locations(offsets));
    }

}
