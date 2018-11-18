package model;

/**
 * represent a fixed geometric space that constrains
 * Location
 */
public interface Boundary {

    Location bound(int x, int y);
}
