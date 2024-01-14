package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.isZero;

/**
 * A class that represents a tube in three-dimensional space, defined by a radius and an axis (Ray).
 * The class inherits from the RadialGeometry class.
 */
public class Tube extends RadialGeometry{
    /**
     * The axis of the tube, represented by a Ray.
     */
    protected final Ray axis;

    /**
     * Constructs a new Tube with the specified radius and axis.
     *
     * @param _radius The radius of the tube.
     * @param _axis   The axis of the tube, represented by a Ray.
     */
    public Tube(double _radius, Ray _axis){
        super(_radius);
        this.axis = _axis;
    }

    @Override
    public Vector getNormal(Point point_on_body) {
        Vector v = axis.getDirection();
        Vector vector_P_P0 = point_on_body.subtract(axis.getHead());
        double t = v.dotProduct(vector_P_P0);

        // point is on the axis of the tube - vector P-P0 is orthogonal to direction vector.
        if (isZero(t)) {
            return vector_P_P0.normalize();
        }

        Point O = axis.getPoint(t);
        return point_on_body.subtract(O).normalize();
    }

    //###########################################################################
    @Override
    public List<Point> findIntersections(Ray ray) {
        return null;
    }
    //###########################################################################
}