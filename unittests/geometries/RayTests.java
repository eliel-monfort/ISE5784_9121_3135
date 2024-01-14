package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for geometries.Geometries class
 * @author Ariel Atias
 */
public class RayTests {

    /**
     * Test method for
     * {@link primitives.Ray#getPoint (primitives.Ray)}.
     */
    @Test
    void testGetPoint() {
        Ray ray = new Ray(new Point(1, 1, 1), new Vector(-1, -1, 1));

        // ============ Equivalence Partitions Tests ==============
        // TC01: Testing with a positive parameter value (12)
        assertEquals(new Point(-5.9282032302755105, -5.9282032302755105, 7.9282032302755105),
                ray.getPoint(12), "Incorrect point calculation for positive parameter value");

        // TC02: Testing with a negative parameter value (-30)
        assertEquals(new Point(18.320508075688775, 18.320508075688775, -16.320508075688775),
                ray.getPoint(-30), "Incorrect point calculation for negative parameter value");

        // =============== Boundary Values Tests ==================
        // TC10: Testing with a parameter value of 0
        assertEquals(new Point(1, 1, 1), ray.getPoint(0),
                "Incorrect point calculation for parameter value of 0");
    }
}
