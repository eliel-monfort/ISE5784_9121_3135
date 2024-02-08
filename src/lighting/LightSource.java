package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * Represents a light source in a 3D scene.
 * Light sources contribute to the illumination of objects by emitting light.
 */
public interface LightSource {

    /**
     * Gets the intensity of the light at a specific point in 3D space.
     *
     * @param p The Point in 3D space for which the light intensity is evaluated.
     * @return The Color representing the intensity of the light at the specified point.
     */
    public Color getIntensity(Point p);

    /**
     * Gets the direction vector of the light at a specific point in 3D space.
     *
     * @param p The Point in 3D space for which the direction vector is evaluated.
     * @return The Vector representing the direction of the light at the specified point.
     */
    public Vector getL(Point p);
}