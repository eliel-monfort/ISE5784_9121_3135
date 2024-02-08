package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * Represents a point light source in a 3D scene.
 * A point light emits light uniformly in all directions from a specific point in space.
 */
public class PointLight extends Light implements LightSource {

    protected Point positon;
    private double kC = 1;
    private double kL = 0;
    private double kQ = 0;

    /**
     * Sets the constant attenuation factor for the point light.
     *
     * @param kC The constant attenuation factor to set.
     * @return The updated PointLight instance.
     */
    public PointLight setKc(double kC) {
        this.kC = kC;
        return this;
    }

    /**
     * Sets the linear attenuation factor for the point light.
     *
     * @param kL The linear attenuation factor to set.
     * @return The updated PointLight instance.
     */
    public PointLight setKl(double kL) {
        this.kL = kL;
        return this;
    }

    /**
     * Sets the quadratic attenuation factor for the point light.
     *
     * @param kQ The quadratic attenuation factor to set.
     * @return The updated PointLight instance.
     */
    public PointLight setKq(double kQ) {
        this.kQ = kQ;
        return this;
    }

    /**
     * Constructs a PointLight with the specified intensity and position.
     *
     * @param intensity The Color representing the intensity of the point light.
     * @param position  The Point representing the position of the point light in 3D space.
     */
    public PointLight(Color intensity, Point positon) {
        super(intensity);
        this.positon = positon;
    }

    @Override
    public Color getIntensity(Point p) {
        double d = this.positon.distance(p);
        return intensity.scale(1d / (kC + (kL * d) + (kQ * d * d)));
    }

    @Override
    public Vector getL(Point p) {
        return p.subtract(this.positon).normalize();
    }
}
