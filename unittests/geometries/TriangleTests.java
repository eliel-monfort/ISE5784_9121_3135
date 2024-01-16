package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

/**
 * Unit tests for geometries.Triangle class
 * @author Ariel Atias
 */
public class TriangleTests {

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

    /**
     * Test case for the
     * {@link geometries.Triangle#findIntersections (geometries.Triangle)}.
     */
    @Test
    void testfindIntsersections() {
        Triangle triangle = new Triangle( new Point(-1,0,0), new Point(2,0,0), new Point(0,3,0));

        // ============ Equivalence Partitions Tests ==============
        // TC01: The point of intersection inside the triangle (1 point)
        var result = triangle.findIntersections(new Ray(new Point(0,1, -1), new Vector(0,1, 1)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(new Point(0,2,0)), result, "Ray crosses in triangle does not work correctly");

        // TC02: The point of intersection is outside the triangle opposite edge (0 points)
        assertNull(triangle.findIntersections(new Ray(new Point(-0.5,1,1), new Vector(-1,0,1 ))),
                "Ray does not cross triangle and point is opposite edge - does not work correctly");

        // TC03: The point of intersection is outside the triangle opposite a vertex (0 points)
        assertNull(triangle.findIntersections(new Ray(new Point(-1,0.5,-1), new Vector(-0.2,-0.6, 1))),
                "Ray does not cross triangle and point is opposite vertex - does not work correctly");

        // =============== Boundary Values Tests ==================
        // TC11: The intersection point is on an edge (0 points)
        assertNull(triangle.findIntersections(new Ray(new Point(-1,-1.5,-1), new Vector(0.5,1.5,1))),
                "Ray does not cross triangle and point is on edge - does not work correctly");

        // TC12: The intersection point is on a vertex (0 points)
        assertNull(triangle.findIntersections(new Ray(new Point(-1,-1,-1), new Vector(3,1,1))),
                "Ray does not cross triangle and point is on vertex - does not work correctly");

        // TC13: The intersection point is on the continuation of an edge (0 points)
        assertNull(triangle.findIntersections(new Ray(new Point(-2,-2,-0.5), new Vector(5,2,0.5))),
                "Ray does not cross triangle and point is on the continuation of an edge - does not work correctly");
    }
}