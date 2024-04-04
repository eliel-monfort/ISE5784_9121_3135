package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;
import renderer.Blackboard;

/**
 * Represents a point light source in a 3D scene.
 * A point light emits light uniformly in all directions from a specific point in space.
 */
public class PointLight extends Light implements LightSource {

    /** The position of the light source. */
    protected Point position;

    /** The constant attenuation coefficient. */
    private double kC = 1;

    /** The linear attenuation coefficient. */
    private double kL = 0;

    /** The quadratic attenuation coefficient. */
    private double kQ = 0;

    //##################################################################################################################
    public Blackboard blackboard;
    //##################################################################################################################

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
    public PointLight(Color intensity, Point position) {
        super(intensity);
        this.position = position;
        this.blackboard = new Blackboard();
    }

    //##################################################################################################################
    public PointLight(Color intensity, Point position, double width,double height, int Nx, int Ny) {
        super(intensity);
        this.position = position;
        this.blackboard = new Blackboard(width, height, Nx, Ny).setCenterPoint(position);
    }
    //##################################################################################################################

    /**
     * Retrieves the position of the light source.
     *
     * @return The position of the light source.
     */
    @Override
    public Point getPosition() {
        return this.position;
    }

    /**
     * Retrieves the intensity of the light at the specified point, taking attenuation into account.
     *
     * @param p The point at which the intensity of the light is being queried.
     * @return The intensity of the light at the specified point, considering attenuation.
     */
    @Override
    public Color getIntensity(Point p) {
        double d = this.position.distance(p);
        return intensity.scale(1 / (kC + (kL * d) + (kQ * d * d)));
    }

    /**
     * Computes the direction vector from the light source to the specified point.
     *
     * @param p The point at which the direction vector is being calculated.
     * @return The direction vector from the light source to the specified point.
     */
    @Override
    public Vector getL(Point p) {
        return p.subtract(this.position).normalize();
    }

    /**
     * Calculates the distance between this light source and the specified point.
     *
     * @param point The Point to which the distance is measured.
     * @return The distance between this light source and the specified point.
     */
    @Override
    public double getDistance(Point point) {
        return point.distance(this.position);
    }

    /**
     * Determines if the light source casts soft shadows.
     *
     * @return True if the light source casts soft shadows, false otherwise.
     */
    @Override
    public boolean isSoftShadowed() {
        if (this.blackboard.isUseBlackboard()){
            return true;
        }
        return false;
    }
}