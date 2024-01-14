package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Unit tests for geometries.Geometries class
 * @author Eliel Monfort
 */
public class GeometriesTests {

    /**
     * Test method for
     * {@link geometries.Geometries#findIntersections (primitives.Geometries)}.
     */
    @Test
    void testfindIntersections() {
        Point p060 = new Point(0, 6, 0);

        Plane plane = new Plane(new Point(2, 0, 0), new Point(0, -2, 0), new Point(0, 0, 4));
        Triangle triangle = new Triangle(new Point(3, 0, 0), new Point(-3, 0, 0), new Point(0, 0, 3));
        Sphere sphere = new Sphere(1, new Point(0, 1, 0));

        Geometries geometries = new Geometries(plane, triangle, sphere);
        Geometries emptyG = new Geometries();

        // ============ Equivalence Partitions Tests ==============
        // Check intersections with some geometries (but not all).
        assertEquals(2, geometries.findIntersections(new Ray(p060, new Vector(1, -6, 1))).size(),
                "Intersections with some geometries (but not all) does not work correctly");

        // =============== Boundary Values Tests ==================
        // Check intersections with all geometries.
        assertEquals(4, geometries.findIntersections(new Ray(p060, new Vector(0, -7, 1))).size(),
                "Intersections with all geometries does not work correctly");

        // Check intersections with just one geometry.
        assertEquals(1, geometries.findIntersections(new Ray(p060, new Vector(3, -6, 3))).size(),
                "Intersections with one geometries does not work correctly");

        // Check non intersections with geometries.
        assertNull(geometries.findIntersections(new Ray(p060, new Vector(-2, -2, 0))),
                "Non intersections with geometries does not work correctly");

        // Check intersections with empty list of geometries.
        assertNull(emptyG.findIntersections(new Ray(p060, new Vector(1, 1, 1))),
                "Intersections with non geometries does not work correctly");
    }
}
