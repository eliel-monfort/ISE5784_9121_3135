package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for geometries.Tube class
 * @author Eliel Monfort
 */
class TubeTests {
    /**
     * Delta value for accuracy when comparing the numbers of type 'double' in
     * assertEquals
     */
    private final double DELTA = 0.000001;

    /**
     * Test case for the
     * {@link geometries.Tube#getNormal (geometries.Tube)}.
     */
    @Test
    void testGetNormal() {
        Tube tube = new Tube(3, new Ray(new Point(0, 0, 0),new Vector(1, 0, 0)));

        // ============ Equivalence Partitions Tests ==============
        // TC01: Checks if no exception is thrown for a valid point.
        assertDoesNotThrow(() -> tube.getNormal(new Point(1, 1, 0)),
                "ERROR: Tube's getNormal throws wrong exception");

        // TC02: Checks if the computed normal is a unit vector.
        assertEquals(1, tube.getNormal(new Point(1, 1, 0)).length(), DELTA,
                "ERROR: Tube's normal is not a unit vector");

        // TC03: Checks if the computed normal matches the expected value for an equivalence partition.
        assertEquals(new Vector(0, 1, 0), tube.getNormal(new Point(1, 1, 0)),
                "ERROR: Tube's getNormal does not work correctly");

        // =============== Boundary Values Tests ==================
        // TC10: Checks if the computed normal matches the expected value for a boundary point.
        // when the vector P-P0 is orthogonal to direction vector.
        assertEquals(new Vector(0, 1, 0), tube.getNormal(new Point(0, 1, 0)),
                "ERROR: Tube GetNormal boundary value test has failed.");
    }
}