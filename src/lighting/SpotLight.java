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

    /** The direction in which the light is emitted. */
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

    /**
     * Sets the constant attenuation coefficient \( k_c \) for the spot light.
     *
     * @param kC The new value for the constant attenuation coefficient.
     * @return The spot light with the updated constant attenuation coefficient.
     */
    @Override
    public SpotLight setKc(double kC) {
        return (SpotLight) super.setKc(kC);
    }

    /**
     * Sets the linear attenuation coefficient \( k_l \) for the spot light.
     *
     * @param kL The new value for the linear attenuation coefficient.
     * @return The spot light with the updated linear attenuation coefficient.
     */
    @Override
    public SpotLight setKl(double kL) {
        return (SpotLight) super.setKl(kL);
    }

    /**
     * Sets the quadratic attenuation coefficient \( k_q \) for the spot light.
     *
     * @param kQ The new value for the quadratic attenuation coefficient.
     * @return The spot light with the updated quadratic attenuation coefficient.
     */
    @Override
    public SpotLight setKq(double kQ) {
        return (SpotLight) super.setKq(kQ);
    }

    /**
     * Retrieves the intensity of the light at the specified point, taking the spotlight effect into account.
     *
     * @param p The point at which the intensity of the light is being queried.
     * @return The intensity of the light at the specified point, considering the spotlight effect.
     */
    @Override
    public Color getIntensity(Point p) {
        Color intensity = super.getIntensity(p);
        return intensity.scale(Math.max(0, this.direction.dotProduct(getL(p))));
    }
}