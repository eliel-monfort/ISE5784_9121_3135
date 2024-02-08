package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * Represents a directional light source in a 3D scene.
 * A directional light emits light in a specific direction with constant intensity.
 */
public class DirectionalLight extends Light implements LightSource {

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

    @Override
    public Color getIntensity(Point p) {
        return this.intensity;
    }

    @Override
    public Vector getL(Point p) {
        return direction;
    }
}
