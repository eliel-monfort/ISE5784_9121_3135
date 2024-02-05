package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * A class that represents a plane in three-dimensional space defined by a point on the plane (q) and a normal vector.
 * The class inherits from the Geometry class.
 */
public class Plane extends Geometry{
    /**
     * A point on the plane.
     */
    private final Point q;

    /**
     * The normal vector to the plane.
     */
    private final Vector normal;

    /**
     * Constructs a plane through three points on the plane.
     *
     * @param point1 A point on the plane.
     * @param point2 Another point on the plane.
     * @param point3 Another point on the plane.
     */
    public Plane(Point point1, Point point2, Point point3){
        this.q = point1;
        Vector v1 = point2.subtract(point1);
        Vector v2 = point3.subtract(point1);
        this.normal = v1.crossProduct(v2).normalize();
    }

    /**
     * Constructs a plane through a point and with a specified normal vector.
     *
     * @param _point  A point on the plane.
     * @param _normal The normal vector to the plane.
     */
    public Plane(Point _point, Vector _normal){
        this.q = _point;
        this.normal = _normal.normalize();
    }

    /**
     * Gets the normal vector of the plane.
     *
     * @return The normal vector of the plane.
     */
    public Vector getNormal(){
        return this.normal;
    }

    @Override
    public Vector getNormal(Point point_on_body) {
        return this.normal;
    }

    @Override
    public List<Point> findIntersections(Ray ray) {
        double nv = this.normal.dotProduct(ray.getDirection());
        if (isZero(nv) || this.q.equals(ray.getHead())){
            return null;
        }
        double nQMinusP0 = this.normal.dotProduct(this.q.subtract(ray.getHead()));
        double t = alignZero(nQMinusP0 / nv);
        if (t > 0){
            return List.of(ray.getPoint(t));
        }
        return null;
    }
}