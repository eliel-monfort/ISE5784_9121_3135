package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for primitives.Point class
 * @author Yossi CohenA
 */
public class PointTests {

    private final double DELTA = 0.000001;

    /**
     * Test method for
     * {@link primitives.Point#subtract(primitives.Point)}.
     */
    @Test
    void testSubtract() {
        Point p1 = new Point(1, 2, 3);
        Point p2 = new Point(2, 4, 6);

        // ============ Equivalence Partitions Tests ==============
        //Test subtracting one point from another with different coordinates.
        assertEquals(new Vector(1, 2, 3), p2.subtract(p1), "ERROR: (point2 - point1) does not work correctly");
        assertEquals(new Vector(-1, -2, -3), p1.subtract(p2), "ERROR: (point2 - point1) does not work correctly");

        // =============== Boundary Values Tests ==================
        // Test subtracting a point from itself, expecting an IllegalArgumentException.
        assertThrows(IllegalArgumentException.class, ()-> p1.subtract(p1), "ERROR: (point - itself) does not throw an exception");
    }

    /**
     * Test method for
     * {@link primitives.Point#add(primitives.Vector)}.
     */
    @Test
    void testAdd() {
        Point p = new Point(1, 2, 3);
        Vector v1 = new Vector(3, 2, 1);
        Vector v2 = new Vector(-3, -2, -1);

        // ============ Equivalence Partitions Tests ==============
        // Test adding a vector to a point, resulting in another point.
        assertEquals(new Point(4, 4, 4), p.add(v1), "ERROR: (point + vector) = other point does not work correctly");

        // Test adding a vector to a point, resulting in the center of coordinates.
        assertEquals(new Point(-2, 0, 2), p.add(v2), "ERROR: (point + vector) = center of coordinates does not work correctly");
    }

    /**
     * Test method for
     * {@link primitives.Point#distanceSquared(primitives.Point)}.
     */
    @Test
    void testDistanceSquared() {
        Point p1 = new Point(3, 2, 6);
        Point p2 = new Point(6, 7, 9);

        // ============ Equivalence Partitions Tests ==============
        // Test calculating the squared distance between two different points.
        assertEquals(43, p2.distanceSquared(p1), DELTA, "ERROR: squared distance between points is wrong");
        assertEquals(43, p1.distanceSquared(p2), DELTA, "ERROR: squared distance between points is wrong");

        // =============== Boundary Values Tests ==================
        // Test calculating the squared distance between a point and itself, expecting a zero squared distance.
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
        // Test calculating the distance between two different points.
        assertEquals(6, p2.distance(p1), DELTA, "ERROR: distance between points to itself is wrong");
        assertEquals(6, p1.distance(p2), DELTA, "ERROR: distance between points to itself is wrong");

        // =============== Boundary Values Tests ==================
        // Test calculating the distance between a point and itself, expecting a zero distance.
        assertEquals(0, p1.distance(p1), DELTA, "ERROR: point distance to itself is not zero");
    }
}