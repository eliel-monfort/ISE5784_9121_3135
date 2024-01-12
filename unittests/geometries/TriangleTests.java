package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

/**
 * Unit tests for geometries.Triangle class
 * @author Ariel Atias
 */
class TriangleTests {

    /**
     * Test case for the
     * {@link geometries.Triangle#getNormal (geometries.Triangle)}.
     */
    @Test
    void testGetNormal() {
        // Array for the three points of the triangle
        Point[] points = {new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0)};
        Triangle triangle = new Triangle(points[0], points[1], points[2]);
        Vector N = triangle.getNormal(new Point(0, 0, 1));

        // ============ Equivalence Partitions Tests ==============
        // TC01: Checks if the computed normal is a unit vector.
        assertEquals(1, N.length(), 0.00000001, "ERROR: Triangle's normal is not a unit vector");

        // TC02: Checks if the computed normal is orthogonal to all edges.
        for (int i = 0; i < 3; ++i) {
            assertTrue(isZero(N.dotProduct(points[i].subtract(points[i == 0 ? 2 : i - 1]))),
                    "ERROR: Triangle's normal is not orthogonal to one of the edges");
        }
    }

    //###########################################################################
    /**
     * Test case for the
     * {@link geometries.Triangle#findIntersections (geometries.Triangle)}.
     */
    @Test
    void testfindIntsersections() {
        
    }
    //###########################################################################
}