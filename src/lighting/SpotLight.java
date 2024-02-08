package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * Represents a spot light source in a 3D scene.
 * A spot light emits light in a specific direction from a point in space,
 * forming a cone of illumination.
 */
public class SpotLight extends PointLight {

    private Vector direction;

    /**
     * Constructs a SpotLight with the specified intensity, position, and direction.
     *
     * @param intensity The Color representing the intensity of the spot light.
     * @param position  The Point representing the position of the spot light in 3D space.
     * @param direction The Vector representing the direction in which the spot light emits light.
     */
    public SpotLight(Color intensity, Point positon, Vector direction) {
        super(intensity, positon);
        this.direction = direction.normalize();
    }

    @Override
    public SpotLight setKc(double kC) {
        return (SpotLight) super.setKc(kC);
    }

    @Override
    public SpotLight setKl(double kL) {
        return (SpotLight) super.setKl(kL);
    }

    @Override
    public SpotLight setKq(double kQ) {
        return (SpotLight) super.setKq(kQ);
    }

    @Override
    public Color getIntensity(Point p) {
        Color intensity = super.getIntensity(p);
        return intensity.scale(Math.max(0, this.direction.dotProduct(getL(p))));
    }
}