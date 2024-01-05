package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

/**
 * Unit tests for geometries.Plane class
 * @author Ariel Atias
 */
class PlaneTests {
    private final double DELTA = 0.000001;

    /**
     * Test case for the
     * {@link geometries.Plane#Plane (geometries.Plane)}.
     */
    @Test
    void testConstructor() {
        Plane plane1 = new Plane(new Point(0,0,0), new Point(0,1,0), new Point(0,0,1));

        // ============ Equivalence Partitions Tests ==============
        // TC01: All three points are the same. Expects exceptions to be thrown.
        assertThrows(IllegalArgumentException.class, ()-> new Plane(new Point(1,2,3), new Point(1,2,3), new Point(1,2,3)));

        // TC02: Checks normal vector length and correctness for non-collinear points.
        assertEquals(1, plane1.getNormal().length(), "ERROR: Plane constructor create wrong normal vector length");
        assertTrue(isZero(plane1.getNormal().dotProduct(new Vector(0,1,0))), "Plane constructor: wrong normal vector");
        assertTrue(isZero(plane1.getNormal().dotProduct(new Vector(0,0,1))), "Plane constructor: wrong normal vector");

        // =============== Boundary Values Tests ==================
        // TC10: Checks if an exception is thrown when two points are the same.
        assertThrows(IllegalArgumentException.class,
                ()->new Plane(new Point(1,0,0), new Point(1,0,0), new Point(5,4,6)),
                "ERROR: Plane constructor Does not throw an exception when two points are same");

        // TC11: Checks if an exception is thrown when all points are on the same straight line.
        assertThrows(IllegalArgumentException.class, ()->new Plane(new Point(1,0,0), new Point(2,0,0), new Point(3,0,0)),
                "ERROR: Plane constructor Does not throw an exception when all the points are on the same straight line");
    }

    /**
     * Test case for the
     * {@link geometries.Plane#getNormal (geometries.Plane)}.
     */
    @Test
    void testGetNormal() {
        Plane plane = new Plane(new Point(1, 2, 3),
                new Point(2, 3, 4),
                new Point(3, 2, 5));

        Vector _normal = plane.getNormal();

        // ============ Equivalence Partitions Tests ==============
        // TC01: Checking if the normal is a unit vector
        assertEquals(1, _normal.length(), DELTA, "ERROR: Plane's normal is not a unit vector");

        // TC02: Checking if the normal vector matches the expected value
        assertEquals(new Vector(0.7071067811865475, 0, -0.7071067811865475), _normal, "ERROR: failed getNormal() ");
    }
}