package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * Represents a directional light source in a 3D scene.
 * A directional light emits light in a specific direction with constant intensity.
 */
public class DirectionalLight extends Light implements LightSource {

    /** The direction of the light. */
    private Vector direction;

    /**
     * Constructs a DirectionalLight with the specified intensity and direction.
     *
     * @param intensity The Color representing the intensity of the directional light.
     * @param direction The Vector representing the direction of the light.
     *                  The direction will be normalized internally.
     */
    public DirectionalLight(Color intensity, Vector direction) {
        super(intensity);
        this.direction = direction.normalize();
    }

    /**
     * Retrieves the intensity of the light at the specified point.
     *
     * @param p The point at which the intensity of the light is being queried.
     * @return The intensity of the light.
     */
    @Override
    public Color getIntensity(Point p) {
        return this.intensity;
    }

    /**
     * Computes the direction vector from the light source to the specified point.
     *
     * @param p The point at which the direction vector is being calculated.
     * @return The direction vector from the light source to the specified point.
     */
    @Override
    public Vector getL(Point p) {
        return direction;
    }

    /**
     * Calculates the distance between this light source and the specified point.
     *
     * @param point The Point to which the distance is measured.
     * @return The distance between this light source and the specified point.
     */
    @Override
    public double getDistance(Point point) {
        return Double.POSITIVE_INFINITY;
    }

    /**
     * Retrieves the position of the light source.
     *
     * @return The position of the light source.
     */
    @Override
    public Point getPosition() {
        return null;
    }

    /**
     * Determines if the light source casts soft shadows.
     *
     * @return True if the light source casts soft shadows, false otherwise.
     */
    @Override
    public boolean isSoftShadowed() {
        return false;
    }
}
