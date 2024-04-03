package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

import static primitives.Util.alignZero;

/**
 * Represents a spot light source in a 3D scene.
 * A spot light emits light in a specific direction from a point in space,
 * forming a cone of illumination.
 */
public class SpotLight extends PointLight {

    /** The direction in which the light is emitted. */
    private Vector direction;

    /** The parameter controlling the narrowness of the light beam. */
    private double narrowBeam = 1;

    /**
     * Constructs a SpotLight with the specified intensity, position, and direction.
     *
     * @param intensity The Color representing the intensity of the spot light.
     * @param positon  The Point representing the position of the spot light in 3D space.
     * @param direction The Vector representing the direction in which the spot light emits light.
     */
    public SpotLight(Color intensity, Point positon, Vector direction) {
        super(intensity, positon);
        this.direction = direction.normalize();
    }

    //##################################################################################################################
    public SpotLight(Color intensity, Point positon, Vector direction, double width, double height, int Nx, int Ny) {
        super(intensity, positon, width, height, Nx, Ny);
        this.direction = direction.normalize();
    }
    //##################################################################################################################

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
     * Sets the parameter controlling the narrowness of the light beam.
     *
     * @param narrowBeam The narrowness parameter to set for the light beam.
     * @return The SpotLight instance with the updated narrowness parameter.
     */
    public SpotLight setNarrowBeam(double narrowBeam) {
        this.narrowBeam = narrowBeam;
        return this;
    }

    /**
     * Gets the parameter controlling the narrowness of the light beam.
     *
     * @return The narrowness parameter of the light beam.
     */
    public double getNarrowBeam() {
        return this.narrowBeam;
    }

    /**
     * Retrieves the intensity of the light at the specified point, taking the spotlight effect into account.
     *
     * @param p The point at which the intensity of the light is being queried.
     * @return The intensity of the light at the specified point, considering the spotlight effect.
     */
    @Override
    public Color getIntensity(Point p) {
        double LDirection = direction.dotProduct(getL(p));
        if (alignZero(LDirection) <= 0){
            return Color.BLACK;
        }
        return super.getIntensity(p).scale(Math.pow(LDirection, narrowBeam));
    }
}