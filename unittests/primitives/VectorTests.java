package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for primitives.Vector class
 * @author Eliel Monfort
 */
class VectorTests {
    private final double DELTA = 0.000001;

    /**
     * Test method for
     * {@link primitives.Vector#add(primitives.Vector)}.
     */
    @Test
    void testAdd() {
        // Create vectors for testing
        Vector v1 = new Vector(3, -4, 5);
        Vector v1_opposite = new Vector(-3, 4, -5);
        Vector v2 = new Vector(-1, 7, 6);

        // ============ Equivalence Partitions Tests ==============
        // Test adding two different vectors. Expects no exceptions to be thrown.
        assertDoesNotThrow(() -> v1.add(v2), "ERROR: Vector + vector throws wrong exception");

        // Test adding a vector to itself. Expects no exceptions to be thrown.
        assertDoesNotThrow(() -> v1.add(v1), "ERROR: Vector + itself throws wrong exception");

        // Test vector addition with different vectors. Expects the result to be the sum of the two vectors.
        assertEquals(new Vector(2, 3, 11), v1.add(v2), "ERROR: Vector + Vector does not work correctly");

        // =============== Boundary Values Tests ==================
        // Test adding a vector to its opposite. Expects an IllegalArgumentException to be thrown, the result is the zero vector.
        assertThrows(IllegalArgumentException.class, () -> v1.add(v1_opposite), "ERROR: Vector + -itself does not throw an exception");
    }

    /**
     * Test method for
     * {@link primitives.Vector#scale (primitives.Vector)}.
     */
    @Test
    void testScale() {
        // Create a vector for testing
        Vector v = new Vector(4, -3, 9);

        // ============ Equivalence Partitions Tests ==============
        // Test scaling a vector by a positive factor. Expects no exceptions to be thrown.
        assertDoesNotThrow(() -> v.scale(3), "ERROR: scale vector throws wrong exception");

        // Test scaling a vector by a positive factor. Expects the result to be the vector multiplied by the scaling factor.
        assertEquals(new Vector(12, -9, 27), v.scale(3), "ERROR: scale vector does not work correctly");

        // =============== Boundary Values Tests ==================
        // Test scaling a vector by zero. Expects an IllegalArgumentException to be thrown, as scaling by zero is undefined.
        assertThrows(IllegalArgumentException.class, () -> v.scale(0), "ERROR: scale vector does not throw an exception");
    }

    /**
     * Test method for
     * {@link primitives.Vector#dotProduct(primitives.Vector)}.
     */
    @Test
    void testDotProduct() {
        Vector v1 = new Vector(3, 1, -7);
        Vector v2 = new Vector(-5, 8, 4);
        Vector v3 = new Vector(-1, 3, 0);

        // ============ Equivalence Partitions Tests ==============

        // Test computing the dot product of two vectors. Expects no exceptions to be thrown.
        assertDoesNotThrow(() -> v1.dotProduct(v2), "ERROR: DotProduct() throws wrong exception");

        // Test computing the dot product of two vectors. Expects the result to be the correct dot product value.
        assertEquals(-35, v1.dotProduct(v2), "ERROR: DotProduct() wrong value");

        // =============== Boundary Values Tests ==================

        // Test computing the dot product of orthogonal vectors. Expects the result to be zero.
        assertEquals(0, v1.dotProduct(v3), DELTA, "ERROR: dotProduct() for orthogonal vectors is not zero");
    }

    /**
     * Test method for
     * {@link primitives.Vector#length (primitives.Vector)}.
     */
    @Test
    void testLength() {
        Vector v = new Vector(4, 2, -4);

        // ============ Equivalence Partitions Tests ==============
        // Test computing the length of the vector. Expects no exceptions to be thrown.
        assertDoesNotThrow(() -> v.length(), "ERROR: length() throws wrong exception");

        // Test computing the length of the vector. Expects the result to be the correct length value.
        assertEquals(6, v.length(), DELTA, "ERROR: length() wrong value");
    }

    /**
     * Test method for
     * {@link primitives.Vector#lengthSquared (primitives.Vector)}.
     */
    @Test
    void testLengthSquared() {
        Vector v = new Vector(4, 2, -4);

        // ============ Equivalence Partitions Tests ==============
        // Test computing the length squared of the vector. Expects no exceptions to be thrown.
        assertDoesNotThrow(() -> v.lengthSquared(), "ERROR: lengthSquared() throws wrong exception");

        // Test computing the length squared of the vector. Expects the result to be the correct length value.
        assertEquals(36, v.lengthSquared(), DELTA, "ERROR: lengthSquared() wrong value");
    }

    /**
     * Test method for
     * {@link primitives.Vector#crossProduct(primitives.Vector)}.
     */
    @Test
    void testCrossProduct() {
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(0, 3, -2);
        Vector v3 = new Vector(3, 6, 9);
        Vector vr = v1.crossProduct(v2);

        // ============ Equivalence Partitions Tests ==============

        // TC01: Test that length of cross-product is proper (orthogonal vectors taken for simplicity)
        assertEquals(v1.length() * v2.length(), vr.length(), DELTA, "ERROR: crossProduct() wrong result length");

        // TC02: Test cross-product result orthogonality to its operands
        assertEquals(0, vr.dotProduct(v1), "ERROR: crossProduct() result is not orthogonal to 1st operand");
        assertEquals(0, vr.dotProduct(v2), "ERROR: crossProduct() result is not orthogonal to 2nd operand");

        // =============== Boundary Values Tests ==================
        // TC11: test zero vector from cross-product of parallel vectors
        assertThrows(IllegalArgumentException.class, ()-> v1.crossProduct(v3), "ERROR: crossProduct() for parallel vectors does not throw an exception");
    }

    /**
     * Test method for
     * {@link Vector#normalize (primitives.Vector)}.
     */
    @Test
    void testNormalize() {
        Vector v = new Vector(0, 3, 4);
        Vector u = v.normalize();

        // ============ Equivalence Partitions Tests ==============
        // Test normalizing a vector. Expects no exceptions to be thrown.
        assertDoesNotThrow(() -> v.normalize(), "ERROR: normalize() throws wrong exception");

        // Test that the length of the normalized vector is approximately 1 (within a small delta).
        assertEquals(1, u.length(), 0.00000001, "ERROR: the normalized vector is not a unit vector");

        // Test that the normalized vector is parallel to the original vector.
        assertThrows(IllegalArgumentException.class, ()->v.crossProduct(u), "Error: The normalized vector is not parallel to the original one");

        // Test that the dot product of the original and normalized vectors is positive.
        assertTrue(v.dotProduct(u) > 0, "ERROR: the normalized vector is opposite to the original one");
    }
}
