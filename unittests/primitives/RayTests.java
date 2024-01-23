package primitives;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Unit tests for primitives.Ray class
 * @author Ariel Atias
 */
public class RayTests {

    /**
     * Test method for
     * {@link Ray#findClosestPoint (primitives.Ray)}.
     */
    @Test
    void testFindClosestPoint() {
        Point p1 = new Point(2, 1, 3);
        Point p2 = new Point(2, 0, 0);
        Point p3 = new Point(-3, 2, 1);

        List<Point> ls = new ArrayList<>();
        ls.add(p1);
        ls.add(p2);
        ls.add(p3);

        List<Point> EmptyLs = new ArrayList<>();

        Ray ray1 = new Ray(new Point(0, 0, 1), new Vector(1, 1, 1));
        Ray ray2 = new Ray(new Point(0, 0, 2), new Vector(2, 0, -1));
        Ray ray3 = new Ray(new Point(-1, 1, 1), new Vector(1, -2, 3));

        // ============ Equivalence Partitions Tests ==============
        // When the closest point is in the middle of the list.
        assertEquals(p2, ray1.findClosestPoint(ls),
                "When closest point is a point that is in the middle - does not work correctly");

        // =============== Boundary Values Tests ==================
        // When the list is empty.
        assertNull(ray1.findClosestPoint(EmptyLs),
                "ERROR: Empty list should return null");

        // When the closest point is the first point in list.
        assertEquals(p1, ray2.findClosestPoint(ls),
                "ERROR: When closest point is the first point - does not work correctly");

        // When the closest point is the last point in list.
        assertEquals(p3, ray3.findClosestPoint(ls),
                "ERROR: When closest point is the last point - does not work correctly");
    }
}
