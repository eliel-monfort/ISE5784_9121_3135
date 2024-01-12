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

    //#############################################################################
    /**
     * Test case for the
     * {@link geometries.Plane#findIntersections (geometries.Plane)}.
     */
    @Test
    void testfindIntsersections() {
        Plane plane = new Plane(new Point(-5, 3, 1), new Point(2, 7, -4), new Point(5, 0, 2));

        // ============ Equivalence Partitions Tests ==============
        // TC01: Ray intersect the plane -  orthogonal to the plane (1 point).
        var result = plane.findIntersections(new Ray(Point.ZERO, new Vector(0.1306288015575493, 0.676894698980028, 0.7243960813645914)));
        assertEquals(List.of(new Point(0.274573402905091,1.4227894514172892,1.5226343252009589)), result,
                "ERROR: Intersections with orthogonal ray does not work correctly");

        // TC01: Ray does not intersect the plane -  parallel to the plane (0 points).
        assertNull(plane.findIntersections(new Ray(Point.ZERO, new Vector(7, 4, -5))),
                "ERROR: Intersections with parallel ray does not work correctly");

        // =============== Boundary Values Tests ==================
        // TC10: parallel to the plane but not included in the plane (0 points).
        assertNull(plane.findIntersections(new Ray(Point.ZERO, new Vector(10, -3, 1))),
                "ERROR: Intersections with parallel ray and not included in plane does not work correctly");

        // TC11: parallel to the plane and included in the plane (0 points)
        assertNull(plane.findIntersections(new Ray(new Point(5, 0, 2), new Vector(7, 4, -5))),
                "ERROR: Intersections with parallel ray and included in plane does not work correctly");

        // **** Group: Ray's line crosses the plane. orthogonal.
        // TC12:  orthogonal to the plane and P0 is before the plane (1 point).
        result = plane.findIntersections(new Ray(new Point(-5, -5, -5), new Vector(0.1306288015575493, 0.676894698980028, 0.7243960813645914)));
        assertEquals(List.of(new Point(-4.189961001793738,9.78359763463453,10.49089177887511)), result,
                "ERROR: Intersections with orthogonal ray and point P0 before plane does not work correctly");

        // TC13:  orthogonal to the plane and P0 is in the plane (1 point).
        result = plane.findIntersections(new Ray(new Point(-5, 3, 1), new Vector(0.1306288015575493, 0.676894698980028, 0.7243960813645914)));
        assertEquals(List.of(new Point(-3.329266237570245,11.65223933561668,11.46475223325447)), result,
                "ERROR: Intersections with orthogonal ray and point P0 in plane does not work correctly");

        // TC14:  orthogonal to the plane and P0 is after the plane (1 point).
        result = plane.findIntersections(new Ray(new Point(4, 6, 3), new Vector(-0.1306288015575493, -0.676894698980028, -0.7243960813645914)));
        assertEquals(List.of(new Point(4.436598600689267,5.925535576930924,5.992556172523736)), result,
                "ERROR: Intersections with orthogonal ray and point P0 after plane does not work correctly");

        // **** Group: Ray's point included in the plane.
        // TC15: Ray's point in plane but the line are not.
        assertNull(plane.findIntersections(new Ray(new Point(2, 7, -4), new Vector(1, 0, 0))),
                "ERROR: Intersections with ray. Head point included in plane but line does not included - Intersections does not work correctly");

        // TC16: Ray's Head point equals to point q in plane, but the line are not included in plane.
        assertNull(plane.findIntersections(new Ray(new Point(-5, 3, 1), new Vector(1, 0, 0))),
                "ERROR: Intersections with ray. Head point equals to the plane's point (q) - Intersections does not work correctly");
    }
    //#############################################################################
}