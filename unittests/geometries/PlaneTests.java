package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

/**
 * Unit tests for geometries.Plane class
 * @author Ariel Atias
 */
class PlaneTests {
    /**
     * Delta value for accuracy when comparing the numbers of type 'double' in
     * assertEquals
     */
    private final double DELTA = 0.000001;

    /**
     * Test case for the
     * {@link geometries.Plane#Plane (geometries.Plane)}.
     */
    @Test
    void testConstructor() {
        Plane plane1 = new Plane(Point.ZERO, new Point(0,1,0), new Point(0,0,1));

        // ============ Equivalence Partitions Tests ==============
        // TC01: All three points are the same. Expects exceptions to be thrown.
        assertThrows(IllegalArgumentException.class,
                ()-> new Plane(new Point(1,2,3), new Point(1,2,3), new Point(1,2,3)));

        // TC02: Checks normal vector length and correctness for non-collinear points.
        assertEquals(1, plane1.getNormal().length(),
                "ERROR: Plane constructor create wrong normal vector length");
        assertTrue(isZero(plane1.getNormal().dotProduct(new Vector(0,1,0))),
                "Plane constructor: wrong normal vector");
        assertTrue(isZero(plane1.getNormal().dotProduct(new Vector(0,0,1))),
                "Plane constructor: wrong normal vector");

        // =============== Boundary Values Tests ==================
        // TC10: Checks if an exception is thrown when two points are the same.
        assertThrows(IllegalArgumentException.class,
                ()->new Plane(new Point(1,0,0), new Point(1,0,0), new Point(5,4,6)),
                "ERROR: Plane constructor Does not throw an exception when two points are same");

        // TC11: Checks if an exception is thrown when all points are on the same straight line.
        assertThrows(IllegalArgumentException.class,
                ()->new Plane(new Point(1,0,0), new Point(2,0,0), new Point(3,0,0)),
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
        assertEquals(new Vector(0.7071067811865475, 0, -0.7071067811865475), _normal,
                "ERROR: Plane's getNormal does not work correctly");
    }

    /**
     * Test case for the
     * {@link geometries.Plane#findIntersections (geometries.Plane)}.
     */
    @Test
    void testfindIntsersections() {
        Plane plane = new Plane(new Point(-5, 3, 1), new Point(2, 7, -4), new Point(5, 0, 2));

        // ============ Equivalence Partitions Tests ==============
        // TC01: Ray intersect the plane -  orthogonal to the plane (1 point).
        var result = plane.findIntersections(new Ray(new Point(1, 1, 1), new Vector(11,57,61)));
        assertEquals(List.of(new Point(1.0744605838386687,1.3858412071640107,1.4129177831053448)), result,
                "ERROR: Intersections with orthogonal ray does not work correctly");

        // TC01: Ray does not intersect the plane -  parallel to the plane (0 points).
        assertNull(plane.findIntersections(new Ray(new Point(3, 6, 1), new Vector(7, 4, -5))),
                "ERROR: Intersections with parallel ray does not work correctly");

        // =============== Boundary Values Tests ==================
        // **** Group: Ray's line does not cross the plane. parallel.
        // TC10: parallel to the plane but not included in the plane (0 points).
        assertNull(plane.findIntersections(new Ray(new Point(1, 2, 3), new Vector(10, -3, 1))),
                "ERROR: Intersections with parallel ray and not included in plane does not work correctly");

        // TC11: parallel to the plane and included in the plane (0 points)
        assertNull(plane.findIntersections(new Ray(new Point(5, 0, 2), new Vector(7, 4, -5))),
                "ERROR: Intersections with parallel ray and included in plane does not work correctly");

        // **** Group: Ray's line crosses the plane. orthogonal.
        // TC12:  orthogonal to the plane and P0 is before the plane (1 point).
        result = plane.findIntersections(new Ray(new Point(-5, -5, -5), new Vector(11,57,61)));
        assertEquals(List.of(new Point(-3.724862501762798,1.6075306726836844,2.0712170356790303)), result,
                "ERROR: Intersections with orthogonal ray and point P0 before plane does not work correctly");

        // TC13:  orthogonal to the plane and P0 is in the plane (1 point).
        assertNull(plane.findIntersections(new Ray(new Point(2, 7, -4), new Vector(11,57,61))),
                "ERROR: Intersections with orthogonal ray and point P0 in plane does not work correctly");

        // TC14:  orthogonal to the plane and P0 is after the plane (1 point).
        result = plane.findIntersections(new Ray(new Point(4, 6, 3), new Vector(-11,-57,-61)));
        assertEquals(List.of(new Point(3.3919052319842056,2.8489634748272454,-0.3721618953603163)), result,
                "ERROR: Intersections with orthogonal ray and point P0 after plane does not work correctly");

        // **** Group: Ray's point included in the plane.
        // TC15: Ray's point in plane but the line are not.
        assertNull(plane.findIntersections(new Ray(new Point(2, 7, -4), new Vector(1, 0, 0))),
                "ERROR: Intersections with ray. Head point included in plane but line does not included - Intersections does not work correctly");

        // TC16: Ray's Head point equals to point q in plane, but the line are not included in plane.
        assertNull(plane.findIntersections(new Ray(new Point(-5, 3, 1), new Vector(1, 0, 0))),
                "ERROR: Intersections with ray. Head point equals to the plane's point (q) - Intersections does not work correctly");
    }
}