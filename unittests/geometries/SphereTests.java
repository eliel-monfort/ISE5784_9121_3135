package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Unit tests for geometries.Sphere class
 * @author Ariel Atias
 */
public class SphereTests {
    /**
     * Delta value for accuracy when comparing the numbers of type 'double' in
     * assertEquals
     */
    private final double DELTA = 0.000001;

    /**
     * Test case for the
     * {@link geometries.Sphere#getNormal (geometries.Sphere)}.
     */
    @Test
    void testGetNormal() {
        Sphere sphere = new Sphere(3, Point.ZERO);

        // ============ Equivalence Partitions Tests ==============
        // TC01: Expects an exception to be thrown when the center and point are the same.
        assertThrows(IllegalArgumentException.class, ()-> sphere.getNormal(new Point(0, 0, 0)),
                "ERROR: Sphere getNormal does not throw an exception when center and point are the same");

        // TC02: Checks if the computed normal is a unit vector.
        assertEquals(1, sphere.getNormal(new Point(3, 0, 0)).length(), DELTA,
                "ERROR: Sphere's normal is not a unit vector");

        // TC03: Checks if the computed normal matches the expected value for an equivalence partition.
        assertEquals(new Vector(1,0,0), sphere.getNormal(new Point(3, 0, 0)),
                "ERROR: Sphere getNormal equivalence partition has failed");
    }

    private final Point p001 = new Point(0, 0, 1);
    private final Point p100 = new Point(1, 0, 0);
    private final Vector v001 = new Vector(0, 0, 1);

    /**
     * Test method for
     * {@link geometries.Sphere#findIntersections (primitives.Ray)}.
     */
    @Test
    public void testFindIntersections() {
        Sphere sphere = new Sphere(1d, p100);
        final Point gp1 = new Point(0.0651530771650466, 0.355051025721682, 0);
        final Point gp2 = new Point(1.53484692283495, 0.844948974278318, 0);
        final var exp = List.of(gp1, gp2);
        final Vector v310 = new Vector(3, 1, 0);
        final Vector v110 = new Vector(1, 1, 0);
        final Vector v100 = new Vector(1, 0, 0);
        final Point p200 = new Point(2, 0, 0);
        final Point p01 = new Point(-1, 0, 0);


        // ============ Equivalence Partitions Tests ==============
        // TC01: Ray's line is outside the sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(p01, v110)), "Ray's line out of sphere");

        // TC02: Ray starts before and crosses the sphere (2 points)
        final var comparator = Comparator.comparingDouble(p -> ((Point) p).distance(p01));
        final var result1 = sphere.findIntersections(new Ray(p01, v310)).stream().sorted(comparator).toList();
        assertEquals(2, result1.size(), "Wrong number of points");
        assertEquals(exp, result1, "Ray crosses sphere");

        // TC03: Ray starts inside the sphere (1 point)
        var result2 = sphere.findIntersections(new Ray(new Point(0.8151530771650466,0.605051025721682,0), v310));
        assertEquals(1, result2.size(), "Wrong number of points");
        assertEquals(List.of(gp2), result2, "ERROR: When Ray that start in sphere (not center), findIntersections() does not work correctly");

        // TC04: Ray starts after the sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(1.83484692283495,0.944948974278318,0), v310)));

        // =============== Boundary Values Tests ==================
        // **** Group: Ray's line crosses the sphere (but not the center)
        // TC11: Ray starts at sphere and goes inside (1 points)
        result2 = sphere.findIntersections(new Ray(gp1, v310));
        assertEquals(1, result2.size(), "Wrong number of points");
        assertEquals(List.of(gp2), result2,
                "ERROR: When Ray starts at sphere and goes inside , findIntersections() does not work correctly");

        // TC12: Ray starts at sphere and goes outside (0 points)
        assertNull(sphere.findIntersections(new Ray(gp2, v310)), "Ray's line out of sphere");

        // **** Group: Ray's line goes through the center
        // TC13: Ray starts before the sphere (2 points)
        result2 = sphere.findIntersections(new Ray(p01, v100)).stream().sorted(comparator).toList();
        assertEquals(2, result2.size(), "Wrong number of points");
        assertEquals(List.of(Point.ZERO, p200), result2,
                "ERROR: When Ray starts before the sphere and Ray's line goes through the center, findIntersections() does not work correctly");

        // TC14: Ray starts at sphere and goes inside (1 points)
        result2 = sphere.findIntersections(new Ray(new Point(1, 0, -1), v001));
        assertEquals(1, result2.size(), "Wrong number of points");
        assertEquals(List.of(new Point(1, 0, 1)), result2,
                "ERROR: When Ray starts at sphere and goes inside and Ray's line goes through the center, findIntersections() does not work correctly");

        // TC15: Ray starts inside (1 points)
        result2 = sphere.findIntersections(new Ray(new Point(1.5, 0, 0), v100));
        assertEquals(1, result2.size(), "Wrong number of points");
        assertEquals(List.of(p200), result2,
                "ERROR: When Ray starts inside and Ray's line goes through the center, findIntersections() does not work correctly");

        // TC16: Ray starts at the center (1 points)
        result2 = sphere.findIntersections(new Ray(p100, v100));
        assertEquals(1, result2.size(), "Wrong number of points");
        assertEquals(List.of(p200), result2, "ERROR: When Ray starts at the center,findIntersections() does not work correctly");

        // TC17: Ray starts at sphere and goes outside (0 points)
        assertNull(sphere.findIntersections(new Ray(p200, v100)),
                "ERROR: When Ray starts at sphere and goes outside,findIntersections() does not work correctly");

        // TC18: Ray starts after sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(2.5, 0, 0), v100)),
                "ERROR: When Ray starts after sphere,findIntersections() does not work correctly");

        // **** Group: Ray's line is tangent to the sphere (all tests 0 points)
        // TC19: Ray starts before the tangent point
        assertNull(sphere.findIntersections(new Ray(new Point(0.5, 1, 0), v100)),
                "ERROR: When Ray starts before the tangent point,findIntersections() does not work correctly");

        // TC20: Ray starts at the tangent point
        assertNull(sphere.findIntersections(new Ray(new Point(1, 1, 0), v100)),
                "ERROR: When Ray starts at the tangent point,findIntersections() does not work correctly");

        // TC21: Ray starts after the tangent point
        assertNull(sphere.findIntersections(new Ray(new Point(1, 1.5, 0), v100)),
                "ERROR: When Ray starts after the tangent point,findIntersections() does not work correctly");

        // **** Group: Special cases
        // TC22: Ray's line is outside, ray is orthogonal to ray start to sphere's center line
        assertNull(sphere.findIntersections(new Ray(new Point(1, 1.5, 0), v100)),
                "ERROR: When Ray's line is outside, ray is orthogonal to ray start to sphere's center line ,findIntersections() does not work correctly");
    }
}