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

    /**
     * Calculates the distance between this light source and the specified point.
     *
     * @param point The Point to which the distance is measured.
     * @return The distance between this light source and the specified point.
     */
    public double getDistance(Point point);

    /**
     * Gets the position of the light source.
     *
     * @return The position of the light source.
     */
    public Point getPosition();

    /**
     * Determines if the light source casts soft shadows.
     *
     * @return True if the light source casts soft shadows, false otherwise.
     */
    boolean isSizedLight();
}