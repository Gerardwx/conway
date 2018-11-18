package model;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class LocationTest implements  Boundary {

    @Test
    public void add( ) {
        Location a = new Location(1,1);
        Location b = new Location(3,7);
        Location c = Location.add(this,a,b);
        Assert.assertTrue(c.getX() == 4);
        Assert.assertTrue(c.getY() == 8);
    }

    @Override
    public Location bound(int x, int y) {
        return new Location(x,y);
    }


}