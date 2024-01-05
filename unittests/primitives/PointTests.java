package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

/**
 * Unit tests for primitives.Point class
 * @author Ariel Atias
 */
public class PointTests {
    private final double DELTA = 0.000001;

    /**
     * Test method for
     * {@link primitives.Point#subtract(primitives.Point)}.
     */
    @Test
    void testSubtract() {
        Point p1 = new Point(-1, 2, 3);
        Point p2 = new Point(2, -4, 6);

        // ============ Equivalence Partitions Tests ==============
        // TC01: Test subtracting a point from another, expecting not to throw an IllegalArgumentException.
        assertDoesNotThrow(()-> p1.subtract(p2), "ERROR: (point2 - point1) throws wrong exception");

        // TC02: Test subtracting one point from another with different coordinates.
        assertEquals(new Vector(3, -6, 3), p2.subtract(p1), "ERROR: (point2 - point1) does not work correctly");

        // =============== Boundary Values Tests ==================
        // TC10: Test subtracting a point from itself, expecting an IllegalArgumentException.
        assertThrows(IllegalArgumentException.class, ()-> p1.subtract(p1), "ERROR: (point - itself) does not throw an exception");
    }

    /**
     * Test method for
     * {@link primitives.Point#add(primitives.Vector)}.
     */
    @Test
    void testAdd() {
        Point p = new Point(1, 2, -3);
        Vector v1 = new Vector(6, -4, 7);
        Vector v2 = new Vector(-1, -2, 3);

        // ============ Equivalence Partitions Tests ==============
        // TC01: Test adding a vector to a point, expecting not to throw an IllegalArgumentException.
        assertDoesNotThrow(()-> p.add(v1), "ERROR: (point + vector) throws wrong exception");

        // TC02: Test adding a vector to a point, resulting in another point.
        assertEquals(new Point(7, -2, 4), p.add(v1), "ERROR: (point + vector) = other point does not work correctly");

        // TC03: Test adding a vector to a point, testing the center of coordinates.
        assertEquals(Point.ZERO, p.add(v2), "ERROR: (point + vector) = center of coordinates does not work correctly");
    }

    /**
     * Test method for
     * {@link primitives.Point#distanceSquared(primitives.Point)}.
     */
    @Test
    void testDistanceSquared() {
        Point p1 = new Point(-3, 2, 6);
        Point p2 = new Point(6, 7, -9);

        // ============ Equivalence Partitions Tests ==============
        // TC01: Test squared distance between two points, expecting not to throw an IllegalArgumentException.
        assertDoesNotThrow(()-> p1.distanceSquared(p1), "ERROR: squared distance between points throws wrong exception");

        // TC02: Test calculating the squared distance between two different points.
        assertEquals(331, p2.distanceSquared(p1), DELTA, "ERROR: squared distance between points is wrong");

        // =============== Boundary Values Tests ==================
        // TC10: Test calculating the squared distance between a point and itself, expecting a zero squared distance.
        assertEquals(0, p1.distanceSquared(p1), DELTA, "ERROR: point squared distance to itself is not zero");
    }

    /**
     * Test method for
     * {@link primitives.Point#distance(primitives.Point)}.
     */
    @Test
    void testDistance() {
        Point p1 = new Point(5, 2, 3);
        Point p2 = new Point(9, 4, 7);

        // ============ Equivalence Partitions Tests ==============
        // TC01: Test distance between two points, expecting not to throw an IllegalArgumentException.
        assertDoesNotThrow(()-> p1.distance(p1), "ERROR: distance between points throws wrong exception");

        // TC02: Test calculating the distance between two different points.
        assertEquals(6, p2.distance(p1), DELTA, "ERROR: distance between points to itself is wrong");
        assertEquals(6, p1.distance(p2), DELTA, "ERROR: distance between points to itself is wrong");

        // =============== Boundary Values Tests ==================
        // TC10: Test calculating the distance between a point and itself, expecting a zero distance.
        assertEquals(0, p1.distance(p1), DELTA, "ERROR: point distance to itself is not zero");
    }
}