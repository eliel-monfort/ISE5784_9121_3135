package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for geometries.Sphere class
 * @author Ariel Atias
 */
class SphereTests {
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
        Sphere sphere = new Sphere(3, new Point(0,0,0));

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
}