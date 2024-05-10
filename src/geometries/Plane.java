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
public class Plane extends Geometry {

    /** A point on the plane. */
    private final Point q;

    /** The normal vector to the plane. */
    private final Vector normal;

    /**
     * Constructs a plane through three points on the plane.
     *
     * @param point1 A point on the plane.
     * @param point2 Another point on the plane.
     * @param point3 Another point on the plane.
     */
    public Plane(Point point1, Point point2, Point point3) {
        this.q = point1;
        Vector v1 = point2.subtract(point1);
        Vector v2 = point3.subtract(point1);
        this.normal = v1.crossProduct(v2).normalize();
    }

    /**
     * Constructs a plane through a point and with a specified normal vector.
     *
     * @param point  A point on the plane.
     * @param normal The normal vector to the plane.
     */
    public Plane(Point point, Vector normal) {
        this.q = point;
        this.normal = normal.normalize();
    }

    /**
     * Gets the q point of the plane.
     *
     * @return The q point of the plane.
     */
    public Point getq() {
        return this.q;
    }

    /**
     * Gets the normal vector of the plane.
     *
     * @return The normal vector of the plane.
     */
    public Vector getNormal() {
        return this.normal;
    }

    /**
     * Computes the normal vector to the plane at the specified point (which is on the plane).
     *
     * @param point The point on the plane.
     * @return The normal vector to the plane at the specified point.
     */
    @Override
    public Vector getNormal(Point point) {
        return this.normal;
    }

    /**
     * Finds the intersection points between the plane and a given ray.
     *
     * @param ray The ray for which intersections need to be found.
     * @return A list of intersection points, or null if no intersections are found.
     */
    @Override
    public List<Point> findIntersections(Ray ray) {
        double nv = alignZero(this.normal.dotProduct(ray.getDirection()));
        if (isZero(nv))
            return null;
        if (this.q.equals(ray.getHead()))
            return null;
        double t = alignZero(this.normal.dotProduct(q.subtract(ray.getHead()))) / nv;
        if (t <= 0)
            return null;
        return List.of(ray.getPoint(t));
    }

    /**
     * Helper method to find geometric intersections between the ray and the plane, considering a specified maximum distance.
     *
     * @param ray The ray for which to find geometric intersections.
     * @param maxDistance The maximum distance for intersection detection.
     * @return A list of GeoPoint objects representing the geometric intersections between the ray and the plane,
     *         or null if no intersections are found.
     */
    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        double nv = alignZero(this.normal.dotProduct(ray.getDirection()));
        if (isZero(nv) || this.q.equals(ray.getHead())) {
            return null;
        }
        double t = alignZero(this.normal.dotProduct(q.subtract(ray.getHead()))) / nv;
        if (t > 0 && alignZero(t - maxDistance) <= 0) {
            return List.of(new GeoPoint(this, ray.getPoint(t)));
        }
        return null;
    }
}